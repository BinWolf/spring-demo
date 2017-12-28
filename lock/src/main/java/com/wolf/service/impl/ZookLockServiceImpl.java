package com.wolf.service.impl;

import com.wolf.commons.util.PropertiesUtil;
import com.wolf.commons.util.ThreadContext;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

/**
 * 分布式锁 zk实现
 * @author zhangxin
 */
@Service
public class ZookLockServiceImpl {

	private static final Logger logger = LoggerFactory.getLogger(ZookLockServiceImpl.class);

	private static final ThreadLocal<NodePath> LOCAL = new ThreadLocal<NodePath>();
	/**
	 * 超时时间
	 */
	private static int SESSION_TIMEOUT = 100;

	/**
	 * 锁超时时间
	 */
	private static int LOCK_WAIT_TIMEOUT = 2000;

	/**
	 * ZK地址
	 */
	private static String CONNECTION_STRING ;

	/**
	 * 线程池基本变量
	 */
	private static final String THREAD_KEY_BASE = "zk_lock_%s";

	private static ZooKeeper zk;

	@PostConstruct
	public void init() {
		Properties properties = PropertiesUtil.getConfig("zookeeper.properties");
		if (properties == null) {
			return;
		}

		String zookeeperLockConnection = properties.getProperty("zookeeper.lock.connection");
		if (StringUtils.isBlank(zookeeperLockConnection)) {
			return;
		}
		int zookeeperLockSessionTimeout = NumberUtils.toInt(properties.getProperty("zookeeper.lock.session.timeout"), SESSION_TIMEOUT);

		int zookeeperLockWaitTimeout = NumberUtils.toInt(properties.getProperty("zookeeper.lock.wait.timeout"), LOCK_WAIT_TIMEOUT);

		this.CONNECTION_STRING = zookeeperLockConnection;
		this.SESSION_TIMEOUT = zookeeperLockSessionTimeout;
		this.LOCK_WAIT_TIMEOUT = zookeeperLockWaitTimeout;

		try {
			zk = new ZooKeeper(CONNECTION_STRING, SESSION_TIMEOUT, new ZkTemplate());
		} catch (IOException e) {
			logger.error("initial zk fail ", e);
		}
	}

	/**
	 * 对信息加锁
	 * @param key    - 锁名
	 * @return - true 为加锁成功
	 */
	public boolean lock(String key) {
		NodePath nodePath = new NodePath(key);
		LOCAL.set(nodePath);

		try {

			initLockPath(nodePath);

			nodePath.selfPath = zk.create(nodePath.subPath, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
			while (!checkForPreparedPath(nodePath)) {

				if (nodePath.isLockExpire()) {
					release(key);
					return false;
				}
				System.out.println(nodePath.waitPath);
				synchronized (nodePath.waitPath) {
					nodePath.waitPath.wait(LOCK_WAIT_TIMEOUT);
				}
			}

			return true;

		} catch (Exception e) {
			logger.error("zkLock lock error [" + key + "]", e);
			if (nodePath.isLockExpire()) {
				release(key);
			}
			return false;
		}
	}

	public boolean tryLock(String key, int waitTimeOut) {
		this.LOCK_WAIT_TIMEOUT = waitTimeOut;
		return lock(key);
	}

	public boolean release(String key) {
		NodePath nodePath = LOCAL.get();
		System.out.println("release: " + nodePath.toString());
		if (nodePath == null) {
			return true;
		}

		try {

			if (zk.exists(nodePath.selfPath, true) == null) {
				return true;
			}

			zk.delete(nodePath.selfPath, -1);
			return true;

		} catch (Exception e) {
			logger.error("zkLock release error [" + key + "]", e);
			return false;
		}
	}

	private boolean checkForPreparedPath(NodePath nodePath) throws Exception {

		List<String> subNodes = zk.getChildren(nodePath.basePath, false);

		Collections.sort(subNodes);

		int index = subNodes.indexOf(nodePath.selfPath.substring(nodePath.basePath.length() + 1));

		switch (index) {
			case -1: {
				// 该节点不存在
				return false;
			}
			case 0: {
				// 准备就绪
				return true;
			}
			default: {
				// 设置
				nodePath.waitPath = nodePath.basePath + "/" + subNodes.get(index - 1);

				try {
					zk.getData(nodePath.waitPath, true, new Stat());
					return false;

				} catch (KeeperException e) {
					// 并发重试
					if (zk.exists(nodePath.waitPath, true) == null) {
						return checkForPreparedPath(nodePath);
					}
					throw e;
				}
			}
		}
	}

	private void initLockPath(NodePath nodePath) throws KeeperException, InterruptedException {
		if (zk.exists(nodePath.basePath, false) != null) {
			return;
		}
		try {
			zk.create(nodePath.basePath, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		} catch (Exception e) {
			logger.info("初始化创建分布式锁节点未成功, 初始化创建分布式锁节点, base path: {}", nodePath.basePath);
		}
	}

	static class NodePath {

		private String basePath = "/zk-lock-test";
		/**
		 * 锁目录
		 */
		private String subPath = basePath + "/%s-";

		private String selfPath;
		private String waitPath;

		/**
		 * 创建时间
		 */
		private long createTime;

		public NodePath(String key) {
			this.subPath = String.format(subPath, key);
			this.createTime = System.currentTimeMillis();
		}

		/**
		 * 锁是否超时
		 */
		private boolean isLockExpire() {
			return createTime + LOCK_WAIT_TIMEOUT <= System.currentTimeMillis();
		}

		@Override
		public String toString() {
			return "NodePath{" +
					"basePath='" + basePath + '\'' +
					", subPath='" + subPath + '\'' +
					", selfPath='" + selfPath + '\'' +
					", waitPath='" + waitPath + '\'' +
					", createTime=" + createTime +
					'}';
		}
	}

	class ZkTemplate implements Watcher {

		@Override
		public void process(WatchedEvent event) {
			if (event == null) {
				return;
			}
			Event.KeeperState keeperState = event.getState();
			Event.EventType eventType = event.getType();

			if (Event.KeeperState.Disconnected == keeperState) {
				// 与ZK服务器断开连接
				return;
			}
			if (Event.KeeperState.AuthFailed == keeperState) {
				// 权限检查失败
				return;
			}
			if (Event.KeeperState.Expired == keeperState) {
				// 会话失效
				return;
			}

			if (Event.KeeperState.SyncConnected == keeperState) {

				if (Event.EventType.None == eventType) {
					// 成功连接上ZK服务器
					return;
				}
				System.out.println("process : " + event.getPath());
				if (event.getType() == Event.EventType.NodeDeleted) {
					synchronized (event.getPath()) {
						event.getPath().notifyAll();
					}
				}
			}
		}

	}
}
