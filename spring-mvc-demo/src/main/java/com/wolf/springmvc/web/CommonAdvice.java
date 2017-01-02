package com.wolf.springmvc.web;

import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by wolf on 17/1/2.
 * @ControllerAdvice 全局@Controller处理，有注解@Controller类中的@RequestMapping的方法都起作用
 */

@ControllerAdvice
public class CommonAdvice {

    /**
     * @Controller 中的@RequestMapping的方法抛出Exception类型异常都会经过该方法处理
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public ModelAndView exception(Exception e, WebRequest webRequest) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("errorMessage", e.getMessage());
        return modelAndView;
    }

    /**
     * 给所有 @RequestMapping 的方法注入msg参数
     * @param model
     */
    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("msg", "额外信息");
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.setDisallowedFields("age");   //忽略这个字段传值
    }
}
