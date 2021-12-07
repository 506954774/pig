package com.pig4cloud.pig.dc.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 用户订单
 * </p>
 *
 * @author chenlei
 * @since 2021-11-13
 */
@Data
@ApiModel(value="用户订单, ,参数", description="用户订单,参数")
public class UserOrderDTO implements Serializable {

    private static final long serialVersionUID = 1L;



	@NotNull(message = "用户id不可为空")
	@ApiModelProperty( value = "用户id" , required = true)
	private Integer userId;

	@NotNull(message = "订单id不可为空")
	@ApiModelProperty( value = "订单id" , required = true)
	private Integer orderId;
}
