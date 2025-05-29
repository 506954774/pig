package com.pig4cloud.pig.dc.api.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * WechatPayResponse
 * 责任人:  ChenLei
 * 修改人： ChenLei
 * 创建/修改时间: 2021/12/7 11:26
 * Copyright :  版权所有
 **/
@Data
public class WechatPayResponse implements Serializable {


	private Integer httpStatus;
	private BodyDTO body;
	private Boolean $1xxinformational;
	private Boolean $2xxsuccessful;
	private Boolean $3xxredirection;
	private Boolean $4xxclienterror;
	private Boolean $5xxservererror;
}
