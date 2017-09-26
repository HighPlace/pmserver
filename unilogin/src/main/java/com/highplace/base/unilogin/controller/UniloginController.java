package com.highplace.base.unilogin.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONPath;
import com.github.scribejava.apis.GitHubApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.highplace.base.unilogin.config.GitHubConfig;
import com.highplace.base.unilogin.dao.UserDao;
import com.highplace.base.unilogin.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ExecutionException;

@Controller
public class UniloginController {

    public static final Logger logger = LoggerFactory.getLogger(UniloginController.class);

    @Autowired
    GitHubConfig gitHubConfig;

    @Autowired
    UserDao userRepository;

    private String secretState = "secret12345";

    //重定向第三方登录
    @RequestMapping(value = {"", "/github"}, method= RequestMethod.GET)
    public String showLogin(){

        //final String secretState = "secret" + new Random().nextInt(999_999);

        final OAuth20Service service = new ServiceBuilder(gitHubConfig.getClientid())
                .apiSecret(gitHubConfig.getClientsecret())
                .state(secretState)
                .callback(gitHubConfig.getCallback())
                .build(GitHubApi.instance());

        return "redirect:" + service.getAuthorizationUrl();
    }

    //第三方登录完成,返回callback页面
    @RequestMapping(value = "/github/callback", method=RequestMethod.GET)
    public String callback(@RequestParam(value = "code", required = true) String code,
                           @RequestParam(value = "state", required = true) String secretState,
                           HttpServletRequest request) throws IOException, InterruptedException, ExecutionException {

        //final String secretState = "secret" + new Random().nextInt(999_999);

        final OAuth20Service service = new ServiceBuilder(gitHubConfig.getClientid())
                .apiSecret(gitHubConfig.getClientsecret())
                .state(secretState)
                .callback(gitHubConfig.getCallback())
                .build(GitHubApi.instance());

        if (!secretState.equals(secretState)) {
            logger.info("State value do not match!");
            return "errorstate";
        }

        final OAuth2AccessToken accessToken = service.getAccessToken(code);
        logger.info("(if your curious it looks like this: " + accessToken
                + ", 'rawResponse'='" + accessToken.getRawResponse() + "')");

        final OAuthRequest oauthRequest = new OAuthRequest(Verb.GET, gitHubConfig.getProtected_resource_url());
        service.signRequest(accessToken, oauthRequest);
        final Response response = service.execute(oauthRequest);
        logger.info("code:" + response.getCode());
        logger.info("body:" + response.getBody());
        Object result = JSON.parse(response.getBody());

        String github_openid = JSONPath.eval(result, "$.id").toString();
        //user.setUsername(JSONPath.eval(result, "$.login").toString());

        User isExists = userRepository.findByGithubOpenid(github_openid);
        if(isExists == null) {
            return "register:" + github_openid;
        }
        request.getSession().setAttribute("user", isExists);
        return "redirect:/success";
    }

    @RequestMapping(value = "/register", method=RequestMethod.POST)
    public String register(@RequestBody User user, HttpServletRequest request){

        if(userRepository.findByUsername(user.getUsername()) != null){
            return "username exists";
        }
        userRepository.insertUserWithGithubOpenid(user);

        request.getSession().setAttribute("user", user);
        return "redirect:/success";
    }

    @RequestMapping(value = "/success", method=RequestMethod.GET)
    @ResponseBody
    public Object success(HttpServletRequest request){
        return request.getSession().getAttribute("user");
    }

}
