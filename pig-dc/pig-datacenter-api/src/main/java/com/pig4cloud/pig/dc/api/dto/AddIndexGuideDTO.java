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
 * 导航
 * </p>
 *
 * @author chenlei
 * @since 2021-11-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="首页导航入参", description="首页导航入参")
public class AddIndexGuideDTO extends Model<AddIndexGuideDTO> {

    private static final long serialVersionUID = 1L;


	@NotNull(message = "标题不可为空")
	@NotEmpty(message = "标题不可为空")
    @ApiModelProperty( value = "标题" , required = true)
    private String title;

	@NotNull(message = "icon的地址不可为空")
	@NotEmpty(message = "icon的地址不可为空")
    @ApiModelProperty( value = "icon的地址" , required = true)
    private String iconUrl;

	@NotNull(message = "内容的web地址不可为空")
	@NotEmpty(message = "内容的web地址不可为空")
    @ApiModelProperty( value = "内容的web地址" , required = true)
    private String webUrl;

    @ApiModelProperty( value = "排序,大的在前面" , required = true)
    private Integer indexSort=999;




}
