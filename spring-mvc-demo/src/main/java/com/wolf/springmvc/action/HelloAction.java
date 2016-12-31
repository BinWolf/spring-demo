package com.wolf.springmvc.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by wolf on 16/12/30.
 */

@Controller
public class HelloAction {

    @RequestMapping("index")
    public String hello() {
        return "index";
    }

}
