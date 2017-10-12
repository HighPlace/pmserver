/*
 * Copyright 2004, 2005, 2006 Acegi Technology Pty Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.highplace.service.oauth.config;

import com.highplace.service.oauth.controller.WechatController;
import com.highplace.service.oauth.domain.User;
import com.highplace.service.oauth.service.WechatLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.Assert;

public class MyDaoAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    private UserDetailsService userDetailsService;

    private PasswordEncoder passwordEncoder;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public MyDaoAuthenticationProvider() {
    }

    protected void additionalAuthenticationChecks(UserDetails userDetails,
                                                  UsernamePasswordAuthenticationToken authentication)
            throws AuthenticationException {

        User myUser = (User) userDetails;
        String username = authentication.getName().split("\\|")[0];

        if (authentication.getCredentials() == null || authentication.getCredentials().toString() == "" ) {
            logger.debug("Authentication failed: no credentials provided");

            throw new BadCredentialsException(messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.badCredentials",
                    "Bad credentials"));
        }

        String presentedPassword = authentication.getCredentials().toString();

        //如果用户是以用户名、邮箱或手机号码登陆
        if(username.equals(myUser.getUsername())
                || username.equals(myUser.getEmail())
                || username.equals(myUser.getMobileNo())) {
            //优先比对密码
            if( !passwordEncoder.matches(presentedPassword, userDetails.getPassword()) )
            {
                //如果是手机号，可能是手机验证码登录，所以再核对下手机验证码
                if (username.equals(myUser.getMobileNo())){

                    //to-do
                    return;

                } else {

                    logger.debug("Authentication failed: password does not match stored value");
                    throw new BadCredentialsException(messages.getMessage(
                                "AbstractUserDetailsAuthenticationProvider.badCredentials",
                                "Bad credentials"));
                }
            }

        //如果是微信openid登陆
        } else if (username.equals(myUser.getWxOpenId())) {

            //如果是微信openid，验证临时密码
            String wxOpenidTempPassword = stringRedisTemplate.opsForValue().get(WechatLoginService.PREFIX_WX_LOGIN_TEMP_PASSWORD_KEY + presentedPassword);
            logger.debug("XXXXXXXXXXXXX wxOpenidTempPasswordFromRedis: " + wxOpenidTempPassword);
            if( (wxOpenidTempPassword == null) || !presentedPassword.equals(wxOpenidTempPassword) ){
                logger.debug("Authentication failed: password does not match stored value");
                throw new BadCredentialsException(messages.getMessage(
                        "AbstractUserDetailsAuthenticationProvider.badCredentials",
                        "Bad credentials"));
            }
            return;

        } else {

            logger.debug("Authentication failed: username does not match any id");
            throw new BadCredentialsException(messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.badCredentials",
                    "Bad credentials"));
        }

    }

    protected void doAfterPropertiesSet() throws Exception {
        Assert.notNull(this.userDetailsService, "A UserDetailsService must be set");
    }

    protected final UserDetails retrieveUser(String username,
                                             UsernamePasswordAuthenticationToken authentication)
            throws AuthenticationException {
        UserDetails loadedUser;

        try {
            loadedUser = this.getUserDetailsService().loadUserByUsername(username);
        }
        catch (UsernameNotFoundException notFound) {

            throw notFound;
        }
        catch (Exception repositoryProblem) {
            throw new InternalAuthenticationServiceException(
                    repositoryProblem.getMessage(), repositoryProblem);
        }

        if (loadedUser == null) {
            throw new InternalAuthenticationServiceException(
                    "UserDetailsService returned null, which is an interface contract violation");
        }
        return loadedUser;
    }

    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    protected PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    protected UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }
}
