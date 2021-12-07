package com.pig4cloud.pig.dc.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 供应商表
 * </p>
 *
 * @author chenlei
 * @since 2021-11-13
 */
@Data
@ApiModel(value="订单核销,入参父类", description="订单核销,入参父类")
public class OrderWriteOffDTO implements Serializable {

    private static final long serialVersionUID = 1L;


    @NotNull(message = "订单id不可为空")
	@ApiModelProperty( value = " 订单id " , required = true)
	private Integer orderId;







}
