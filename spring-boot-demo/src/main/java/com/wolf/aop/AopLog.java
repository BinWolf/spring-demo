package com.wolf.aop;

import java.lang.annotation.*;

/**
 * Created by wolf on 16/11/22.
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AopLog {
    String print() default "aoplog print something.....";
}
