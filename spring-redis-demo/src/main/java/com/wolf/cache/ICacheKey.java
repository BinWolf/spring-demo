package com.wolf.cache;

import java.util.concurrent.TimeUnit;

/**
 * 对key统一管理
 */
public abstract class ICacheKey {

	public static final String CACHE_KEY_SEPARATOR = ":";
	
	/**
	 * cache key前缀
	 * @return Key
	 */
	public abstract String getPrefixKey();

	/**
	 * cache key后缀
	 * @return
	 */
	public abstract String getSuffixKey();
	
	/**
	 * 设置过期时间(-1表示永不过期)
	 * @return
	 */
	public int getExpirationTime() {
		return -1;
	}

	/**
	 * 过期时间单位
	 * @return
	 */
	public TimeUnit getExpirationTimeUnit() {
		return TimeUnit.SECONDS;
	}
	
	
	@Override
	public String toString() {
		return getPrefixKey() + CACHE_KEY_SEPARATOR + getSuffixKey();
	}
	
}
