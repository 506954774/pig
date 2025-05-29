package com.pig4cloud.pig.dc.api.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 系统参数
 * </p>
 *
 * @author chenlei
 * @since 2021-12-02
 */
@Data
@ApiModel(value="设置参数内容,OscSysParam对象", description="设置参数内容")
public class OscSysParamMemberHint extends Model<OscSysParamMemberHint> {

    private static final long serialVersionUID = 1L;


    @NotNull(message = "参数内容不可为空")
    @ApiModelProperty( value = "参数内容,json串" , required = true)
    private String paramValue;




}
