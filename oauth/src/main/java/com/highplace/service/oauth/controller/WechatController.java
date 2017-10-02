package com.highplace.service.oauth.controller;

import com.highplace.service.oauth.dao.UserDao;
import com.highplace.service.oauth.domain.User;
import com.highplace.service.oauth.domain.WechatAccessToken;
import com.highplace.service.oauth.domain.WechatUserInfo;
import com.highplace.service.oauth.config.WechatConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.*;
import java.util.concurrent.ExecutionException;

@Controller
public class WechatController {

    public static final Logger logger = LoggerFactory.getLogger(WechatController.class);

    public static final String ACCESS_TOKEN_BASE_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";
    public static final String WEB_LOGIN_BASE_URL = "https://open.weixin.qq.com/connect/qrconnect";
    public static final String MOBILE_LOGIN_BASE_URL = "https://open.weixin.qq.com/connect/oauth2/authorize";
    public static final String GET_USERINFO_URL = "https://api.weixin.qq.com/sns/userinfo";

    @Autowired
    WechatConfig wechatConfig;

    @Autowired
    private UserDao userDao;

    private RestTemplate restTemplate = new RestTemplate();

    //验证微信服务器后台请求token
    @RequestMapping(value ="/wechat/checktoken", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String checkSignature(HttpServletRequest request) {
        String echostr = request.getParameter("echostr");       // 随机字符串

        if (isSignature(request)) {
            return echostr;
        }
        return null;
    }

    //重定向第三方登录
    //https://open.weixin.qq.com/connect/qrconnect?appid=APPID
    // &redirect_uri=CALLBACK&response_type=code&scope=snsapi_login
    // &state=State
    @RequestMapping(value = "/wechat/login", method= RequestMethod.GET)
    public String showLogin(HttpServletRequest request){

        //生成state放入session
        String secretState = "secret" + new Random().nextInt(999_999);
        request.getSession().setAttribute("state", secretState);

        //String loginUrl = WEB_LOGIN_BASE_URL
        String loginUrl = MOBILE_LOGIN_BASE_URL
                            + "?appid=" + wechatConfig.getClientid()
                            + "&redirect_uri=" + URLEncoder.encode(wechatConfig.getCallback())
                            + "&response_type=code&scope=snsapi_login"
                            + "&state=" + secretState
                            + "#wechat_redirect";

        return "redirect:" + loginUrl;
    }

    //第三方登录完成,返回callback页面
    //CALLBACK?code=0419p3Cc0YxTtG1nadCc0Ms7Cc09p3C8&state=secret121324
    @RequestMapping(value = "/wechat/callback", method=RequestMethod.GET)
    public Object callback(@RequestParam(value = "code", required = true) String code,
                           @RequestParam(value = "state", required = true) String secretState,
                           HttpServletRequest request) throws IOException, InterruptedException, ExecutionException {


        //检查传回的state跟session中的是否一致
        final String secretStateFromSession = (String) request.getSession().getAttribute("state");
        if (!secretStateFromSession.equals(secretState)) {
            logger.info("State value do not match!");
            return "errorstate";
        }


        //获取accesstoken
        String accessTokenUrl = ACCESS_TOKEN_BASE_URL
                        + "?appid=" + wechatConfig.getClientid()
                        + "&secret=" + wechatConfig.getClientsecret()
                        + "&code=" + code
                        + "&grant_type=authorization_code";
        WechatAccessToken wechatAccessToken = restTemplate.getForObject(accessTokenUrl, WechatAccessToken.class);
        logger.info(wechatAccessToken.toString());

        //获取用户信息
        // https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID
        String userinfoUrl = GET_USERINFO_URL
                        + "?access_token=" + wechatAccessToken.getAccess_token()
                        + "&openid=" + wechatAccessToken.getOpenid();
        WechatUserInfo wechatUserInfo =  restTemplate.getForObject(userinfoUrl, WechatUserInfo.class);
        logger.info(wechatUserInfo.toString());

        //通过openid检查用户是否已经存在
        User isExists = userDao.findByWxOpenId(wechatUserInfo.getOpenid());
        if(isExists == null) {
            isExists = new User();
            isExists.setWxOpenId(wechatUserInfo.getOpenid());
            isExists.setUsername(wechatUserInfo.getNickname() + new Random().nextInt(999_999));

            isExists.setProductInstId("550E8400-E29B-11D4-A716-446655440000");//hard code...
            userDao.insertUser(isExists);
            logger.info("XXXXXXXXXXXXX user:" + isExists.toString());
            userDao.insertUserRole(isExists.getUserId(), 1L); //hard code...
        }
        request.getSession().setAttribute("user", isExists);
        return isExists;
    }

    // 检查签名
    public boolean isSignature(HttpServletRequest request) {
        String signature = request.getParameter("signature");           // 微信加密签名
        String timestamp = request.getParameter("timestamp");           // 时间戳
        String nonce = request.getParameter("nonce");                   // 随机数

        String[] arr = new String[] {timestamp, nonce, wechatConfig.getToken()};
        Arrays.sort(arr);
        String s = arr[0] + arr[1] + arr[2];
        MessageDigest md;
        byte[] digest = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
            digest = md.digest(s.getBytes("utf-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String sign = bytesToHexString(digest);
        return signature.equals(sign);
    }

    public static final String bytesToHexString(byte[] bArray) {
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString().toLowerCase();
    }
}
