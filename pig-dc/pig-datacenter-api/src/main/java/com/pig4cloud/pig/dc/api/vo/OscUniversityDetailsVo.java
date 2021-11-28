package com.pig4cloud.pig.dc.api.vo;

import com.pig4cloud.pig.dc.api.entity.OscCollege;
import com.pig4cloud.pig.dc.api.entity.OscUniversity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * <p>
 * 大学
 * </p>
 *
 * @author chenlei
 * @since 2021-11-28
 */
@Data
@ApiModel(value="大学详情", description="大学详情")
public class OscUniversityDetailsVo extends OscUniversity {



    @ApiModelProperty( value = "学院列表" , required = true)
    private List<OscCollege> cooleges;


}
