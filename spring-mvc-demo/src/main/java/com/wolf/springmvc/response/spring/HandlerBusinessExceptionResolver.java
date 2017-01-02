package com.wolf.springmvc.response.spring;

import com.wolf.springmvc.error.BusinessException;
import com.wolf.springmvc.error.CommonError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 统一异常处理
 */
public class HandlerBusinessExceptionResolver implements HandlerExceptionResolver {

    private static final Logger LOGGER = LoggerFactory.getLogger(HandlerBusinessExceptionResolver.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) {
        if (exception instanceof BusinessException) {
            return this.handleBusinessException((BusinessException) exception);
        } else {
            return this.handleSystemException(exception);
        }
    }

    /**
     * 处理业务异常
     *
     * @param exception
     * @return
     */
    protected ModelAndView handleBusinessException(BusinessException exception) {
        LOGGER.info(exception.getMessage(), exception.getParams());
        ErrorEntityJsonView errorEntityJsonView = new ErrorEntityJsonView(exception.getErrorEntity());
        return new ModelAndView(errorEntityJsonView);
    }

    /**
     * 处理系统异常
     *
     * @param exception
     * @return
     */
    protected ModelAndView handleSystemException(Exception exception) {
        LOGGER.error(exception.getMessage(), exception);
        ErrorEntityJsonView errorEntityJsonView = new ErrorEntityJsonView(CommonError.SERVER_ERROR);
        return new ModelAndView(errorEntityJsonView);
    }
}
