package com.wolf.springmvc.error;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;

/**
 * 借鉴spring validate的做法
 */
public class BusinessAssert {


    /**
     * 数据为NULL则抛异常
     *
     * @param obj
     * @param errorEntity
     * @param msg
     * @param params
     */
    public static void isNotNull(Object obj, ErrorEntity errorEntity, String msg, Object... params) {
        if (obj == null) {
            throw BusinessException.error(errorEntity, msg, params);
        }
    }


    /**
     * 字符串为空抛异常
     *
     * @param value
     * @param errorEntity
     * @param msg
     * @param params
     */
    public static void isNotBlank(String value, ErrorEntity errorEntity, String msg, Object... params) {
        if (StringUtils.isBlank(value)) {
            throw BusinessException.error(errorEntity, msg, params);
        }
    }


    /**
     * false则抛异常
     *
     * @param isTrue
     * @param errorEntity
     * @param msg
     * @param params
     */
    public static void isTrue(Boolean isTrue, ErrorEntity errorEntity, String msg, Object... params) {
        if (isTrue == null || !isTrue) {
            throw BusinessException.error(errorEntity, msg, params);
        }
    }

    /**
     * 集合为空则抛异常
     *
     * @param collection
     * @param errorEntity
     * @param msg
     * @param params
     */
    public static void isNotEmpty(Collection collection, ErrorEntity errorEntity,String msg, Object... params) {
        if (CollectionUtils.isEmpty(collection)) {
            throw BusinessException.error(errorEntity, msg, params);
        }
    }
}
