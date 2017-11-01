package com.highplace.service.oauth.config;

import com.highplace.service.oauth.service.ProductInstanceUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;

@Configuration
public class MyWebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsService userDetailsService() {
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


    /*
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            MyUsernamePasswordAuthenticationFilter myUsernamePasswordAuthenticationFilter = new MyUsernamePasswordAuthenticationFilter();
            myUsernamePasswordAuthenticationFilter.setAuthenticationManager(authenticationManagerBean());
            http.addFilterAt(myUsernamePasswordAuthenticationFilter,UsernamePasswordAuthenticationFilter.class);
        }
    */
    @Bean
    public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
        return new SecurityEvaluationContextExtension();
    }

    //TokenEndpoint

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
