package com.wolf.springmvc.response.spring;

import com.wolf.springmvc.response.CommonResponse;
import com.wolf.springmvc.response.IResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.HttpEntityMethodProcessor;

import java.io.IOException;
import java.util.List;

/**
 * 处理返回值为IResponse的方法的json格式
 */
public class IResponseMethodProcessor extends HttpEntityMethodProcessor{

    public IResponseMethodProcessor(List<HttpMessageConverter<?>> converters) {
        super(converters);
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return false;
    }

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return IResponse.class.isAssignableFrom(returnType.getParameterType());
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws IOException, HttpMediaTypeNotAcceptableException, HttpMessageNotWritableException {
        mavContainer.setRequestHandled(true);
        ServletServerHttpRequest inputMessage = this.createInputMessage(webRequest);
        ServletServerHttpResponse outputMessage = this.createOutputMessage(webRequest);
        CommonResponse<Object> commonResponse = new CommonResponse<>();
        IResponse response = (IResponse) returnValue;
        if (response != null) {
            commonResponse.setData(response.toResponse());
        }
        this.writeWithMessageConverters(commonResponse, returnType, inputMessage, outputMessage);
    }

}
