package com.wolf.springmvc.response;

public class MessageResponse implements IResponse {

    protected String msg;

    public MessageResponse() {
    }

    public MessageResponse(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public Object toResponse() {
        return this;
    }

    public static final IResponse success() {
        return new MessageResponse();
    }

    public static final IResponse success(String msg) {
        return new MessageResponse(msg);
    }

}
