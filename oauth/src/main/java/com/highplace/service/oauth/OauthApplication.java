package com.highplace.service.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@SpringBootApplication
@EnableDiscoveryClient
@RefreshScope
public class OauthApplication {

	public static void main(String[] args) {
		SpringApplication.run(OauthApplication.class, args);
	}

    @Configuration
    @EnableAuthorizationServer
    protected static class OAuth2AuthorizationConfig extends AuthorizationServerConfigurerAdapter {

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

            // TODO persist clients details

            // @formatter:off
            clients.inMemory()  // 使用in-memory存储
                        .withClient("client")               // client_id
                        .secret("secret")                           // client_secret
                        .authorizedGrantTypes("authorization_code") // 该client允许的授权类型
                        .scopes("app");                             // 允许的授权范围
        }

    }
}
