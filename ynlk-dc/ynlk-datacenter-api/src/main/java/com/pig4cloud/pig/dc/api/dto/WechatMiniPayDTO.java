package com.pig4cloud.pig.dc.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 供应商表
 * </p>
 *
 * @author chenlei
 * @since 2021-11-13
 */
@Data
@ApiModel(value="微信小程序,预支付,参数", description="微信小程序,预支付,参数")
public class WechatMiniPayDTO implements Serializable {

    private static final long serialVersionUID = 1L;


	@NotNull(message = "openId不可为空")
    @ApiModelProperty( value = "openId" , required = true)
    private String openId;


	@ApiModelProperty( value = "商品列表" , required = true)
	private List<WechatMiniPayGoodsDTO> goods;

	@NotNull(message = "类型不可为空")
	@ApiModelProperty( value = "类型 0:购买会员 1:购买产品" , required = true)
	private Integer type;


	@ApiModelProperty( value = "用户备注" , required = true)
	private String userRemark;
}
