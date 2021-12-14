package com.pig4cloud.pig.dc.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * WebcastPageQo
 * 责任人:  ChenLei
 * 修改人： ChenLei
 * 创建/修改时间: 2021/12/14 11:17
 * Copyright :  版权所有
 **/
@NoArgsConstructor
@Data
public class WebcastPageQo implements Serializable {


	@ApiModelProperty( value = "起始拉取房间，start = 0 表示从第 1 个房间开始拉取" , required = true)
	private Integer start=0;

	@ApiModelProperty( value = "每次拉取的个数上限，不要设置过大，建议 100 以内" , required = true)
	private Integer limit=100;
}
