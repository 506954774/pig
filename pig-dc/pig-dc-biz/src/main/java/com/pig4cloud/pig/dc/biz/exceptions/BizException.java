package com.pig4cloud.pig.dc.biz.exceptions;

/**
 * BizException
 * 业务异常
 * 责任人:  ChenLei
 * 修改人： ChenLei
 * 创建/修改时间: 2021/8/6 17:18
 * Copyright :  版权所有
 **/
public class BizException extends RuntimeException{
    public BizException(String message) {
        super(message);
    }
}
