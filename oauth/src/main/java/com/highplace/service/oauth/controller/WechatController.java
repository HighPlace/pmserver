package com.highplace.service.oauth.controller;

import com.highplace.service.oauth.service.WechatLoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;

@CrossOrigin(origins = "*",maxAge = 3600)
@Controller
public class WechatController {

    public static final Logger logger = LoggerFactory.getLogger(WechatController.class);

    @Autowired
    private WechatLoginService wechatLoginService;

    //验证微信服务器后台请求token
    @RequestMapping(value ="/wechat/checktoken", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String checkSignature(HttpServletRequest request) {
        String echostr = request.getParameter("echostr");       // 随机字符串
        if (wechatLoginService.isSignature(request)) {
            return echostr;
        }
        return null;
    }

    //重定向第三方登录
    @RequestMapping(value = "/wechat/login", method= RequestMethod.GET)
    public String showLogin(@RequestParam(value = "clientType", required = true) int clientType)
            throws UnsupportedEncodingException{
        return "redirect:" + wechatLoginService.generateLoginUrl(clientType, null);
    }

    //获取第三方登录url
    @RequestMapping(value = "/wechat/loginUrl", method= RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getLoginUrl(@RequestParam(value = "clientType", required = true) int clientType,
                                           @RequestParam(value = "callBackUrl", required = true) String callBackUrl)
            throws UnsupportedEncodingException{

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("loginUrl", wechatLoginService.generateLoginUrl(clientType, callBackUrl));
        return result;
    }

    //第三方登录完成,页面调用该请求获取相关信息
    @RequestMapping(value = "/wechat/callback", method=RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> callback(@RequestParam(value = "code", required = true) String code,
                           @RequestParam(value = "state", required = true) String secretState,
                           HttpServletRequest request) throws Exception {

        return wechatLoginService.callback(code, secretState);
    }

    //将微信openid与用户帐号绑定
    @RequestMapping(value = "/bindWxOpenId", method=RequestMethod.POST )
    @ResponseBody
    public void bind(Principal user,
                       @RequestParam(value = "wxOpenId", required = true) String wxOpenId) {

        wechatLoginService.bind(user.getName(), wxOpenId);
    }
}
