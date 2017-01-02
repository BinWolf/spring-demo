package com.wolf.springmvc.vo;

/**
 * Created by wolf on 16/12/31.
 */
public class UserForm {
    private String name;
    private int age;

    public UserForm() {
    }

    public UserForm(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
