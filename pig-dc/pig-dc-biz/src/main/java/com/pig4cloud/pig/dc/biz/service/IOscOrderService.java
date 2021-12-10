package com.pig4cloud.pig.dc.biz.service;

import cn.felord.payment.wechat.v3.WechatResponseEntity;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.dc.api.dto.*;
import com.pig4cloud.pig.dc.api.entity.OscOrder;
import com.pig4cloud.pig.dc.api.vo.OrderCountVo;
import com.pig4cloud.pig.dc.api.vo.OrderVo;
import com.pig4cloud.pig.dc.api.vo.WechatPayResponse;

import java.util.List;

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


	/**
	 * @Name:
	 * @Description: 查询订单数量统计
	 * @Param:
	 * @return:
	 * @Author: LeiChen
	 * @Date:2021/12/6 22:25
	 *
	 * */
	List<OrderCountVo> queryOrderStastics(QueryOrderPageDTO dto);

	/**
	 * @Name:
	 * @Description: 查询订单分页列表
	 * @Param:
	 * @return:
	 * @Author: LeiChen
	 * @Date:2021/12/6 22:32
	 *
	 * */
	Page<OrderVo> queryOrderPage(QueryOrderPageDTO dto);

	/**
	 * @Name:
	 * @Description: 取消订单
	 * @Param:
	 * @return:
	 * @Author: LeiChen
	 * @Date:2021/12/7 8:57
	 *
	 * */
	Integer cancelOrder(UserOrderDTO dto);

	/**
	 * @Name:
	 * @Description: 继续支付订单
	 * @Param:
	 * @return:
	 * @Author: LeiChen
	 * @Date:2021/12/7 9:07
	 *
	 * */
	WechatPayResponse continue2Pay(UserOrderDTO dto);

	/**
	 * @Name:
	 * @Description: 根据id查询详情
	 * @Param:
	 * @return:
	 * @Author: LeiChen
	 * @Date:2021/12/7 19:31
	 *
	 * */
	OrderVo details(Integer id);

	/**
	 * @Name:
	 * @Description: 订单核销
	 * @Param:
	 * @return:
	 * @Author: LeiChen
	 * @Date:2021/12/7 19:43
	 *
	 * */
	Integer writeOff(OrderWriteOffDTO dto);

	/**
	 * @Name:
	 * @Description: 查询会员订单分页
	 * @Param:
	 * @return:
	 * @Author: LeiChen
	 * @Date:2021/12/9 10:06
	 *
	 * */
	Page<OrderVo> queryMemberOrderPage(QueryMemberOrderPageDTO dto);

	/**
	 * @Name: 
	 * @Description: 根据id查询会员订单详情 
	 * @Param: 
	 * @return: 
	 * @Author: LeiChen
	 * @Date:2021/12/9 10:57
	 *
	 * */
	
	OrderVo queryMemberOrderdDetails(Integer id);

	/**
	 * @Name:
	 * @Description: 订单全额退款
	 * @Param:
	 * @return:
	 * @Author: LeiChen
	 * @Date:2021/12/10 14:26
	 *
	 * */
    Integer refund(OrderWriteOffDTO dto);
}
