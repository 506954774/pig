package com.pig4cloud.pig.dc.api.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * <p>
 * 产品
 * </p>
 *
 * @author chenlei
 * @since 2021-11-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="新增产品信息,入参", description="新增产品信息,入参")
public class AddProductDTO extends Model<AddProductDTO> {

    private static final long serialVersionUID = 1L;


	@NotNull(message = "产品名称不可为空")
	@NotEmpty(message = "产品名称不可为空")
    @ApiModelProperty( value = "产品名称" , required = true)
    private String productName;

	@NotNull(message = "封面图片地址不可为空")
	@NotEmpty(message = "封面图片地址不可为空")
    @ApiModelProperty( value = "封面图片地址" , required = true)
    private String imageUrl;

	@NotNull(message = "详细信息不可为空")
	@NotEmpty(message = "详细信息不可为空")
    @ApiModelProperty( value = "详细信息" , required = true)
    private String productDesc;

	@NotNull(message = "价格不可为空")
	@ApiModelProperty( value = "价格,单位:人命币,元" , required = true)
	private Double productPrice;

  /*  @ApiModelProperty( value = "排序,大的在前面" , required = true)
    private Integer productSort=999;
*/



}
