package com.highplace.biz.pm.controller;

import com.highplace.biz.pm.service.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class PropertyController {

    public static final Logger logger = LoggerFactory.getLogger(PropertyController.class);

    @RequestMapping(path = "/current", method = RequestMethod.GET)
    public Map<String, String> getCurrentAccount(Principal principal) {

        String pid = SecurityUtils.getCurrentProductInstId(principal);
        logger.info("XXXXXXXXXX Username:" + principal.getName());
        logger.info("XXXXXXXXXX ProductInstId:" + pid);

        Map<String, String> result = new LinkedHashMap<>();
        result.put("username", principal.getName());
        result.put("productInstId", pid);

        return result;
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
