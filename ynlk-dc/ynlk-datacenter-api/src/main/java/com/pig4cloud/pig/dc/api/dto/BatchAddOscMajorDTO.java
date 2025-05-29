package com.pig4cloud.pig.dc.api.dto;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>
 * 专业
 * </p>
 *
 * @author chenlei
 * @since 2021-11-28
 */
@Data
@ApiModel(value="批量导入专业,参数对象", description="批量导入专业,参数对象")
public class BatchAddOscMajorDTO extends Model<BatchAddOscMajorDTO> {

    private static final long serialVersionUID = 1L;


    @Valid
    @NotNull(message = "专业列表不可为空")
    @ApiModelProperty( value = "专业列表" , required = true)
    private List<AddOscMajorDTO2> list;




}
