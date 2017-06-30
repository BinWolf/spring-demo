package com.wolf.service.impl;

import com.wolf.commons.util.IDUtil;
import com.wolf.commons.util.PropertiesUtil;
import com.wolf.commons.util.ThreadContext;
import com.wolf.service.IDistributedLockService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

/**
 * 分布式锁 zk实现
 * @author zhangxin
 */
@Service
public class ZkLockServiceImpl implements IDistributedLockService {

	private static final Logger logger = LoggerFactory.getLogger(ZkLockServiceImpl.class);

	/**
	 * 超时时间
	 */
	private static int SESSION_TIMEOUT = 6000;

	/**
	 * 锁超时时间
	 */
	private static int LOCK_WAIT_TIMEOUT = 15000;

	/**
	 * ZK地址
	 */
	private static String CONNECTION_STRING ;

	/**
	 * 线程池基本变量
	 */
	private static final String THREAD_KEY_BASE = "zk_lock_%s";

	@PostConstruct
	public boolean init() {
		Properties properties = PropertiesUtil.getConfig("zookeeper.properties");
		if (properties == null) {
			return false;
		}

		String zookeeperLockConnection = properties.getProperty("zookeeper.lock.connection");
		if (StringUtils.isBlank(zookeeperLockConnection)) {
			return false;
		}
		int zookeeperLockSessionTimeout = NumberUtils.toInt(properties.getProperty("zookeeper.lock.session.timeout"), SESSION_TIMEOUT);

		int zookeeperLockWaitTimeout = NumberUtils.toInt(properties.getProperty("zookeeper.lock.wait.timeout"), LOCK_WAIT_TIMEOUT);

		this.CONNECTION_STRING = zookeeperLockConnection;
		this.SESSION_TIMEOUT = zookeeperLockSessionTimeout;
		this.LOCK_WAIT_TIMEOUT = zookeeperLockWaitTimeout;
		return true;
	}


	/**
	 * 对信息加锁(并发时不等待)
	 * @param key - 锁名
	 * @return - true 为加锁成功
	 */
	@Override
	public boolean lock(String key) {
		return lock(key, false);
	}


	/**
	 * 对信息加锁
	 * @param key    - 锁名
	 * @param isWait - 并发时是否等待
	 * @return - true 为加锁成功
	 */
	@Override
	public boolean lock(String key, boolean isWait) {

		ZkTemplate zkTemplate = new ZkTemplate(IDUtil.random32Chars(), key, isWait);

		ThreadContext.put(String.format(THREAD_KEY_BASE, key), zkTemplate);

		try {

			zkTemplate.zk = new ZooKeeper(CONNECTION_STRING, SESSION_TIMEOUT, zkTemplate);

			initLockPath(zkTemplate);

			zkTemplate.selfPath = zkTemplate.zk.create(zkTemplate.subPath, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
			while (!checkForPreparedPath(zkTemplate)) {

				if (!isWait || zkTemplate.isLockExpire()) {
					zkTemplate.isWait = false;
					release(key);
					return false;
				}

				synchronized (zkTemplate) {
					zkTemplate.wait(LOCK_WAIT_TIMEOUT);
				}
			}

			return true;

		} catch (Exception e) {
			logger.error("zkLock lock error [" + zkTemplate.id + "][" + key + "][ " + isWait + "]", e);
			if (!isWait || zkTemplate.isLockExpire()) {
				zkTemplate.isWait = false;
				release(key);
			}
			return false;
		}
	}

	@Override
	public boolean tryLock(String key, int waitTimeOut) {
		this.LOCK_WAIT_TIMEOUT = waitTimeOut;
		return lock(key, true);
	}

	@Override
	public boolean release(String key) {

		ZkTemplate zkTemplate = (ZkTemplate) ThreadContext.remove(String.format(THREAD_KEY_BASE, key));

		if (zkTemplate == null) {
			return true;
		}

		try {

			if (zkTemplate.zk.exists(zkTemplate.selfPath, true) == null) {
				return true;
			}

			zkTemplate.zk.delete(zkTemplate.selfPath, -1);
			if (zkTemplate.zk != null) {
				zkTemplate.zk.close();
			}

			return true;

		} catch (Exception e) {
			logger.error("zkLock release error [" + zkTemplate.id + "][" + key + "][ " + zkTemplate.isWait + "]", e);
			return false;
		}
	}


	private boolean checkForPreparedPath(ZkTemplate zkTemplate) throws Exception {

		List<String> subNodes = zkTemplate.zk.getChildren(zkTemplate.basePath, false);

		Collections.sort(subNodes);

		int index = subNodes.indexOf(zkTemplate.selfPath.substring(zkTemplate.basePath.length() + 1));

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
				zkTemplate.waitPath = zkTemplate.basePath + "/" + subNodes.get(index - 1);

				try {
					zkTemplate.zk.getData(zkTemplate.waitPath, true, new Stat());
					return false;

				} catch (KeeperException e) {
					// 并发重试
					if (zkTemplate.zk.exists(zkTemplate.waitPath, true) == null) {
						return checkForPreparedPath(zkTemplate);
					}
					throw e;
				}
			}
		}
	}

	private void initLockPath(ZkTemplate zkTemplate) throws KeeperException, InterruptedException {
		if (zkTemplate.zk.exists(zkTemplate.basePath, false) != null) {
			return;
		}
		try {
			zkTemplate.zk.create(zkTemplate.basePath, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		} catch (Exception e) {
			logger.info("初始化创建分布式锁节点未成功, 初始化创建分布式锁节点, id:" + zkTemplate.id + " Path: " + zkTemplate.basePath);
		}
	}

	class ZkTemplate implements Watcher {

		/**
		 * ID
		 */
		private String id;

		/**
		 * zk实例
		 */
		private ZooKeeper zk = null;

		/**
		 * 并发是否等待
		 */
		private boolean isWait = false;

		/**
		 * 创建时间
		 */
		private long createTime;

		/**
		 * 该锁的zk地址
		 */
		private String selfPath;

		/**
		 * 等待锁的zk路径
		 */
		private String waitPath;

		/**
		 * 锁根目录
		 */
		private String basePath = "/zk-lock";

		/**
		 * 锁目录
		 */
		private String subPath = basePath + "/%s-";

		public ZkTemplate(String id, String key, boolean isWait) {
			this.id = id;
			this.subPath = String.format(subPath, key);
			this.isWait = isWait;
			this.createTime = System.currentTimeMillis();
		}

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

				if (event.getType() == Event.EventType.NodeDeleted && event.getPath().equals(waitPath) && this.isWait) {
					synchronized (this) {
						this.notifyAll();
					}
				}
			}
		}

		/**
		 * 锁是否超时
		 */
		private boolean isLockExpire() {
			return createTime + LOCK_WAIT_TIMEOUT <= System.currentTimeMillis();
		}
	}
}
