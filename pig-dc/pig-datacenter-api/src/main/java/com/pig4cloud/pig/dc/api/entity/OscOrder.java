package com.pig4cloud.pig.dc.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 订单
 * </p>
 *
 * @author chenlei
 * @since 2021-12-05
 */
@Data
@ApiModel(value="OscOrder对象", description="订单")
public class OscOrder extends Model<OscOrder> {

    private static final long serialVersionUID = 1L;



    @ApiModelProperty( value = "主键" , required = true)
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;


	@ApiModelProperty( value = "用户微信openid" , required = true)
	private String openId;

	@ApiModelProperty( value = "用户id" , required = true)
	private Integer userId;


	@ApiModelProperty( value = "订单编号" , required = true)
    private String orderNum;

    @ApiModelProperty( value = "0:会员  1:商品" , required = true)
    private Integer orderType;

	@ApiModelProperty( value = "描述" , required = true)
	private String orderDesc;

    @ApiModelProperty( value = "0:微信支付" , required = true)
    private Integer payType;

    @ApiModelProperty( value = "订单支付总额,人民币,元" , required = true)
    private BigDecimal orderAmount;

    @ApiModelProperty( value = "订单状态: 0预支付 1已取消 2已支付 3申请退款中 4,已全部退款完毕 5已核销 " , required = true)
    private Integer orderStatus;

    @ApiModelProperty( value = "订单取消时间" , required = true)
    private Date cancelTime;

    @ApiModelProperty( value = "订单支付时间" , required = true)
    private Date payTime;

    @ApiModelProperty( value = "退款申请时间" , required = true)
    private Date refundApplyTime;

    @ApiModelProperty( value = "退款时间" , required = true)
    private Date refundTime;

    @ApiModelProperty( value = "核销时间" , required = true)
    private Date writeOffTime;

    @ApiModelProperty( value = "微信支付,预支付id" , required = true)
    private String prepayId;

    @ApiModelProperty( value = "第三方流水id" , required = true)
    private String thirdPartyId;

    @ApiModelProperty( value = "状态,0正常 1冻结" , required = true)
    private String lockFlag;

	@TableLogic
	@ApiModelProperty( value = "删除标识,0未删除 1已删除" , required = true)
    private String delFlag;

    @ApiModelProperty( value = "创建时间" , required = true)
    private Date createTime;

    @ApiModelProperty( value = "修改时间" , required = true)
    private Date updateTime;

    @ApiModelProperty( value = "创建人" , required = true)
    private String createBy;



}
