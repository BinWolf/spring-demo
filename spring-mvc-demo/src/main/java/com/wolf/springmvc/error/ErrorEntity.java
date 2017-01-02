package com.wolf.springmvc.error;

public abstract class ErrorEntity{
	private String errorCode;
	private String errorMessage;
	
	protected abstract String getHeadCode();
	
	public ErrorEntity(String errorCode,String errorMessage){
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
	public String getUniqErrorCode() {
		return getHeadCode()+errorCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
}
