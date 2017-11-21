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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Collections;
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
    public Account createAccount(@Valid @RequestBody Account account,
                                    Principal principal) throws Exception {

        logger.debug("pre account:" + account.toString());
        int rows = accountService.insert(SecurityUtils.getCurrentProductInstId(principal), account);
        if (rows == -1)
            throw new Exception("员工id不存在,请检查");
        else if (rows == -2)
            throw new Exception("员工已有平台账号,请检查");
        else if (rows != 1)
            throw new Exception("create failed, effected num:" + rows);
        return account;
    }

    @RequestMapping(path = "/account", method = RequestMethod.PUT)
    @PreAuthorize("hasAnyAuthority('/account;PUT','/account;ALL','/account/**;PUT','/account/**;ALL','ADMIN')")
    public Account changeDepartment(@RequestBody Account account,
                                       Principal principal) throws Exception {

        if ( StringUtils.isEmpty(account.getUsername())) throw new Exception("username is null");
        logger.debug("pre account:" + account.toString());

        //插入记录
        accountService.update(SecurityUtils.getCurrentProductInstId(principal), account);
        return account;
    }

    @RequestMapping(path = "/account/username", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('/account;GET','/account;ALL','/account/**;GET','/account/**;ALL','ADMIN')")
    public Map<String, Object> getEntityList(@RequestParam(value = "input", required = true) String input,
                                             Principal principal) {

        return accountService.rapidSearch(SecurityUtils.getCurrentProductInstId(principal),input);
    }

    @RequestMapping(path = "/account/checkUsername", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('/account;GET','/account;ALL','/account/**;GET','/account/**;ALL','ADMIN')")
    public Map<String, Object>  checkUsernameValid(@RequestParam(value = "input", required = true) String input,
                                             Principal principal) {

        boolean isExist = accountService.checkUsernameExists(SecurityUtils.getCurrentProductInstId(principal),input);

        if(!isExist)
            return Collections.<String, Object>singletonMap("result", true);
        else
            return Collections.<String, Object>singletonMap("result", false);

    }
}
