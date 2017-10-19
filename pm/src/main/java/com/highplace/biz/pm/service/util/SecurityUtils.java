package com.highplace.biz.pm.service.util;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.util.Assert;

import java.security.Principal;
import java.util.LinkedHashMap;

public final class SecurityUtils {


    public static String getCurrentUserUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = null;
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            currentUserName = authentication.getName();

        }
        return currentUserName;
    }

    //从当前请求中获取产品实例ID
    public static String getCurrentProductInstId(Principal principal) {

        OAuth2Authentication authen = (OAuth2Authentication) principal;
        LinkedHashMap a = (LinkedHashMap)authen.getUserAuthentication().getDetails();
        LinkedHashMap o = (LinkedHashMap)a.get("principal");
        String productInstId = (String)o.get("productInstId");
        Assert.notNull(productInstId, "productInstId is null");
        return productInstId;
    }

}