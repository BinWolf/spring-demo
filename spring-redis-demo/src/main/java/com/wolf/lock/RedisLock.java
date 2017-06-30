package com.wolf.lock;

import com.wolf.cache.ICacheKey;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.data.redis.core.ValueOperations;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by wolf on 17/6/7.
 */
public class RedisLock {

	public RedisLock(ValueOperations<String, String> valOpt, ICacheKey cacheKey) {
		this.cacheKey = cacheKey;
		this.valOpt = valOpt;
	}

	private ValueOperations<String, String> valOpt;

	private ICacheKey cacheKey;

	//纳秒和毫秒之间的转换率
	public static final long SECOND_MILLI_TIME = 1000L;

	public String lockValue;

	public static final Random RANDOM = new Random();

	private boolean lock = false;

	/**
	 * @param timeout 轮询锁的超时时间,单位秒
	 * @return
	 */
	public boolean lock(long timeout) {
		long currentTime = System.currentTimeMillis();
		this.lockValue = String.valueOf(currentTime) + TimeUnit.MICROSECONDS.convert(cacheKey.getExpirationTime(), cacheKey.getExpirationTimeUnit());
		timeout *= SECOND_MILLI_TIME;
		try {
			while (System.currentTimeMillis() - currentTime < timeout) {

				// 正常情况下拿到锁
				if (valOpt.setIfAbsent(cacheKey.toString(), this.lockValue)) {
					valOpt.getOperations().expire(cacheKey.toString(), cacheKey.getExpirationTime(), cacheKey.getExpirationTimeUnit());
					this.lock = true;
					return this.lock;
				}

				// 上一个锁时间到了异常情况下没有释放锁，该情况也应该拿到锁
				long value = NumberUtils.toLong(valOpt.get(cacheKey.toString()));
				if (currentTime > value) {
					valOpt.getOperations().expire(cacheKey.toString(), cacheKey.getExpirationTime(), cacheKey.getExpirationTimeUnit());
					this.lock = true;
					return this.lock;
				}

				int times = 0;
				System.out.println("try get lock times : " + (++times));
				//短暂休眠，避免可能的活锁
				Thread.sleep(3, RANDOM.nextInt(30));
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return this.lock;
	}

	/**
	 * 释放锁
	 */
	public void unlock() {
		try {
			if (this.lock) {
				String value = valOpt.get(cacheKey.toString());
				if (StringUtils.isNotBlank(value) && value.equals(this.lockValue)) {
					valOpt.getOperations().delete(cacheKey.toString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
