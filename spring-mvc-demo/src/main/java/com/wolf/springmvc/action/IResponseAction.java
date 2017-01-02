package com.wolf.springmvc.action;

import com.wolf.springmvc.error.BusinessAssert;
import com.wolf.springmvc.error.CommonError;
import com.wolf.springmvc.response.IResponse;
import com.wolf.springmvc.response.MessageResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by wolf on 17/1/2.
 * IResponse或者BusinessException异常返回值demo
 */

@Controller
public class IResponseAction {

    @RequestMapping("/iResponse")
    public IResponse testIResponse(){
        MessageResponse response = new MessageResponse();
        response.setMsg("IResponse demo");
        return response;
    }

    @RequestMapping("/business")
    public IResponse businessDemo(String name){
        BusinessAssert.isTrue("wolf".equals(name), CommonError.ARGS_ERROR,"argument error");
        return new MessageResponse(name);
    }
}
