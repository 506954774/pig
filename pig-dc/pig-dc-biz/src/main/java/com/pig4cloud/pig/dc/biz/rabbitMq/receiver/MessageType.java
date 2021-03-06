package com.pig4cloud.pig.dc.biz.rabbitMq.receiver;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * MessageType
 * 责任人:  ChenLei
 * 修改人： ChenLei
 * 创建/修改时间: 2021/12/5 20:35
 * Copyright :  版权所有
 **/
@Data
public class MessageType implements Serializable {


	public static final String TYPE_OREDER_CANCEL="TYPE_OREDER_CANCEL";


	@NotNull(message = "消息类型不可为空")
	@NotEmpty(message = "消息类型不可为空")
	private String type;
	private Integer id;

}
