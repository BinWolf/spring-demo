package com.wolf.springmvc.response.spring;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.spring.FastJsonJsonView;
import com.wolf.springmvc.error.ErrorEntity;
import com.wolf.springmvc.response.CommonResponse;
import com.wolf.springmvc.response.CommonResponseValueFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.Map;


public class ErrorEntityJsonView extends FastJsonJsonView {

    private ErrorEntity errorEntity;
    private CommonResponseValueFilter commonResponseValueFilter;

    ErrorEntityJsonView(ErrorEntity errorEntity) {
        this.errorEntity = errorEntity;
        this.commonResponseValueFilter = new CommonResponseValueFilter();
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
                                           HttpServletResponse response) throws Exception {
        Object value = filterAndWrapEntity();

        String text = JSON.toJSONString(value, commonResponseValueFilter, SerializerFeature.DisableCircularReferenceDetect);
        byte[] bytes = text.getBytes(getCharset());

        OutputStream stream = response.getOutputStream();
        stream.write(bytes);

    }

    private Object filterAndWrapEntity() {
        CommonResponse<Object> commonResponse = new CommonResponse<>();
        commonResponse.setError(errorEntity);
        commonResponse.setData(new Object());
        return commonResponse;
    }
}
