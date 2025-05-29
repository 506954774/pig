package com.pig4cloud.pig.dc.biz.config;

import com.google.common.base.Throwables;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.security.exception.UnauthorizedException;
import com.pig4cloud.pig.dc.biz.exceptions.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * MyExceptionHandlerAdvice
 * 责任人:  ChenLei
 * 修改人： ChenLei
 * 创建/修改时间: 2021/8/6 17:21
 * Copyright :  版权所有
 **/
@Slf4j
@ControllerAdvice
public class MyExceptionHandlerAdvice {

	/**
	 * 401 异常
	 * 这个异常是common-Feign 包里面的restTeplate抛出来的
	 * @param request
	 * @param response
	 * @param handler
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(UnauthorizedException.class)
	@ResponseBody
	R handleUnauthorizedException(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler, Throwable ex) {
		log.error("handleUnauthorizedException",ex);
		response.setStatus(401);
		R<String> result=new R<String>();
		result.setCode(401);
		result.setMsg("无效的tokentoken");
		result.setData("无效的tokentoken");
		return result;
	}

	/**
	 * 处理业务异常
	 * @param request
	 * @param response
	 * @param handler
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(HttpClientErrorException.class)
	@ResponseBody
	R handleHttpClientErrorException(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler, Throwable ex) {
		log.error("handleHttpClientErrorException",ex);
		return R.failed(ex.getMessage());
	}

    /**
     * 处理业务异常
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @return
     */
    @ExceptionHandler(BizException.class)
    @ResponseBody
    R handleBizException(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler, Throwable ex) {
        return R.failed(ex.getMessage());
    }

    /**
     * 处理业务异常
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    R handleException(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler, Throwable ex) {
        return R.failed(Throwables.getStackTraceAsString(ex));
    }

    /**
     * 处理参数异常
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    R handleMethodArgumentNotValidException(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler, Throwable ex) {
        MethodArgumentNotValidException exception= (MethodArgumentNotValidException) ex;
        return R.failed(exception.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }

/*    *//**
     * 处理业务异常
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @return
     *//*
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    R handleException(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler, Throwable ex) {
        return R.failed(Throwables.getStackTraceAsString(ex));
    }*/

}
