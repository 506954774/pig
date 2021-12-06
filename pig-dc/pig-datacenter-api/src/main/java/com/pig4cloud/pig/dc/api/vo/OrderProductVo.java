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
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author lengleng
 * @date 2020/2/10
 */
@Data
@ApiModel(value = "订单产品vo")
public class OrderProductVo implements Serializable {



	@ApiModelProperty( value = "产品名称" , required = true)
	private String productName;

	@ApiModelProperty( value = "产品数量" , required = true)
	private Integer productQuantity;

	@ApiModelProperty( value = "产品总价,人民币,元" , required = true)
	private BigDecimal productTotalPrice;

	@ApiModelProperty( value = "产品单价,人民币,元" , required = true)
	private BigDecimal productSinglePrice;




}
