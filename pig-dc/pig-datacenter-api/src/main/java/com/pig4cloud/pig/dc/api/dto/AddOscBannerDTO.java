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
 * 轮播图
 * </p>
 *
 * @author chenlei
 * @since 2021-11-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="轮播图上传,qo", description="轮播图上传,qo")
public class AddOscBannerDTO extends Model<AddOscBannerDTO> {

    private static final long serialVersionUID = 1L;




    @NotNull(message = "标题不可为空")
    @NotEmpty(message = "标题不可为空")
    @ApiModelProperty( value = "标题" , required = true)
    private String title;

	@NotNull(message = "图片预览不可为空")
	@NotEmpty(message = "图片预览不可为空")
    @ApiModelProperty( value = "图片预览" , required = true)
    private String imageUrl;

    @ApiModelProperty( value = "跳转地址" , required = true)
    private String linkUrl;

    @ApiModelProperty( value = "排序,大的在前面" , required = true)
    private Integer bannerSort=999;




}
