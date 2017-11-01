package com.highplace.biz.pm.service;

import com.highplace.biz.pm.client.OAuthServiceClient;
import com.highplace.biz.pm.dao.org.EmployeeMapper;
import com.highplace.biz.pm.domain.org.Employee;
import com.highplace.biz.pm.domain.org.EmployeeExample;
import com.highplace.biz.pm.domain.system.Account;
import com.highplace.biz.pm.domain.ui.EmployeeSearchBean;
import com.highplace.biz.pm.service.util.CommonUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Pattern;

@Service
public class AccountService {

    public static final Logger logger = LoggerFactory.getLogger(AccountService.class);

    //写入redis的key前缀
    public static final String ACCOUNT_USERNAME_KEY = "ACCOUNT_USERNAME_KEY_";

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private OAuthServiceClient oAuthServiceClient;

    //username以set数据结构缓存到redis中
    public void addRedisValue(String productInstId, String username) {
        if (StringUtils.isEmpty(productInstId)) return;
        stringRedisTemplate.opsForSet().add(ACCOUNT_USERNAME_KEY, username);
        stringRedisTemplate.opsForSet().add(ACCOUNT_USERNAME_KEY + productInstId, username);
    }

    //从redis中查询username列表，用于前端在检索时快速提示(模糊查询)
    public Map<String, Object> rapidSearch(String productInstId, String searchValue) {

        String redisKey = ACCOUNT_USERNAME_KEY + productInstId;

        Map<String, Object> result = new HashMap<String, Object>();
        Set<String> sEntity = stringRedisTemplate.opsForSet().members(redisKey);
        if (sEntity == null) {
            result.put("data", null);
        } else {
            List<String> dataList = new ArrayList();
            Pattern pattern = Pattern.compile(searchValue, Pattern.CASE_INSENSITIVE); //大小写不敏感
            int i = 0;
            for (String entityValue : sEntity) {
                i++;
                if (pattern.matcher(entityValue).find()) dataList.add(entityValue);  //find()模糊匹配  matches()精确匹配
                if (i >= 10) break; //匹配到超过10条记录，退出
            }
            result.put("data", dataList);

        }
        return result;
    }

    //从redis中查询当前输入的username是否已经存在
    public boolean checkUsernameExists(String productInstId, String username) {
        return stringRedisTemplate.opsForSet().isMember(ACCOUNT_USERNAME_KEY, username);
    }


    //查询员工信息列表
    public Map<String, Object> query(String productInstId, EmployeeSearchBean searchBean, boolean noPageSortFlag) {

        Map<String, Object> result = employeeService.query(productInstId, searchBean, false, true);
        List<Employee> employeeList = (List<Employee>) result.get("data");
        List<Account> accountList = new ArrayList<>();
        Account account;
        for (Employee employee : employeeList) {
            //从oauth_service获取账号相关信息
            account = oAuthServiceClient.getUserAccount(employee.getSysUsername());
            account.setEmployeeId(employee.getEmployeeId());
            account.setEmployeeName(employee.getEmployeeName());
            account.setDeptName(employee.getDeptName());
            account.setPosition(employee.getPosition());
            account.setStatus(employee.getStatus());
            accountList.add(account);
        }
        result.put("data", accountList);
        return result;
    }

    //插入账号信息
    public int insert(String productInstId, Account account) {

        //设置产品实例ID
        account.setProductInstId(productInstId);

        //检查是否已经创建帐号
        EmployeeExample employeeExample = new EmployeeExample();
        EmployeeExample.Criteria criteria = employeeExample.createCriteria();
        criteria.andProductInstIdEqualTo(account.getProductInstId());
        criteria.andEmployeeIdEqualTo(account.getEmployeeId());
        List<Employee> employeeList = employeeMapper.selectByExample(employeeExample);
        if (employeeList == null || employeeList.size() == 0) { //员工id不存在
            return -1;
        } else {
            if (StringUtils.isNotEmpty(employeeList.get(0).getSysUsername())) //已经创建用户
                return -2;
        }

        //同步电话和邮箱信息
        account.setMobileNo(employeeList.get(0).getPhone());
        account.setEmail(employeeList.get(0).getEmail());

        //如果没有传入密码，则创建8位随机密码
        if (StringUtils.isEmpty(account.getPassword())) {
            account.setPassword(CommonUtils.createPassWord(8));
        }

        //创建账户
        oAuthServiceClient.createUserAccount(account);

        //将账户名更新到员工信息中
        Employee employee = new Employee();
        employee.setEmployeeId(account.getEmployeeId());
        employee.setSysUsername(account.getUsername());
        employeeMapper.updateByPrimaryKeySelective(employee);

        //写入cache
        addRedisValue(productInstId, account.getUsername());
        return 1;
    }

    //更新账号信息
    public void update(String productInstId, Account account) {

        //设置产品实例ID
        account.setProductInstId(productInstId);
        oAuthServiceClient.modifyUserAccount(account);
    }
}
