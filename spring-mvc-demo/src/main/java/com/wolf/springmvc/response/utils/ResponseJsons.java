package com.wolf.springmvc.response.utils;

import com.wolf.springmvc.response.CommonResponse;
import org.springframework.http.ResponseEntity;

/**
 * Created by zhenghualong on 2016/11/23.
 */
public class ResponseJsons {

    public static ResponseEntity<String> ok(Object obj){
        return ResponseEntity.ok(String.valueOf(obj));
    }

    public static ResponseEntity<String> ok(){
        return ResponseEntity.ok(String.valueOf(new CommonResponse<Object>()));
    }
}
