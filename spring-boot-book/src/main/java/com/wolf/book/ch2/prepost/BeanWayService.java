package com.wolf.book.ch2.prepost;

/**
 * Created by wolf on 16/12/23.
 */
public class BeanWayService {

    public BeanWayService() {
        System.out.println("BeanWayService constructor called");
    }

    public void init() {
        System.out.println("@Bean-init-method");
    }

    public void destroy() {
        System.out.println("@Bean-destroy-method");
    }
}
