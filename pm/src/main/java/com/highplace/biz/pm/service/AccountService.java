package com.highplace.biz.pm.service;

import com.highplace.biz.pm.client.OAuthServiceClient;
import com.highplace.biz.pm.domain.org.Employee;
import com.highplace.biz.pm.domain.system.Account;
import com.highplace.biz.pm.domain.ui.AccountViewBean;
import com.highplace.biz.pm.domain.ui.EmployeeSearchBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AccountService {

    public static final Logger logger = LoggerFactory.getLogger(AccountService.class);

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private OAuthServiceClient oAuthServiceClient;

    //查询员工信息列表
    public Map<String, Object> query(String productInstId, EmployeeSearchBean searchBean, boolean noPageSortFlag) {

        Map<String, Object> result = employeeService.query(productInstId, searchBean, false, true);
        List<Employee> employeeList = (List<Employee>) result.get("data");
        List<AccountViewBean> accountViewBeanList = new ArrayList<>();
        AccountViewBean accountViewBean;
        for(Employee employee : employeeList) {
            //从oauth_service获取账号相关信息
            accountViewBean = (AccountViewBean) oAuthServiceClient.getUserAccount(employee.getSysUsername());
            accountViewBean.setEmployeeName(employee.getEmployeeName());
            accountViewBean.setDeptName(employee.getDeptName());
            accountViewBean.setPosition(employee.getPosition());
            accountViewBean.setStatus(employee.getStatus());
            accountViewBeanList.add(accountViewBean);
        }
        result.put("data", accountViewBeanList);
        return result;
    }

    //插入账号信息
    public void insert(String productInstId, Account account) {

        //设置产品实例ID
        account.setProductInstId(productInstId);
        oAuthServiceClient.createUserAccount(account);
    }

    //更新账号信息
    public void update(String productInstId, Account account) {

        //设置产品实例ID
        account.setProductInstId(productInstId);
        oAuthServiceClient.modifyUserAccount(account);
    }
}
