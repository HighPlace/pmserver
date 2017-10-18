package com.highplace.biz.pm.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class PropertyController {

    public static final Logger logger = LoggerFactory.getLogger(PropertyController.class);

    @RequestMapping(path = "/current", method = RequestMethod.GET)
    public Principal getCurrentAccount(Principal principal) {

        logger.info("XXXXXXXXXX:" + principal.toString());
        logger.info("XXXXXXXXXX:" + principal.getName());

        return principal;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping("/testadmin")
    public String testadmin() {
        return "有权限访问";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', '/testinfo;GET', '/testinfo/**;GET', '/testinfo;ALL', '/testinfo/**;ALL')")
    @RequestMapping("/testinfo")
    public String testinfo() {
        return "有权限访问";
    }

}
