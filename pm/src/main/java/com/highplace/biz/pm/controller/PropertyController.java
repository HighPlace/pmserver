package com.highplace.biz.pm.controller;

import com.highplace.ProductInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.LinkedHashMap;

@RestController
public class PropertyController {

    public static final Logger logger = LoggerFactory.getLogger(PropertyController.class);

    @RequestMapping(path = "/current", method = RequestMethod.GET)
    public Principal getCurrentAccount(Principal principal) {

        logger.info("XXXXXXXXXX Username:" + principal.getName());


        OAuth2Authentication authen = (OAuth2Authentication) principal;
        //logger.info("XXXXXXXXXX:" + authen.getUserAuthentication().getPrincipal());
        //logger.info("XXXXXXXXXX:" + authen.getPrincipal().getClass());
        LinkedHashMap a = (LinkedHashMap)authen.getUserAuthentication().getDetails();
        LinkedHashMap o = (LinkedHashMap)a.get("principal");
        logger.info("XXXXXXXXXX:" + (String)o.get("productInstId"));

        return principal;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping("/testadmin")
    public String testadmin() {
        return "有权限访问";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', '/property;GET', '/property/**;GET', '/property;ALL', '/property/**;ALL')")
    @RequestMapping("/property")
    public String testinfo() {
        return "有权限访问";
    }

}
