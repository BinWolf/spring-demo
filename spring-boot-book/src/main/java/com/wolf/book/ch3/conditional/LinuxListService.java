package com.wolf.book.ch3.conditional;

/**
 * Created by wolf on 16/12/29.
 */
public class LinuxListService implements ListService {
    @Override
    public String showListCmd() {
        return "ls";
    }
}
