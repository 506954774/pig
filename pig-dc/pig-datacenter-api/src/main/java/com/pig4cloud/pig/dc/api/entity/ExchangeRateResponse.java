package com.pig4cloud.pig.dc.api.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * HL
 * 责任人:  ChenLei
 * 修改人： ChenLei
 * 创建/修改时间: 2021/12/9 9:51
 * Copyright :  版权所有
 **/
@Data
public class ExchangeRateResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private String showapiResError;
	private String showapiResId;
	private Integer showapiResCode;
	private Integer showapiFeeNum;
	private ShowapiResBodyDTO showapiResBody;
}
