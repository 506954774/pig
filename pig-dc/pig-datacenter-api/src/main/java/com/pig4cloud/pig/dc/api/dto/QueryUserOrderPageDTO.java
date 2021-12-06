package com.pig4cloud.pig.dc.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 * 供应商表
 * </p>
 *
 * @author chenlei
 * @since 2021-11-13
 */
@Data
@ApiModel(value="user订单分页接口,入参父类", description="user订单分页接口,入参父类")
public class QueryUserOrderPageDTO implements Serializable {

    private static final long serialVersionUID = 1L;


	@ApiModelProperty( value = "订单状态: 0预支付 1已取消 2已支付 3申请退款中 4,已全部退款完毕 5已核销 " , required = true)
	private Integer orderStatus;

	@NotNull(message = "用户id 不可为空")
	@ApiModelProperty( value = " 用户id " , required = true)
	private Integer userId;


    @ApiModelProperty( value = "每页数量" , required = true)
    private long size=10L;

    @ApiModelProperty( value = "页码" , required = true)
    private long current=1L;





}
