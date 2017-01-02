package com.wolf.springmvc.response.spring;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.wolf.springmvc.response.CommonResponse;
import com.wolf.springmvc.response.CommonResponseValueFilter;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by zhenghualong on 2016/12/2.
 */
public class CommonResponseFastJsonConverter extends FastJsonHttpMessageConverter {

    private CommonResponseValueFilter responseValueFilter;

    public CommonResponseFastJsonConverter() {
        this.responseValueFilter = new CommonResponseValueFilter();
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return CommonResponse.class.isAssignableFrom(clazz);
    }

    @Override
    protected void writeInternal(Object obj, HttpOutputMessage outputMessage) throws IOException,
            HttpMessageNotWritableException {
        OutputStream out = outputMessage.getBody();
        String text = JSON.toJSONString(obj, responseValueFilter, SerializerFeature.DisableCircularReferenceDetect);
        byte[] bytes = text.getBytes(getCharset());
        out.write(bytes);
    }
}
