package com.wolf.book.ch2.prepost;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Created by wolf on 16/12/23.
 */
public class JSR250WayService {

    public JSR250WayService() {
        System.out.println("JSR250WayService constructor called");
    }

    @PostConstruct
    public void init() {
        System.out.println("JSR250-init-method");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("JSR250-destroy-method");
    }
}
