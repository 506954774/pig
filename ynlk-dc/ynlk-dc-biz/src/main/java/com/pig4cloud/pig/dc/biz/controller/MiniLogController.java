package com.pig4cloud.pig.dc.biz.controller;

import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.security.annotation.Inner;
import com.pig4cloud.pig.dc.api.dto.KeyValueDTO;
import com.pig4cloud.pig.dc.biz.kafka.KafkaProducer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author LeiChen
 * @version 1.0
 * @date 2021/5/21 12:37
 */
@RestController
@AllArgsConstructor
@RequestMapping("/mini/log")
@Api(value = "MiniLogController", tags = "日志")
public class MiniLogController {

	private final KafkaProducer kafkaProducer;



	/**
	 *  kafka发送消息
	 *
	 * @return
	 */
	@Inner(value = false)
	@ApiOperation(value = " kafka发送消息", notes = " kafka发送消息")
	@PostMapping("/send")
	public R sendKafkaMsg(@RequestBody @Valid KeyValueDTO dto) {
		kafkaProducer.send(dto.getKey(),dto.getValue());
		return R.ok(null);
	}



}
