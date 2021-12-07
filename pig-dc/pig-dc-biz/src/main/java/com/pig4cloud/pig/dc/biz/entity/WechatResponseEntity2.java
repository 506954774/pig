package com.pig4cloud.pig.dc.biz.entity;

import cn.felord.payment.wechat.v3.WechatResponseEntity;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Data;

import java.io.Serializable;

/**
 * WechatResponseEntity2
 * 责任人:  ChenLei
 * 修改人： ChenLei
 * 创建/修改时间: 2021/12/7 10:52
 * Copyright :  版权所有
 **/
@Data
public class WechatResponseEntity2 extends WechatResponseEntity<ObjectNode>  implements Serializable {

	private static final long serialVersionUID = 1L;

}
