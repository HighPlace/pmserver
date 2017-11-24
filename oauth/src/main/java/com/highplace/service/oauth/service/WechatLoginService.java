package com.highplace.service.oauth.service;

import com.highplace.service.oauth.config.WechatConfig;
import com.highplace.service.oauth.dao.UserDao;
import com.highplace.service.oauth.domain.User;
import com.highplace.service.oauth.domain.wechat.WechatAccessToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class WechatLoginService {

    public static final Logger logger = LoggerFactory.getLogger(WechatLoginService.class);

    public static final String ACCESS_TOKEN_BASE_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";
    public static final String WEB_LOGIN_BASE_URL = "https://open.weixin.qq.com/connect/qrconnect";
    public static final String WECHAT_LOGIN_BASE_URL = "https://open.weixin.qq.com/connect/oauth2/authorize";
    public static final String GET_USERINFO_URL = "https://api.weixin.qq.com/sns/userinfo";
    public static final String PREFIX_WX_LOGIN_STATE_KEY = "PREFIX_WX_LOGIN_STATE_KEY";
    public static final String PREFIX_WX_LOGIN_TEMP_PASSWORD_KEY = "PREFIX_WX_LOGIN_TEMP_PASSWORD_KEY";

    public static final int CLIENT_TYPE_WEB = 1;
    public static final int CLIENT_TYPE_WECHAT = 2;

    @Autowired
    WechatConfig wechatConfig;

    @Autowired
    private UserDao userDao;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

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

    //针对微信的特殊处理,参考http://blog.csdn.net/kinginblue/article/details/52706155
    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new WechatLoginService.WxMappingJackson2HttpMessageConverter());
        return restTemplate;
    }

    // 检查从微信服务器发过来的签名
    public boolean isSignature(HttpServletRequest request) {
        String signature = request.getParameter("signature");           // 微信加密签名
        String timestamp = request.getParameter("timestamp");           // 时间戳
        String nonce = request.getParameter("nonce");                   // 随机数

        String[] arr = new String[]{timestamp, nonce, wechatConfig.getToken()};
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

    //生成微信第三方登录url
    //https://open.weixin.qq.com/connect/qrconnect?appid=APPID&redirect_uri=CALLBACK&response_type=code&scope=snsapi_login&state=State
    public String generateLoginUrl(int clientType, String callBackUrl) throws UnsupportedEncodingException {

        //生成state放入redis
        String secretState = "secret" + new Random().nextInt(999_999_999);
        stringRedisTemplate.opsForValue().set(PREFIX_WX_LOGIN_STATE_KEY + secretState, secretState, 60 * 5, TimeUnit.SECONDS);

        String loginUrl = null;
        if (clientType == CLIENT_TYPE_WEB) {
            loginUrl = WEB_LOGIN_BASE_URL;
        } else if (clientType == CLIENT_TYPE_WECHAT) {
            loginUrl = WECHAT_LOGIN_BASE_URL;
        } else {
            return null;
        }

        //如果没有指定，用缺省配置的
        if (callBackUrl == null) {
            callBackUrl = wechatConfig.getCallback();
        }

        loginUrl = loginUrl
                + "?appid=" + wechatConfig.getClientid()
                + "&redirect_uri=" + URLEncoder.encode(callBackUrl, "utf-8")
                //+ "&response_type=code&scope=snsapi_login"
                + "&response_type=code&scope=snsapi_base"
                + "&state=" + secretState
                + "#wechat_redirect";

        return loginUrl;
    }

    //处理call back请求
    //CALLBACK?code=0419p3Cc0YxTtG1nadCc0Ms7Cc09p3C8&state=secret121324
    public Map<String, Object> callback(String code, String secretState) throws Exception {
        /*
        //检查传回的state跟session中的是否一致
        final String secretStateFromSession = (String) request.getSession().getAttribute("state");
        if (!secretStateFromSession.equals(secretState)) {
            logger.info("State value do not match!");
            return "errorstate";
        }
        */
        String secretStateFromRedis = stringRedisTemplate.opsForValue().get(PREFIX_WX_LOGIN_STATE_KEY + secretState);
        logger.debug("secretStateFromRedis:{} " , secretStateFromRedis);
        if ((secretStateFromRedis == null) || !secretStateFromRedis.equals(secretState)) {
            throw new Exception("State value do not match!");
        }

        //获取accesstoken
        String accessTokenUrl = ACCESS_TOKEN_BASE_URL
                + "?appid=" + wechatConfig.getClientid()
                + "&secret=" + wechatConfig.getClientsecret()
                + "&code=" + code
                + "&grant_type=authorization_code";
        logger.debug("accesstokenUrl: {}" , accessTokenUrl);

        WechatAccessToken wechatAccessToken = restTemplate().getForObject(accessTokenUrl, WechatAccessToken.class);

        if (null == wechatAccessToken || !wechatAccessToken.valid()) {
            logger.error("getWechatAccessToken invalid: {}" , wechatAccessToken);
            throw new Exception("获取微信AccessToken失败");
        }

        logger.debug("wechatAccessToken: {}" , wechatAccessToken);

        /*
        //获取用户信息
        // https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID
        String userinfoUrl = GET_USERINFO_URL
                        + "?access_token=" + wechatAccessToken.getAccess_token()
                        + "&openid=" + wechatAccessToken.getOpenid();
        logger.debug("XXXXXXXXXXXXX getUserinfoUrl: " + userinfoUrl);

        WechatUserInfo wechatUserInfo =  restTemplate().getForObject(userinfoUrl, WechatUserInfo.class);

        if (null == wechatUserInfo || !wechatUserInfo.valid()){
            logger.error("getWechatUserInfo invalid: " + wechatUserInfo);
            wechatUserInfo = null;
            return null;
        }
        logger.debug(wechatUserInfo.toString());
        */

        Map<String, Object> result = new LinkedHashMap<>();

        //通过openid检查用户是否已经存在
        User isExists = userDao.findByWxOpenId(wechatAccessToken.getOpenid());
        //Assert.notNull(isExists, "你没有绑定微信帐号: " + wechatAccessToken.getOpenid());
        if (isExists == null) {
            result.put("bindFlag", 0); //未绑定微信帐号
            result.put("wxOpenId", wechatAccessToken.getOpenid());
        } else {

            //生成一个临时的用于通过wxopenid登录的密码，并放入到session中
            String wxOpenidTempPassword = UUID.randomUUID().toString();
            stringRedisTemplate.opsForValue().set(PREFIX_WX_LOGIN_TEMP_PASSWORD_KEY + wxOpenidTempPassword, wxOpenidTempPassword, 60 * 1, TimeUnit.SECONDS);

            result.put("bindFlag", 1);
            result.put("wxOpenId", wechatAccessToken.getOpenid());
            result.put("wxOpenidTempPassword", wxOpenidTempPassword);
        }
        return result;
    }


    //绑定用户微信openid
    public int bind(String username, String wxOpenId) {

        User isExists = userDao.findByUsername(username);
        Assert.notNull(isExists, "username not exists: " + username);
        return userDao.updateWxOpenIdByUsername(username, wxOpenId);
    }

    protected class WxMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {
        public WxMappingJackson2HttpMessageConverter() {
            List<MediaType> mediaTypes = new ArrayList<>();
            mediaTypes.add(MediaType.TEXT_PLAIN);
            setSupportedMediaTypes(mediaTypes);
        }
    }
}
