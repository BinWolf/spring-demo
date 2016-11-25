package com.wolf.entity;

import org.springframework.beans.factory.annotation.Configurable;

/**
 * Created by wolf on 16/10/24.
 */
@Configurable
public class Person {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
