package com.wolf.lock;

import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.*;

/**
 * Created by wolf on 17/6/7.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CacheLock {
	String cacheKey() default StringUtils.EMPTY;
}
