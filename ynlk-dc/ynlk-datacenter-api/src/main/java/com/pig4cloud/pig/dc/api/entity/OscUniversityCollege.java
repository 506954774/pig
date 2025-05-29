package com.pig4cloud.pig.dc.api.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 大学-学院
 * </p>
 *
 * @author chenlei
 * @since 2021-11-28
 */
@Data
@ApiModel(value="OscUniversityCollege对象", description="大学-学院")
public class OscUniversityCollege extends Model<OscUniversityCollege> {

    private static final long serialVersionUID = 1L;



    @ApiModelProperty( value = "大学ID" , required = true)
    private Integer universityId;

    @ApiModelProperty( value = "学院ID" , required = true)
    private Integer collegeId;


}
