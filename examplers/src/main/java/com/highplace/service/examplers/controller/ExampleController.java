package com.highplace.service.examplers.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class ExampleController {

    public static final Logger logger = LoggerFactory.getLogger(ExampleController.class);

    @RequestMapping(path = "/current", method = RequestMethod.GET)
    public Principal getCurrentAccount(Principal principal) {

        logger.info("XXXXXXXXXX:" + principal.toString());
        logger.info("XXXXXXXXXX:" + principal.getName());

        User user = null;
        if (principal instanceof User) {

            user = (User) principal;
            logger.info("XXXXXXXXXX:" + user.toString());
        }

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
