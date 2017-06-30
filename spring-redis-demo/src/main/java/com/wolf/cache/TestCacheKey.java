package com.wolf.cache;

import java.util.concurrent.TimeUnit;

/**
 * Created by wolf on 17/6/7.
 */
public class TestCacheKey extends ProjectCacheKey {
	@Override
	public String getSuffixKey() {
		return "test";
	}

	@Override
	public int getExpirationTime() {
		return 30;
	}

	@Override
	public TimeUnit getExpirationTimeUnit() {
		return TimeUnit.SECONDS;
	}
}
