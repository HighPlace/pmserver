package com.highplace.service.oauth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

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
                .formLogin().disable()     //使用form表单登录
                // .and()
                .csrf()          //禁用csrf
                .disable();

        // @formatter:on
    }

}
