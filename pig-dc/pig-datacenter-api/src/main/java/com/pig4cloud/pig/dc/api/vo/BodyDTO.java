package com.pig4cloud.pig.dc.api.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * BodyDTO
 * 责任人:  ChenLei
 * 修改人： ChenLei
 * 创建/修改时间: 2021/12/7 11:26
 * Copyright :  版权所有
 **/
@Data
public class BodyDTO implements Serializable {

	private String prepayId;
	private String appId;
	private String timeStamp;
	private String nonceStr;
	private String packageX;
	private String signType;
	private String paySign;
}
