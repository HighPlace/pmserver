package com.highplace.service.oauth.config;

import com.highplace.service.oauth.controller.UserController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        String codeFromRequest = request.getParameter("verifycode");
        if (codeFromRequest == null || codeFromRequest.equals("")) {
            throw new BadCredentialsException("请输入验证码");
        } else {
            String codeFromRedis = stringRedisTemplate.opsForValue().get(UserController.PREFIX_VERIFY_CODE_NAME_INSESSION + codeFromRequest);
            if (codeFromRedis == null || !codeFromRedis.equals(codeFromRequest)) {
                logger.debug("XXXXXXXXXXXXXXX codeFromRedis=" + codeFromRedis + "codeFromRequest=" + codeFromRequest);
                throw new BadCredentialsException("验证码错误");
            }
            stringRedisTemplate.delete(UserController.PREFIX_VERIFY_CODE_NAME_INSESSION + codeFromRequest);
        }
        return super.attemptAuthentication(request, response);
    }

}
