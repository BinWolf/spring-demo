package com.wolf.service;

/**
 * 分布式锁
 *
 * @author zhangxin
 * 2017年5月17日
 */
public interface IDistributedLockService {
	
	boolean lock(String key);
	
	boolean lock(String key, boolean isWait);

	boolean tryLock(String key, int waitTimeOut);
	
	boolean release(String key);
}
