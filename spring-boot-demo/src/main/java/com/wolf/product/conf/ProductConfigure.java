package com.wolf.product.conf;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Created by wolf on 16/11/26.
 */
@Configuration
@ComponentScan("com.wolf.product")
@EnableAspectJAutoProxy
public class ProductConfigure {
}
