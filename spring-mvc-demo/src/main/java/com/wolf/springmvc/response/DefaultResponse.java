package com.wolf.springmvc.response;

public class DefaultResponse implements IResponse {


    public DefaultResponse() {
    }

    @Override
    public Object toResponse() {
        return this;
    }

    public static final IResponse success() {
        return new DefaultResponse();
    }


}
