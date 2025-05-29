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
 * 学院
 * </p>
 *
 * @author chenlei
 * @since 2021-11-28
 */
@Data
@ApiModel(value="新增OscCollege对象", description="新增OscCollege对象")
public class AddOscCollegeDTO extends Model<AddOscCollegeDTO> {

    private static final long serialVersionUID = 1L;


    @NotNull(message = "大学id不可为空")
    @ApiModelProperty( value = "主键" , required = true)
    private Integer universtyId;

	@NotNull(message = "学院中文名称不可为空")
	@NotEmpty(message = "学院中文名称不可为空")
    @ApiModelProperty( value = "名称,中文" , required = true)
    private String collegeName;

    @ApiModelProperty( value = "名称,英文" , required = true)
    private String collegeNameEng;


}
