package com.wolf.lock;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by wolf on 17/6/16.
 */
public class LockMain {

	public static RLock initLock(String key) {
		Config config = new Config();
		config.setUseLinuxNativeEpoll(true);
		config.useClusterServers().addNodeAddress("redis://127.0.0.1:6379");
		RedissonClient redissonClient = Redisson.create(config);
		return redissonClient.getLock(key);
	}

	public static void main(String[] args) {

		RLock myLock = initLock("testMyLock");

		try {
			boolean lock = myLock.tryLock(20, 10, TimeUnit.SECONDS);
			System.out.println("main : " + Thread.currentThread().getName()
					+ " ; " + (new Date()) + "; " + lock);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		LockTask lockTask = new LockTask();
		new Thread(lockTask).start();
	}

	static class LockTask implements Runnable {
		@Override
		public void run() {
			RLock myLock = initLock("testMyLock");
			try {
				boolean lock = myLock.tryLock(20, 10, TimeUnit.SECONDS);
				System.out.println("LockTask : " + Thread.currentThread().getName()
						+ " ; " + (new Date()) + "; " + lock);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

}
