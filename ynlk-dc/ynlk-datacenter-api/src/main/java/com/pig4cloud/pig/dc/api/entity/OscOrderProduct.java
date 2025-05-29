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
 * 订单-产品
 * </p>
 *
 * @author chenlei
 * @since 2021-12-05
 */
@Data
@ApiModel(value="OscOrderProduct对象", description="订单-产品")
public class OscOrderProduct extends Model<OscOrderProduct> {

    private static final long serialVersionUID = 1L;





    @ApiModelProperty( value = "主键" , required = true)
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty( value = "订单id" , required = true)
    private Integer orderId;

    @ApiModelProperty( value = "产品id" , required = true)
    private Integer productId;

	@ApiModelProperty( value = "产品名称" , required = true)
	private String productName;

	@ApiModelProperty( value = "产品数量" , required = true)
	private Integer productQuantity;

	@ApiModelProperty( value = "产品总价,人民币,元" , required = true)
	private BigDecimal productTotalPrice;

	@ApiModelProperty( value = "产品单价,人民币,元" , required = true)
	private BigDecimal productSinglePrice;

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
