package com.highplace.service.oauth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.util.Arrays;

@Controller
public class WechatController {

    public static String WECHAT_TOKEN = "Token";

    @RequestMapping(value ="/wechat/checktoken", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String checkSignature(HttpServletRequest request) {
        String echostr = request.getParameter("echostr");       // 随机字符串

        if (isSignature(request)) {
            return echostr;
        }

        return null;
    }

    // 检查签名
    public boolean isSignature(HttpServletRequest request) {
        String signature = request.getParameter("signature");           // 微信加密签名
        String timestamp = request.getParameter("timestamp");           // 时间戳
        String nonce = request.getParameter("nonce");                   // 随机数

        String[] arr = new String[] {timestamp, nonce, WECHAT_TOKEN};
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
