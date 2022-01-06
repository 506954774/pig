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
 * 新闻
 * </p>
 *
 * @author chenlei
 * @since 2021-11-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="插入新文资讯,入参对象", description="插入新文资讯,入参对象")
public class AddNewsDTO extends Model<AddNewsDTO> {

    private static final long serialVersionUID = 1L;


	@NotNull(message = "标题不可为空")
	@NotEmpty(message = "标题不可为空")
    @ApiModelProperty( value = "标题" , required = true)
    private String title;

	@NotNull(message = "新闻地址不可为空")
	@NotEmpty(message = "新闻地址不可为空")
    @ApiModelProperty( value = "新闻地址" , required = true)
    private String webUrl;

	@NotNull(message = "缩略图不可为空")
	@NotEmpty(message = "缩略图不可为空")
	@ApiModelProperty( value = "缩略图" , required = true)
	private String iocnUrl;

	@ApiModelProperty( value = "首页推荐 0 否 1 是" , required = true)
	private Integer recommendFlag;

	@ApiModelProperty( value = "排序字段" , required = true)
	private Integer newsSort;
}
