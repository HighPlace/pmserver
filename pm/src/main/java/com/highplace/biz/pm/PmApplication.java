package com.highplace.biz.pm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableDiscoveryClient
@RefreshScope
@EnableGlobalMethodSecurity(prePostEnabled=true)   //PreAuthorize时必须开启
@MapperScan("com.highplace.biz.pm.dao")
@EnableScheduling  //支持定时任务
public class PmApplication {

	public static void main(String[] args) {
		SpringApplication.run(PmApplication.class, args);
	}
}
