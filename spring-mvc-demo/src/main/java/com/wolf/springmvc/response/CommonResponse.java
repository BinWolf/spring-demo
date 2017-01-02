package com.wolf.springmvc.response;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.wolf.springmvc.error.ErrorEntity;

import java.util.HashMap;
import java.util.Map;

public class CommonResponse<T> {

    private static ValueFilter NULL_AS_EMPTY_FILTER = new ValueFilter() {
        @Override
        public Object process(Object obj, String s, Object v) {
            if (obj instanceof CommonResponse && v == null) {
                return "";
            }
            return v;
        }
    };

    @JSONField(name = "isError")
    private boolean isError = false;

    private String errorCode = "";

    private String errorMessage = "";

    private T data;

    private transient ErrorEntity errorEntity = null;

    public void setError(ErrorEntity error) {
        setError(true);
        setErrorCode(error.getUniqErrorCode());
        setErrorMessage(error.getErrorMessage());
        this.errorEntity = error;
    }

    public ErrorEntity getErrorEntity() {
        return errorEntity;
    }

    public boolean isError() {
        return isError;
    }

    public void setError(boolean isError) {
        this.isError = isError;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static CommonResponse<Object> success() {
        return new CommonResponse<>();
    }

    public static <T> CommonResponse<T> success(T data) {
        CommonResponse<T> commonResponse = new CommonResponse<>();
        commonResponse.setData(data);
        return commonResponse;
    }

    public static CommonResponse<Object> success(String msg) {
        CommonResponse<Object> commonResponse = new CommonResponse<>();
        Map<String, String> msgMap = new HashMap<String, String>();
        msgMap.put("msg", msg);
        commonResponse.setData(msgMap);
        return commonResponse;
    }
    
    public static CommonResponse<Object> error(ErrorEntity error){
        CommonResponse<Object> commonResponse = new CommonResponse<>();
        commonResponse.setError(error);
        return commonResponse;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this, NULL_AS_EMPTY_FILTER, SerializerFeature.DisableCircularReferenceDetect);
    }

 /*   public byte[] toBytes() {
        return StringUtils.getBytesUtf8(this.toString());
    }*/

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("isError", isError);
        map.put("errorCode", errorCode);
        map.put("errorMessage", errorMessage);
        if (data == null) {
            map.put("data", "");
        } else {
            map.put("data", data);
        }
        return map;
    }

}
