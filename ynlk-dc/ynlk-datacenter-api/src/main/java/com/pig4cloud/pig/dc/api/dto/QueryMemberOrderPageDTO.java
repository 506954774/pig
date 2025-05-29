package com.pig4cloud.pig.dc.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
@ApiModel(value="会员订单分页接口,入参父类", description="会员订单分页接口,入参父类")
public class QueryMemberOrderPageDTO implements Serializable {

    private static final long serialVersionUID = 1L;


	@ApiModelProperty( value = "订单状态: 0预支付 1已取消 2已支付 3申请退款中 4,已全部退款完毕 5已核销 " , required = true)
	private Integer orderStatus;

	@ApiModelProperty( value = " 用户id " , required = true)
	private Integer userId;

	@ApiModelProperty( value = "关键字" , required = true)
    private String keyword;

    @ApiModelProperty( value = "每页数量" , required = true)
    private long size=10L;

    @ApiModelProperty( value = "页码" , required = true)
    private long current=1L;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty( value = "创建时间,开始" , required = true)
	private Date createTimeStart;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty( value = "创建时间,结束" , required = true)
	private Date createTimeEnd;



}
