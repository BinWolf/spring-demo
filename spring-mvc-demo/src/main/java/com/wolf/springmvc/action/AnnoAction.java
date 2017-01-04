package com.wolf.springmvc.action;

import com.wolf.springmvc.vo.UserForm;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by wolf on 16/12/31.
 */
@Controller
@RequestMapping("/anno")
public class AnnoAction {

    @RequestMapping("/normalPage")
    public String junitTest(Model model){
        model.addAttribute("testMsg", "junit测试信息");
        return "page";
    }

    /**
     * 默认是类的请求路径
     * @param request
     * @return
     */
    @RequestMapping(produces = "text/pain;charset=UTF-8")
    public @ResponseBody String index(HttpServletRequest request){
        return "url : " + request.getRequestURI() + " can access";
    }

    /**
     * restful请求，把请求的占位符绑定到参数
     * @param msg
     * @param request
     * @return
     */
    @RequestMapping(value = "/pathvar/{msg}", produces = "text/pain;charset=UTF-8")
    public @ResponseBody String pathVariable(@PathVariable String msg, HttpServletRequest request){
        return "url : " + request.getRequestURI() + " can access, msg : " + msg;
    }

    /**
     * 参数直接注入
     * @param msg 参数名必须一致
     * @param request
     * @return
     */
    @RequestMapping(value = "/requestParam", produces = "text/pain;charset=UTF-8")
    public @ResponseBody String requestParam(String msg, HttpServletRequest request){
        return "url : " + request.getRequestURI() + " can access, msg : " + msg;
    }

    /**
     * 以对象形式注入
     * @param user
     * @param request
     * @return
     */
    @RequestMapping(value = "/passObject", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String passObject(UserForm user, HttpServletRequest request){
        return "url : " + request.getRequestURI() + " can access, user : " + ToStringBuilder.reflectionToString(user);
    }

    @RequestMapping(value = "/getJson", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public UserForm getJson(UserForm user){
        return new UserForm(user.getName() + "11", user.getAge() + 1);
    }

    @RequestMapping(value = "/getXml", produces = "application/xml;charset=UTF-8")
    @ResponseBody
    public UserForm getXml(UserForm user){
        return new UserForm(user.getName() + "11", user.getAge() + 1);
    }

}
