package com.pig4cloud.pig.dc.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 供应商表
 * </p>
 *
 * @author chenlei
 * @since 2021-11-13
 */
@Data
@ApiModel(value="查询汇率", description="查询汇率")
public class QueryExchangeRateDTO implements Serializable {

    private static final long serialVersionUID = 1L;


    @Valid
	@NotNull(message = "参数不可为空")
	@ApiModelProperty( value = "币种列表,传英文代号 " +
			"\n" +
			" \t{\"澳大利亚元\":\"AUD\",\"巴西里亚尔\":\"BRL\",\"加拿大元\":\"CAD\",\"瑞士法郎\":\"CHF\",\"沙特里亚尔\":\"SAR\",\"丹麦克朗\":\"DKK\",\"欧元\":\"EUR\",\"英镑\":\"GBP\",\"港币\":\"HKD\",\"印尼卢比\":\"IDR\",\"土耳其里拉\":\"TRY\",\"日元\":\"JPY\",\"韩国元\":\"KRW\",\"澳门元\":\"MOP\",\"林吉特\":\"MYR\",\"挪威克朗\":\"NOK\",\"南非兰特\":\"ZAR\",\"新西兰元\":\"NZD\",\"菲律宾比索\":\"PHP\",\"卢布\":\"RUB\",\"瑞典克朗\":\"SEK\",\"印度卢比\":\"INR\",\"新加坡元\":\"SGD\",\"泰国铢\":\"THB\",\"新台币\":\"TWD\",\"美元\":\"USD\",\"阿联酋迪拉姆\":\"AED\"}" , required = true)
	private  List<String> params;

}
