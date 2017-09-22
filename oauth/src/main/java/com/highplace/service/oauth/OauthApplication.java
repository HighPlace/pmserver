package com.highplace.service.oauth;

import com.highplace.service.oauth.JpaTemplate.JpaUserDetailsService;
import com.highplace.service.oauth.security.MysqlUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EnableDiscoveryClient
@RefreshScope
@EnableGlobalMethodSecurity(prePostEnabled = true)  //该注解可以开启prePostEnabled，securedEnabled，jsr250Enabled的支持
public class OauthApplication {

	public static void main(String[] args) {
		SpringApplication.run(OauthApplication.class, args);
	}


    @Configuration
    @EnableWebSecurity
    protected static class webSecurityConfig extends WebSecurityConfigurerAdapter {
        /*
        @Autowired
        private MysqlUserDetailsService userDetailsService;
        */

        @Autowired
        private JpaUserDetailsService userDetailsService;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            // @formatter:off
            http.authorizeRequests()   //配置安全策略
                     .antMatchers("/" , "/reg").permitAll()  //不需要验证
                     .anyRequest().authenticated() //其余请求都需要验证
                     .and()
                 .logout()
                     .permitAll() //logout不需要验证
                     .and()
                 .formLogin()     //使用form表单登录
                     .and()
                 .csrf()          //禁用csrf
                    .disable();
            // @formatter:on
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService)
                    .passwordEncoder(new BCryptPasswordEncoder());
        }

        @Override
        @Bean
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }

    }

    @Configuration
    @EnableAuthorizationServer
    protected static class OAuth2AuthorizationConfig extends AuthorizationServerConfigurerAdapter {

        @Autowired
        private AuthenticationManager authenticationManager;

        @Autowired
        private DataSource dataSource;

        @Bean // 声明TokenStore实现
        public TokenStore tokenStore() {
            return new JdbcTokenStore(dataSource);
        }

        @Bean // 声明 ClientDetails实现
        public ClientDetailsService clientDetails() {
            return new JdbcClientDetailsService(dataSource);
        }

        @Override // 配置框架应用上述实现
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
            endpoints.authenticationManager(authenticationManager);
            endpoints.tokenStore(tokenStore());

            // 配置TokenServices参数
            DefaultTokenServices tokenServices = new DefaultTokenServices();
            tokenServices.setTokenStore(endpoints.getTokenStore());
            tokenServices.setSupportRefreshToken(false);
            tokenServices.setClientDetailsService(endpoints.getClientDetailsService());
            tokenServices.setTokenEnhancer(endpoints.getTokenEnhancer());
            tokenServices.setAccessTokenValiditySeconds( (int) TimeUnit.DAYS.toSeconds(30)); // 30天
            endpoints.tokenServices(tokenServices);
        }

        @Override
        public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
            oauthServer.checkTokenAccess("isAuthenticated()");
            oauthServer.checkTokenAccess("permitAll()");
            oauthServer.allowFormAuthenticationForClients();
        }

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            clients.withClientDetails(clientDetails());
        }



        /*
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
        */



    }
}
