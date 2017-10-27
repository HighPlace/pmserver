package com.highplace.biz.pm.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.highplace.biz.pm.dao.org.DepartmentMapper;
import com.highplace.biz.pm.dao.org.EmployeeMapper;
import com.highplace.biz.pm.domain.org.Department;
import com.highplace.biz.pm.domain.org.DepartmentExample;
import com.highplace.biz.pm.domain.org.Employee;
import com.highplace.biz.pm.domain.org.EmployeeExample;
import com.highplace.biz.pm.domain.ui.EmployeeSearchBean;
import com.highplace.biz.pm.service.util.CommonUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import tk.mybatis.orderbyhelper.OrderByHelper;

import java.util.*;
import java.util.regex.Pattern;

@Service
public class EmployeeService {

    public static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    //写入redis的key前缀, 后面加上productInstId
    public static final String PREFIX_EMPLOYEE_POSITION_KEY = "EMPLOYEE_POSITION_KEY_";
    public static final String PREFIX_EMPLOYEE_NAME_KEY = "EMPLOYEE_NAME_KEY_";
    public static final String PREFIX_EMPLOYEE_PHONE_KEY = "EMPLOYEE_PHONE_KEY_";

    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private DepartmentMapper departmentMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    //position/employeeName/phone以set数据结构缓存到redis中
    public void addRedisValue(String productInstId, Employee employee) {
        if (productInstId == null) return;

        if (StringUtils.isNotEmpty(employee.getPosition())) {
            stringRedisTemplate.opsForSet().add(PREFIX_EMPLOYEE_POSITION_KEY + productInstId, employee.getPosition());

            //如果部门id不为空，还加上部门ID对应的职位
            if( employee.getDeptId() != null )
                stringRedisTemplate.opsForSet().add(PREFIX_EMPLOYEE_POSITION_KEY + productInstId + "_" + employee.getDeptId(), employee.getPosition());
        }

        if (StringUtils.isNotEmpty(employee.getEmployeeName()))
            stringRedisTemplate.opsForSet().add(PREFIX_EMPLOYEE_NAME_KEY + productInstId, employee.getEmployeeName());

        if (StringUtils.isNotEmpty(employee.getPhone()))
            stringRedisTemplate.opsForSet().add(PREFIX_EMPLOYEE_PHONE_KEY + productInstId, employee.getPhone());
    }

    //从redis中查询position/employeeName/phone列表，用于前端在检索时快速提示(模糊查询)
    public Map<String, Object> rapidSearch(String productInstId, String entity, String searchValue , String deptId) {

        String redisKey;
        //对于position,如果传入了deptId，则查对应deptId下的position
        if (entity.equals("position")) {

            redisKey = PREFIX_EMPLOYEE_POSITION_KEY + productInstId;
            if( deptId!=null )
                redisKey = PREFIX_EMPLOYEE_POSITION_KEY + productInstId + "_" + deptId;

        } else if (entity.equals("name")) {

            redisKey = PREFIX_EMPLOYEE_NAME_KEY + productInstId;

        } else if (entity.equals("phone")) {

            redisKey = PREFIX_EMPLOYEE_PHONE_KEY + productInstId;

        } else {
            return null;
        }

        Map<String, Object> result = new HashMap<String, Object>();

        Set<String> sEntity = stringRedisTemplate.opsForSet().members(redisKey);
        if (sEntity == null) {

            result.put("data", null);
        } else {

            //对于position,如果传入了deptId，则返回该deptId下的全量position列表
            if (entity.equals("position") && (deptId != null)) {
                result.put("data", sEntity);

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
        }
        return result;
    }

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

    //插入员工信息
    public int insert(String productInstId, Employee employee) {

        //设置产品实例ID
        employee.setProductInstId(productInstId);

        //必须要有部门id
        if (employee.getDeptId() == 0) return 0;

        //查询部门ID是否存在
        DepartmentExample departmentExample = new DepartmentExample();
        DepartmentExample.Criteria dCriteria = departmentExample.createCriteria();
        dCriteria.andProductInstIdEqualTo(productInstId);
        dCriteria.andDeptIdEqualTo(employee.getDeptId());
        List<Department> departmentList = departmentMapper.selectByExample(departmentExample);

        //没有deptId部门存在
        if (departmentList.size() == 0) return -1;

        //有deptId部门存在,插入员工记录
        int num = employeeMapper.insertSelective(employee);
        if (num == 1) {
            //更新redis
            addRedisValue(productInstId, employee);
        }
        return num;
    }

    //删除员工信息
    public int delete(String productInstId, Long employeeId) {

        Employee employee = employeeMapper.selectByPrimaryKey(employeeId);
        //员工状态: 0:在岗 1:不在岗 2:离职, 只有离职的员工才能删除
        if ((employee != null) && (employee.getStatus() != 2)) return -1;

        //删除员工
        EmployeeExample employeeExample = new EmployeeExample();
        EmployeeExample.Criteria criteria = employeeExample.createCriteria();
        criteria.andProductInstIdEqualTo(productInstId);
        criteria.andEmployeeIdEqualTo(employeeId);
        return employeeMapper.deleteByExample(employeeExample);
    }

    //修改员工信息
    public int update(String productInstId, Employee employee) {

        //如果有传入deptId,要查看deptid是否存在
        if (employee.getDeptId() != null) {

            DepartmentExample departmentExample = new DepartmentExample();
            DepartmentExample.Criteria dCriteria = departmentExample.createCriteria();
            dCriteria.andProductInstIdEqualTo(productInstId);
            dCriteria.andDeptIdEqualTo(employee.getDeptId());
            List<Department> departmentList = departmentMapper.selectByExample(departmentExample);
            if (departmentList.size() == 0) {
                //没有deptId部门存在
                return -1;
            }
        }

        EmployeeExample example = new EmployeeExample();
        EmployeeExample.Criteria criteria = example.createCriteria();
        //产品实例ID，必须填入
        criteria.andProductInstIdEqualTo(productInstId);
        criteria.andEmployeeIdEqualTo(employee.getEmployeeId());
        int num = employeeMapper.updateByExampleSelective(employee, example);
        if (num == 1) {
            //更新redis
            addRedisValue(productInstId, employee);
        }
        return num;
    }
}
