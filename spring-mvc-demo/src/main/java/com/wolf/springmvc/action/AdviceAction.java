package com.wolf.springmvc.action;

import com.wolf.springmvc.vo.UserForm;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by wolf on 17/1/2.
 * 测试全局抛出异常类
 */

@Controller
public class AdviceAction {

    @RequestMapping("/advice")
    public String getSomething(@ModelAttribute("msg") String msg, UserForm userForm) {
        System.out.println("userName : " + userForm.getName() +" ; user age : "+ userForm.getAge());
        throw new IllegalArgumentException("AdviceAction advice method argument exception, from @ModelAttribute: " + msg);
    }

}
