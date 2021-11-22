package com.pig4cloud.pig.dc.biz;

import com.pig4cloud.pig.common.feign.annotation.EnablePigFeignClients;
import com.pig4cloud.pig.common.security.annotation.EnablePigResourceServer;
import com.pig4cloud.pig.common.swagger.annotation.EnablePigSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnablePigSwagger2
@EnablePigResourceServer
@EnablePigFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class PigDcBizApplication {

	public static void main(String[] args) {
		SpringApplication.run(PigDcBizApplication.class, args);
	}

}
