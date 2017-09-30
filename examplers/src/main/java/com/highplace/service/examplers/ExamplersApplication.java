package com.highplace.service.examplers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.security.Principal;

@SpringBootApplication
@RestController
@EnableResourceServer
@EnableDiscoveryClient
@RefreshScope
//@EnableGlobalMethodSecurity(prePostEnabled=true)
//@EnableOAuth2Client
//@EnableConfigurationProperties
//@Configuration
//@EnableFeignClients
public class ExamplersApplication {

    public static final Logger logger = LoggerFactory.getLogger(ExamplersApplication.class);

    //@Autowired
    //private ResourceServerProperties sso;

	public static void main(String[] args) {
		SpringApplication.run(ExamplersApplication.class, args);
	}

	@RequestMapping(path = "/current", method = RequestMethod.GET)
    public Principal getCurrentAccount(Principal principal) {

        logger.info("XXXXXXXXXX:" + principal.toString());
        logger.info("XXXXXXXXXX:" + principal.getName());

        User user = null;
        if (principal instanceof User) {

            user = (User) principal;
            logger.info("XXXXXXXXXX:" + user.toString());
        }

        return principal;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping("/testadmin")
    public String testadmin() {
        return "有权限访问";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', '/testinfo;ALL', '/testinfo;GET')")
    @RequestMapping("/testinfo")
    public String testinfo() {
        return "有权限访问";
    }
/*
    @Bean
    @ConfigurationProperties(prefix = "security.oauth2.client")
    public ClientCredentialsResourceDetails clientCredentialsResourceDetails() {
        return new ClientCredentialsResourceDetails();
    }

    @Bean
    public RequestInterceptor oauth2FeignRequestInterceptor(){
        return new OAuth2FeignRequestInterceptor(new DefaultOAuth2ClientContext(), clientCredentialsResourceDetails());
    }

    @Bean
    public OAuth2RestTemplate clientCredentialsRestTemplate() {
        return new OAuth2RestTemplate(clientCredentialsResourceDetails());
    }

    @Bean
    @Primary
    public ResourceServerTokenServices tokenServices() {
        return new CustomUserInfoTokenServices(sso.getUserInfoUri(), sso.getClientId());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/" , "/demo").permitAll()
                .anyRequest().authenticated();
    }
*/

}
