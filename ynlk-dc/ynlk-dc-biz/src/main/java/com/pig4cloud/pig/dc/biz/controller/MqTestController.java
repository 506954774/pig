/*
 * Copyright (c) 2020 pig4cloud Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.pig4cloud.pig.dc.biz.controller;

import com.alibaba.fastjson.JSON;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.dc.biz.rabbitMq.receiver.MessageType;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pig4cloud.pig.common.mq.constants.RabbitMqConstants;
import pig4cloud.pig.common.mq.sender.RabbitMqSender;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

/**
 * <p>
 *测试 mq,延时消息
 * </p>
 *
 * @author lengleng
 * @since 2019/2/1
 */
@ApiIgnore
@RestController
@RequiredArgsConstructor
@RequestMapping("/mq")
@Api(value = "mq", tags = "测试rabbitMq")
public class MqTestController {

	private final RabbitMqSender rabbitMqSender;


	@GetMapping("/send/{message}/{time}")
	public R test(@PathVariable ("message") String message , @PathVariable ("time") String time) {

		Long delayTime = Long.parseLong(time);
		rabbitMqSender.sendDelayMessage(RabbitMqConstants.EXCHANGE_TEST,
				RabbitMqConstants.TOPIC_TEST, message, delayTime);

		return R.ok();
	}



	@GetMapping("/sendLimitedMsg")
	public R sendLimitedMsg(@RequestBody  @Valid MessageType messageType) {

		rabbitMqSender.sendMessage(RabbitMqConstants.EXCHANGE_LIMITED,
				RabbitMqConstants.TOPIC_LIMITED, JSON.toJSONString(messageType) );

		return R.ok();
	}



	@GetMapping("/sendLimitedMsg/v2")
	public R sendLimitedMsgBatch
			(@RequestBody  @Valid MessageType messageType) {

		for (int i = 0; i < 10; i++) {
			rabbitMqSender.sendMessage(RabbitMqConstants.EXCHANGE_LIMITED,
					RabbitMqConstants.TOPIC_LIMITED, JSON.toJSONString(messageType) );
		}
		return R.ok();
	}

}