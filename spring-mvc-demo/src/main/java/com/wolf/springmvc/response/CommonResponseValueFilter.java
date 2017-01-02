package com.wolf.springmvc.response;

import com.alibaba.fastjson.serializer.ValueFilter;

public class CommonResponseValueFilter implements ValueFilter {

    @Override
    public Object process(Object obj, String s, Object v) {
        if (obj instanceof CommonResponse && v == null) {
            return "";
        }
        return v;
    }

}
