package com.highplace.service.oauth.config;

import com.highplace.service.oauth.service.ProductInstanceUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

@Configuration
public class MyWebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsService userDetailsService(){
        return new ProductInstanceUserService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public MyDaoAuthenticationProvider myDaoAuthenticationProvider() {
        MyDaoAuthenticationProvider myDaoAuthenticationProvider = new MyDaoAuthenticationProvider();
        myDaoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        myDaoAuthenticationProvider.setUserDetailsService(userDetailsService());
        return myDaoAuthenticationProvider;
    }
    //UsernamePasswordAuthenticationFilter
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        //auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
        //myDaoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        //myDaoAuthenticationProvider.setUserDetailsService(userDetailsService());
        auth.authenticationProvider(myDaoAuthenticationProvider());
    }

    @Bean
    public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
        return new SecurityEvaluationContextExtension();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
