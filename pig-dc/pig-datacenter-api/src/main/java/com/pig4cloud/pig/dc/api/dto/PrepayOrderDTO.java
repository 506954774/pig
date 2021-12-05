package com.pig4cloud.pig.dc.api.dto;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.pig4cloud.pig.dc.api.entity.OscOrder;
import com.pig4cloud.pig.dc.api.entity.OscOrderProduct;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;

/**
 * <p>
 * </p>
 *
 * @author chenlei
 * @since 2021-11-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="预支付,dto", description="预支付,dto")
public class PrepayOrderDTO extends Model<PrepayOrderDTO> {

    private static final long serialVersionUID = 1L;


	@ApiModelProperty( value = "订单详情" , required = true)
	private ArrayList<OscOrderProduct> orderProducts;

	@ApiModelProperty( value = "订单实体" , required = true)
	private OscOrder order;

}
