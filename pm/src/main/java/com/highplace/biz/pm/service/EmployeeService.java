package com.highplace.biz.pm.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.highplace.biz.pm.dao.org.EmployeeMapper;
import com.highplace.biz.pm.domain.org.Employee;
import com.highplace.biz.pm.domain.org.EmployeeExample;
import com.highplace.biz.pm.domain.ui.EmployeeSearchBean;
import com.highplace.biz.pm.service.util.CommonUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.orderbyhelper.OrderByHelper;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmployeeService {

    public static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    @Autowired
    private EmployeeMapper employeeMapper;

    //查询房产信息列表
    public Map<String, Object> query(String productInstId, EmployeeSearchBean searchBean, boolean noPageSortFlag) {

        EmployeeExample example = new EmployeeExample();
        EmployeeExample.Criteria criteria = example.createCriteria();

        //产品实例ID，必须填入
        criteria.andProductInstIdEqualTo(productInstId);

        //查询条件判断
        if (searchBean.getDeptId() != null)
            criteria.andDeptIdEqualTo(searchBean.getDeptId());

        if (StringUtils.isNotEmpty(searchBean.getPosition()))
            criteria.andPositionLike("%" + searchBean.getPosition() + "%"); //模糊查询

        if (StringUtils.isNotEmpty(searchBean.getEmployeeName()))
            criteria.andEmployeeNameLike("%" + searchBean.getEmployeeName() + "%"); //模糊查询

        if (StringUtils.isNotEmpty(searchBean.getPhone()))
            criteria.andPhoneLike("%" + searchBean.getPhone() + "%"); //模糊查询

        if (searchBean.getStatus() != null)
            criteria.andStatusEqualTo(searchBean.getStatus());

        //如果noPageSortFlag 不为true
        if (!noPageSortFlag) {
            //设置分页参数
            if (searchBean.getPageNum() != null && searchBean.getPageSize() != null)
                PageHelper.startPage(searchBean.getPageNum(), searchBean.getPageSize());

            //设置排序字段,注意前端传入的是驼峰风格字段名,需要转换成数据库下划线风格字段名
            if (searchBean.getSortField() != null) {
                if (searchBean.getSortType() == null) {
                    OrderByHelper.orderBy(CommonUtils.underscoreString(searchBean.getSortField()) + " asc"); //默认升序
                } else {
                    OrderByHelper.orderBy(CommonUtils.underscoreString(searchBean.getSortField()) + " " + searchBean.getSortType());
                }
            }
        }

        //查询结果
        List<Employee> employeeList = employeeMapper.selectByExampleWithBLOBsWithDeptName(example);

        //总记录数
        long totalCount;

        //判断是否有分页
        if (!noPageSortFlag && searchBean.getPageNum() != null && searchBean.getPageSize() != null) {
            totalCount = ((Page) employeeList).getTotal();
        } else {
            totalCount = employeeList.size();
        }

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("totalCount", totalCount);
        result.put("data", employeeList);
        return result;
    }
}
