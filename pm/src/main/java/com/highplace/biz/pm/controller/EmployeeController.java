package com.highplace.biz.pm.controller;

import com.highplace.biz.pm.domain.ui.EmployeeSearchBean;
import com.highplace.biz.pm.service.EmployeeService;
import com.highplace.biz.pm.service.util.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Map;

@RestController
public class EmployeeController {

    public static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;

    @RequestMapping(path = "/employee", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('/employee;GET','/employee;ALL','/employee/**;GET','/employee/**;ALL','ADMIN')")
    public Map<String, Object> getProperty(@Valid EmployeeSearchBean searchBean,
                                           Principal principal) {

        logger.debug("EmployeeSearchBean:" + searchBean.toString());
        logger.debug("productInstId:" + SecurityUtils.getCurrentProductInstId(principal));
        return employeeService.query(SecurityUtils.getCurrentProductInstId(principal), searchBean, false);

    }
}
