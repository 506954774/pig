/*
 * Copyright (c) 2020 pig4cloud Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pig4cloud.pig.dc.api.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author lengleng
 * @date 2020/2/10
 */
@Data
@ApiModel(value = "订单vo")
public class OrderVo  implements Serializable {


	@ApiModelProperty( value = "主键" , required = true)
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;


	@ApiModelProperty( value = "订单状态: 0预支付 1已取消 2已支付 3申请退款中 4,已全部退款完毕 5已核销 " , required = true)
	private Integer orderStatus;


	@ApiModelProperty( value = "订单编号" , required = true)
	private String orderNum;


	@ApiModelProperty( value = "第三方流水id" , required = true)
	private String thirdPartyId;


	@ApiModelProperty( value = "创建时间" , required = true)
	private Date createTime;


	@ApiModelProperty( value = "订单取消时间" , required = true)
	private Date cancelTime;

	@ApiModelProperty( value = "订单支付时间" , required = true)
	private Date payTime;


	@ApiModelProperty( value = "核销时间" , required = true)
	private Date writeOffTime;



	@ApiModelProperty( value = "用户id" , required = true)
	private Integer userId;



	@ApiModelProperty( value = "订单支付总额,人民币,元" , required = true)
	private BigDecimal orderAmount;



////////////////////////////////////////

	@ApiModelProperty( value = "姓名" , required = true)
	private String realname;


	@ApiModelProperty( value = "电话号码" , required = true)
	private String phone;


	@ApiModelProperty( value = "昵称" , required = true)
	private String nickname;


	@ApiModelProperty( value = "头像" , required = true)
	private String avatar;

//////////////////////////////////////////////////////////

    @ApiModelProperty( value = "订单的产品列表" , required = true)
	private List<OrderProductVo> products;




}
