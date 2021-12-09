package com.pig4cloud.pig.dc.api.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * ShowapiResBodyDTO
 * 责任人:  ChenLei
 * 修改人： ChenLei
 * 创建/修改时间: 2021/12/9 9:51
 * Copyright :  版权所有
 **/
@Data
public class ShowapiResBodyDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer retCode;
	private String money;
}
