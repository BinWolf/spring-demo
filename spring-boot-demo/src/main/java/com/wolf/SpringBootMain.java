package com.wolf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by wolf on 16/10/24.
 */
@SpringBootApplication
@Controller
public class SpringBootMain {

    @RequestMapping("/")
    public String index(){
        return  "index";
    }

    @RequestMapping("/hello")
    public String hello() {
        return "hello";
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootMain.class);
    }
}
