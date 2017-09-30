package com.highplace.base.apigw;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableZuulProxy
@EnableDiscoveryClient
@RestController
@RefreshScope
public class GatewayApplication{

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

    @Value("${hello.str}")
    private String hello;

    @RequestMapping(value = "/hello")
    public String hello(){
        return this.hello;
    }

}