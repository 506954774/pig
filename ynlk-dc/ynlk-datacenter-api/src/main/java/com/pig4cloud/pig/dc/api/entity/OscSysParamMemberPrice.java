package com.pig4cloud.pig.dc.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * <p>
 * 系统参数
 * </p>
 *
 * @author chenlei
 * @since 2021-12-02
 */
@Data
@ApiModel(value="设置会员价格,OscSysParam对象", description="设置会员价格")
public class OscSysParamMemberPrice extends Model<OscSysParamMemberPrice> {

    private static final long serialVersionUID = 1L;


    @NotNull(message = "会员价格不可为空")
    @ApiModelProperty( value = "会员价格,人民币,单位元" , required = true)
    private String paramValue;




}
