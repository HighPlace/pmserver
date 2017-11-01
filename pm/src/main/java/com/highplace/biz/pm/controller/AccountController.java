package com.highplace.biz.pm.controller;

import com.highplace.biz.pm.domain.system.Account;
import com.highplace.biz.pm.domain.ui.EmployeeSearchBean;
import com.highplace.biz.pm.service.AccountService;
import com.highplace.biz.pm.service.util.SecurityUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Map;

@RestController
public class AccountController {

    public static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private AccountService accountService;

    @RequestMapping(path = "/account", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('/account;GET','/account;ALL','/account/**;GET','/account/**;ALL','ADMIN')")
    public Map<String, Object> getAccount(@Valid EmployeeSearchBean searchBean,
                                          Principal principal) throws Exception {

        logger.debug("AccountSearchBean:" + searchBean.toString());
        return accountService.query(SecurityUtils.getCurrentProductInstId(principal), searchBean, false);
    }

    @RequestMapping(path = "/account", method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('/account;POST','/account;ALL','/account/**;POST','/account/**;ALL','ADMIN')")
    public Account createDepartment(@Valid @RequestBody Account account,
                                    Principal principal) throws Exception {

        logger.debug("pre account:" + account.toString());
        accountService.insert(SecurityUtils.getCurrentProductInstId(principal), account);
        return account;
    }

    @RequestMapping(path = "/account", method = RequestMethod.PUT)
    @PreAuthorize("hasAnyAuthority('/account;PUT','/account;ALL','/account/**;PUT','/account/**;ALL','ADMIN')")
    public Account changeDepartment(@RequestBody Account account,
                                       Principal principal) throws Exception {

        if (account.getUsername() == null) throw new Exception("username is null");
        logger.debug("pre account:" + account.toString());

        //插入记录
        accountService.update(SecurityUtils.getCurrentProductInstId(principal), account);
        return account;
    }
}
