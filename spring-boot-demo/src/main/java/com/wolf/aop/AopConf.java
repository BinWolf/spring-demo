package com.wolf.aop;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Created by wolf on 16/11/22.
 */

@Configuration
@ComponentScan("com.wolf.aop")
@EnableAspectJAutoProxy
public class AopConf {
}
