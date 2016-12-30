package com.wolf.book.ch3.conditional;

/**
 * Created by wolf on 16/12/29.
 */
public class WindowsListService implements ListService {

    public WindowsListService() {
        System.out.println("WindowsListService");
    }

    @Override
    public String showListCmd() {
        return "dir";
    }

}
