package com.wolf.springmvc.error;


public class CommonError extends ErrorEntity {

	private CommonError(String errorCode, String errorMessage) {
		super(errorCode, errorMessage);
	}

	@Override
	public String getHeadCode() {
		return HeadCode.COMMON_HEAD_CODE;
	}

	/**
	 * 参数不能为空
	 */
	public static final ErrorEntity ARGS_EMPTY = new CommonError("001", "参数不能为空");

	/**
	 * 参数错误
	 */
	public static final ErrorEntity ARGS_ERROR = new CommonError("002", "参数错误");

	/**
	 * 系统错误
	 */
	public static final ErrorEntity SERVER_ERROR = new CommonError("003", "系统错误");
	
	/**
	 * 异常请求
	 */
	public static final ErrorEntity INVALID_REQUEST = new CommonError("004", "请求异常");

}
