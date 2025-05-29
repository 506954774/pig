package com.pig4cloud.pig.gateway.config;




import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
import com.alibaba.csp.sentinel.adapter.gateway.sc.SentinelGatewayFilter;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.BlockRequestHandler;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import com.alibaba.csp.sentinel.adapter.gateway.sc.exception.SentinelGatewayBlockExceptionHandler;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.result.view.ViewResolver;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * GatewayFilterConfiguration
 * 责任人:  ChenLei
 * 修改人： ChenLei
 * 创建/修改时间: 2021/12/20 10:02
 * Copyright :  版权所有
 **/
//@Configuration
public class GatewayFilterConfiguration {

	private final List<ViewResolver> viewResolvers;
	private final ServerCodecConfigurer serverCodecConfigurer;

	public GatewayFilterConfiguration(ObjectProvider<List<ViewResolver>> viewResolversProvider,
								ServerCodecConfigurer serverCodecConfigurer) {
		this.viewResolvers = viewResolversProvider.getIfAvailable(Collections::emptyList);
		this.serverCodecConfigurer = serverCodecConfigurer;
	}

	// 配置限流的异常处理器
	@Bean
	@Order(Ordered.HIGHEST_PRECEDENCE)
	public SentinelGatewayBlockExceptionHandler sentinelGatewayBlockExceptionHandler() {
		// Register the block exception handler for Spring Cloud Gateway.
		return new SentinelGatewayBlockExceptionHandler(viewResolvers, serverCodecConfigurer);
	}

	// 初始化一个限流的过滤器
	@Bean
	@Order(Ordered.HIGHEST_PRECEDENCE)
	public GlobalFilter sentinelGatewayFilter2() {
		return new SentinelGatewayFilter();
	}

	/**
	 * 配置初始化的限流參數
	 */
	@PostConstruct
	public void initGatewayRules() {
		Set<GatewayFlowRule> rules = new HashSet<GatewayFlowRule>();
		rules.add(
				new GatewayFlowRule("auth-token")    // 资源名称，对应路由id
						.setCount(1)        // 限流阈值
						.setIntervalSec(1)   // 统计时间窗口（熔断时间），单位：秒。默认是1秒
		);

		GatewayRuleManager.loadRules(rules);
	}

	/**
	 * 自定义限流异常页面，限流返回的信息
	 */
	@PostConstruct
	public void initBlockHandlers() {
		BlockRequestHandler blockRequestHandler = new BlockRequestHandler() {
			@Override
			public Mono<ServerResponse> handleRequest(ServerWebExchange serverWebExchange, Throwable throwable) {
				Map map = new HashMap();
				map.put("code", -1);
				map.put("msg", "接口被限流了");

				return ServerResponse.status(HttpStatus.OK)
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.body(BodyInserters.fromObject(map));
			}
		};

		GatewayCallbackManager.setBlockHandler(blockRequestHandler);
	}
}
