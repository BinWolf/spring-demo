package com.wolf.springmvc.error;

/**
 * 业务异常类
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 3190256515490831676L;

    private String message;
    private Object[] params;
    private ErrorEntity errorEntity;

    public BusinessException(ErrorEntity errorEntity) {
        this.errorEntity = errorEntity;
        this.message = errorEntity.getErrorMessage();
    }

    public BusinessException(ErrorEntity errorEntity, String message, Object... params) {
        this.errorEntity = errorEntity;
        this.message = errorEntity.getErrorMessage() + "," + message;
        this.params = params;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }

    public ErrorEntity getErrorEntity() {
        return errorEntity;
    }

    public void setErrorEntity(ErrorEntity errorEntity) {
        this.errorEntity = errorEntity;
    }

    public static BusinessException error(ErrorEntity errorEntity) {
        return new BusinessException(errorEntity);
    }

    public static BusinessException error(ErrorEntity errorEntity, String message) {
        return new BusinessException(errorEntity, message, null);
    }

    public static BusinessException error(ErrorEntity errorEntity, String message, Object... params) {
        return new BusinessException(errorEntity, message, params);
    }
}
