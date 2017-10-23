package com.highplace.biz.pm.controller;

import com.highplace.biz.pm.domain.Customer;
import com.highplace.biz.pm.domain.Property;
import com.highplace.biz.pm.domain.ui.CustomerSearchBean;
import com.highplace.biz.pm.domain.ui.PropertySearchBean;
import com.highplace.biz.pm.service.CustomerService;
import com.highplace.biz.pm.service.PropertyService;
import com.highplace.biz.pm.service.util.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
public class CustomerController {

    public static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerService customerService;

    @RequestMapping(path = "/customer", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('/customer;GET','/customer;ALL','/customer/**;GET','/customer/**;ALL','ADMIN')")
    public Map<String, Object> getCustomer(@Valid CustomerSearchBean searchBean,
                                           Principal principal) {

        logger.debug("CustomerSearchBean:" + searchBean.toString());
        logger.debug("productInstId:" + SecurityUtils.getCurrentProductInstId(principal));
        return customerService.query(SecurityUtils.getCurrentProductInstId(principal), searchBean, false);

    }
}
