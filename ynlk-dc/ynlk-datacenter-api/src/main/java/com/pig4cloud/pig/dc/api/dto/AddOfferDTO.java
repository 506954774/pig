package com.pig4cloud.pig.dc.api.dto;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * offer榜单
 * </p>
 *
 * @author chenlei
 * @since 2021-11-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="offer信息,录入参数", description="offer信息录入")
public class AddOfferDTO extends Model<AddOfferDTO> {

    private static final long serialVersionUID = 1L;


	@NotNull(message = "学生姓名不可为空")
	@NotEmpty(message = "学生姓名不可为空")
    @ApiModelProperty( value = "学生姓名" , required = true)
    private String stuName;

	@NotNull(message = "学校信息不可为空")
	@NotEmpty(message = "学校信息不可为空")
    @ApiModelProperty( value = "学校信息" , required = true)
    private String schoolDesc;

	@NotNull(message = "封面图片地址不可为空")
	@NotEmpty(message = "封面图片地址不可为空")
    @ApiModelProperty( value = "封面图片地址" , required = true)
    private String imageUrl;

	/*@NotNull(message = "详情界面的web地址不可为空")
	@NotEmpty(message = "详情界面的web地址不可为空")
    @ApiModelProperty( value = "详情界面的web地址" , required = true)
    private String webUrl;
*/
    @ApiModelProperty( value = "排序,大的在前面" , required = true)
    private Integer offerSort=999;


	@ApiModelProperty( value = "是否首页推荐 0否 1是" , required = true)
	private Integer recommendFlag;

	@NotNull(message = "详情内容不可为空")
	@NotEmpty(message = "详情内容不可为空")
	@ApiModelProperty( value = "内容,富文本" , required = true)
	private String content;

	@NotNull(message = "专业名称不可为空")
	@NotEmpty(message = "专业名称不可为空")
	@ApiModelProperty( value = "专业名称" , required = true)
	private String majorName;

}
