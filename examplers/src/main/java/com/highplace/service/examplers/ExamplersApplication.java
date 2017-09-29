package com.highplace.service.examplers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@SpringBootApplication
@RestController
@EnableResourceServer
public class ExamplersApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExamplersApplication.class, args);
	}

    @RequestMapping(path = "/current", method = RequestMethod.GET)
    public Principal getCurrentAccount(Principal principal) {
        return principal;
    }

}
