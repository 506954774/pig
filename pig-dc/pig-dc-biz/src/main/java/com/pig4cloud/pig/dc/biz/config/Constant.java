package com.pig4cloud.pig.dc.biz.config;

import java.math.BigDecimal;

/**
 * Constan
 * 责任人:  ChenLei
 * 修改人： ChenLei
 * 创建/修改时间: 2021/11/21 17:43
 * Copyright :  版权所有
 **/
public class Constant {

	public static final String APP_ID="";
	public static final String APP_SECRET="";
	public static final String TOKEN="";
	public static final String AES_KEY="";
	public static final String PASSWORD = "123456";
	public static final String OPERATED = "1";
	public static final String TANANTID = "miniapp";

	//系统参数:会员价格
	public static final String MEMBER_PRICE = "MEMBER_PRICE";
	public static final int ORDER_LIMIT = 100000;
	//微信是按分传参的
	public static final int PRICE_TIME = 100;
	public static final BigDecimal TIMES= new BigDecimal(PRICE_TIME);
	public static final String CNY = "CNY";
	public static final String PREPAY_ID = "prepay_id";
	public static final String SEPCTOR = ",";
	//没有支付则自动设置为已取消
	public static final Long DELAY_TIME = 5*60*1000L;
	public static final String MEMBER_FLAG = "1";
}
