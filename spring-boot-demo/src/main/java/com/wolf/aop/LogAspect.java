package com.wolf.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * Created by wolf on 16/11/22.
 */
@Aspect
@Component
public class LogAspect {

    @Pointcut("@annotation(com.wolf.aop.AopLog)")
    public void annotationPointCut() {}

    @After("annotationPointCut()")
    public void after(JoinPoint joinpoint) {
        MethodSignature methodSignature = (MethodSignature) joinpoint.getSignature();
        Method method = methodSignature.getMethod();
        System.out.println(method.getName());
        AopLog aopLog = method.getAnnotation(AopLog.class);
        System.out.println(aopLog.print());
    }
}
