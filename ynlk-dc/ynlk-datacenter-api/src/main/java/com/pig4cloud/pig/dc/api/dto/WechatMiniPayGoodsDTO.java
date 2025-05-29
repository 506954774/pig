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
@ApiModel(value="商品参数", description="商品参数")
public class WechatMiniPayGoodsDTO implements Serializable {

    private static final long serialVersionUID = 1L;


    @NotNull(message = "商品id不可为空")
    @ApiModelProperty( value = "商品id" , required = true)
    private Integer goodsId;

	@NotNull(message = "商品数量不可为空")
	@ApiModelProperty( value = "数量" , required = true)
	private Integer quantity=1;



}
