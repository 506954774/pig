package com.pig4cloud.pig.dc.biz.service;

import cn.felord.payment.wechat.v3.WechatResponseEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.dc.api.dto.WechatMiniPayDTO;
import com.pig4cloud.pig.dc.api.entity.OscOrder;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author chenlei
 * @since 2021-12-05
 */
public interface IOscOrderService extends IService<OscOrder> {

	/**
	 * @Name:
	 * @Description: 微信预支付
	 * @Param:
	 * @return:
	 * @Author: LeiChen
	 * @Date:2021/12/5 13:36
	 *
	 * */
	WechatResponseEntity<ObjectNode> prepay(WechatMiniPayDTO payDTO);

	/**
	 * @Name:
	 * @Description: 调用微信接口,发起预支付
	 * @Param:
	 * @return:
	 * @Author: LeiChen
	 * @Date:2021/12/5 14:44
	 *
	 * */
	WechatResponseEntity<ObjectNode> wechatPrepay(OscOrder order );

	/**
	 * @Name:
	 * @Description: 查询订单支付状态
	 * @Param:
	 * @return:
	 * @Author: LeiChen
	 * @Date:2021/12/6 17:06
	 *
	 * */
	Integer queryPayResult(String prepayId);
}
