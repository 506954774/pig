package com.pig4cloud.pig.dc.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 学院
 * </p>
 *
 * @author chenlei
 * @since 2021-11-28
 */
@Data
@ApiModel(value="OscCollege对象", description="学院")
public class OscCollege extends Model<OscCollege> {

    private static final long serialVersionUID = 1L;



    @ApiModelProperty( value = "主键" , required = true)
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty( value = "名称,中文" , required = true)
    private String collegeName;

    @ApiModelProperty( value = "名称,英文" , required = true)
    private String collegeNameEng;

    @ApiModelProperty( value = "logo图地址" , required = true)
    private String logoUrl;

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
