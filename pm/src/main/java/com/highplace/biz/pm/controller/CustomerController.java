package com.highplace.biz.pm.controller;

import com.highplace.biz.pm.domain.base.Customer;
import com.highplace.biz.pm.domain.ui.CustomerSearchBean;
import com.highplace.biz.pm.service.CustomerService;
import com.highplace.biz.pm.service.util.SecurityUtils;
import org.apache.commons.lang.StringUtils;
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

    @RequestMapping(path = "/customer/{entity}", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('/customer;GET','/customer;ALL','/customer/**;GET','/customer/**;ALL','ADMIN')")
    public Map<String, Object> getEntityList(@PathVariable String entity,
                                             @RequestParam(value = "input", required = true) String input,
                                             Principal principal) {
        //entity 支持name/phone/plateNo 三种
        return customerService.rapidSearch(SecurityUtils.getCurrentProductInstId(principal), entity, input);
    }

    @RequestMapping(path = "/customer", method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('/customer;POST','/customer;ALL','/customer/**;POST','/customer/**;ALL','ADMIN')")
    public Customer createCustomer(@Valid @RequestBody Customer customer,
                                   Principal principal) throws Exception {

        logger.debug("pre customer:" + customer.toString());
        if (StringUtils.isEmpty(customer.getCustomerName())) throw new Exception("customerName is empty");

        //插入记录
        int rows = customerService.insert(SecurityUtils.getCurrentProductInstId(principal), customer);
        logger.debug("customer insert return num:" + rows);
        logger.debug("post customer:" + customer.toString());
        if (rows != 1)
            throw new Exception("create failed, effected num:" + rows);
        return customer;
    }

    @RequestMapping(path = "/customer", method = RequestMethod.PUT)
    @PreAuthorize("hasAnyAuthority('/customer;PUT','/customer;ALL','/customer/**;PUT','/customer/**;ALL','ADMIN')")
    public Customer changeCustomer(@RequestBody Customer customer,
                                   Principal principal) throws Exception {

        if (customer.getCustomerId() == null) throw new Exception("customerId is null");

        logger.debug("pre customer:" + customer.toString());

        //插入记录
        int rows = customerService.update(SecurityUtils.getCurrentProductInstId(principal), customer);
        logger.debug("customer insert return num:" + rows);
        logger.debug("post customer:" + customer.toString());
        if (rows != 1) throw new Exception("change failed, effected num:" + rows);
        return customer;
    }

    @RequestMapping(path = "/customer", method = RequestMethod.DELETE)
    @PreAuthorize("hasAnyAuthority('/customer;DELETE','/customer;ALL','/customer/**;DELETE','/customer/**;ALL','ADMIN')")
    public void deleteCustomer(@RequestParam(value = "customerId", required = true) Long customerId,
                               Principal principal) throws Exception {

        //删除客户信息/客户房产关系/客户房产下的车辆信息
        int rows = customerService.delete(SecurityUtils.getCurrentProductInstId(principal), customerId, null, 0);
        logger.debug("customer delete return num:" + rows);
        if (rows != 1) throw new Exception("delete failed, effected num:" + rows);
    }

    @RequestMapping(path = "/customer/import", method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('/customer/import;POST','/customer/import;ALL','/customer/**;POST','/customer/**;ALL','ADMIN')")
    public Map<String, String> importRequest(@RequestParam(value = "fileUrl", required = true) String fileUrl,
                                             @RequestParam(value = "vendor", required = false) Integer vendor,
                                             Principal principal) {
        if(vendor == null) vendor = new Integer(0); //对象存储服务供应商 0: 腾讯云 1:阿里云 ，默认为0
        String taskId = customerService.batchImportCall(SecurityUtils.getCurrentProductInstId(principal), fileUrl, vendor);
        Map<String, String> result = new HashMap<>();
        result.put("taskId", taskId);
        return result;
    }

    @RequestMapping(path = "/customer/export", method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('/customer/export;POST','/customer/export;ALL','/customer/**;POST','/customer/**;ALL','ADMIN')")
    public Map<String, String> exportRequest(@RequestParam(value = "vendor", required = false) Integer vendor,
                                             Principal principal) {
        if (vendor == null) vendor = new Integer(0); //对象存储服务供应商 0: 腾讯云 1:阿里云 ，默认为0
        String taskId = customerService.batchExportCall(SecurityUtils.getCurrentProductInstId(principal), vendor);
        Map<String, String> result = new HashMap<>();
        result.put("taskId", taskId);
        return result;
    }

    @RequestMapping(path = "/customer/import", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('/customer/import;GET','/customer/import;ALL','/customer/**;GET','/customer/**;ALL','ADMIN')")
    public Map<Object, Object> getImportTaskResult(@RequestParam(value = "taskId", required = true) String taskId,
                                             Principal principal) {
        return customerService.getTaskStatus(SecurityUtils.getCurrentProductInstId(principal), taskId, "import");
    }

    @RequestMapping(path = "/customer/export", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('/customer/export;GET','/customer/export;ALL','/customer/**;GET','/customer/**;ALL','ADMIN')")
    public Map<Object, Object> getExportTaskResult(@RequestParam(value = "taskId", required = true) String taskId,
                                             Principal principal) {
        return customerService.getTaskStatus(SecurityUtils.getCurrentProductInstId(principal), taskId, "export");
    }


}
