package com.pig4cloud.pig.dc.api.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * <p>
 * 地区
 * </p>
 *
 * @author chenlei
 * @since 2021-11-28
 */
@Data
@ApiModel(value="插入OscArea对象", description="插入OscArea对象")
public class AddOscAreaDTO extends Model<AddOscAreaDTO> {

    private static final long serialVersionUID = 1L;



    @NotNull(message = "地区中文名称不能为空")
    @NotEmpty(message = "地区中文名称不能为空")
    @ApiModelProperty( value = "名称,中文" , required = true)
    private String areaName;

    @ApiModelProperty( value = "名称,英文" , required = true)
    private String areaNameEng;

    @ApiModelProperty( value = "排序,大的在前面" , required = true)
    private Integer areaSort=999;


}
