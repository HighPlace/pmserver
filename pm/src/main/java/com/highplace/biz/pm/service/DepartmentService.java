package com.highplace.biz.pm.service;

import com.highplace.biz.pm.dao.org.DepartmentMapper;
import com.highplace.biz.pm.dao.org.EmployeeMapper;
import com.highplace.biz.pm.domain.org.Department;
import com.highplace.biz.pm.domain.org.DepartmentExample;
import com.highplace.biz.pm.domain.org.EmployeeExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DepartmentService {

    public static final Logger logger = LoggerFactory.getLogger(DepartmentService.class);

    //写入redis的key前缀, 后面加上productInstId
    public static final String PREFIX_DEPARTMENT_NAME_KEY = "DEPARTMENT_NAME_KEY_";

    public static final String MAP_DEPARTMENT_ID = "deptId";
    public static final String MAP_DEPARTMENT_NAME = "deptName";
    public static final String MAP_DEPARTMENT_LEVEL = "level";
    public static final String MAP_DEPARTMENT_SUBDEPARTMENT = "subDeptList";

    @Autowired
    private DepartmentMapper departmentMapper;
    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    //depamrtment ID 和 name 以hash数据结构缓存到redis中
    public void addRedisValue(String productInstId, Long deptId) {

        String redisKey;
        DepartmentExample example = new DepartmentExample();
        DepartmentExample.Criteria criteria = example.createCriteria();
        criteria.andProductInstIdEqualTo(productInstId);
        criteria.andDeptIdEqualTo(deptId);
        List<Department> departmentList = departmentMapper.selectByExample(example);
        if (departmentList.size() > 0) {
            if (departmentList.get(0).getLevel() == 1) {  //一级部门
                redisKey = PREFIX_DEPARTMENT_NAME_KEY + departmentList.get(0).getProductInstId();
            } else {
                if (departmentList.get(0).getSuperiorDeptId() == null) return;
                redisKey = PREFIX_DEPARTMENT_NAME_KEY + departmentList.get(0).getProductInstId() + "_" + departmentList.get(0).getSuperiorDeptId();
            }
            redisTemplate.opsForHash().put(redisKey, departmentList.get(0).getDeptId(), departmentList.get(0).getDeptName());
        }
    }

    //查询部门信息列表
    public Map<String, Object> query(String productInstId, Long superiorDeptId) {

        DepartmentExample example = new DepartmentExample();
        DepartmentExample.Criteria criteria = example.createCriteria();

        //产品实例ID，必须填入
        criteria.andProductInstIdEqualTo(productInstId);
        if (superiorDeptId != null) criteria.andSuperiorDeptIdEqualTo(superiorDeptId);

        //查询结果
        List<Department> departmentList = departmentMapper.selectByExampleWithBLOBs(example);

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("totalCount", departmentList.size());
        result.put("data", departmentList);
        return result;
    }

    //插入部门信息
    public int insert(String productInstId, Department department) {

        //设置产品实例ID
        department.setProductInstId(productInstId);

        //如果有传入superiorDeptId,要先查看superiorDeptId是否存在，并获取到superiorDept的level
        if (department.getSuperiorDeptId() != null) {

            DepartmentExample departmentExample = new DepartmentExample();
            DepartmentExample.Criteria dCriteria = departmentExample.createCriteria();
            dCriteria.andProductInstIdEqualTo(productInstId);
            dCriteria.andDeptIdEqualTo(department.getSuperiorDeptId());
            List<Department> departmentList = departmentMapper.selectByExample(departmentExample);
            if (departmentList.size() == 0) {
                //没有superiorDeptId部门存在
                return -1;
            } else {
                //有,计算出level
                department.setLevel(departmentList.get(0).getLevel() + 1);
            }
        }

        int num = departmentMapper.insertSelective(department);
        if (num == 1) {
            //更新redis
            addRedisValue(productInstId, department.getDeptId());
        }
        return num;
    }

    //删除部门信息
    public int delete(String productInstId, Long deptId) {

        DepartmentExample departmentExample = new DepartmentExample();
        DepartmentExample.Criteria dCriteria = departmentExample.createCriteria();
        dCriteria.andProductInstIdEqualTo(productInstId);
        dCriteria.andSuperiorDeptIdEqualTo(deptId);

        EmployeeExample employeeExample = new EmployeeExample();
        EmployeeExample.Criteria criteria = employeeExample.createCriteria();
        criteria.andProductInstIdEqualTo(productInstId);
        criteria.andDeptIdEqualTo(deptId);

        //如果不存在该部门的下级部门，并且不存在该部门下的员工关系，则可以删除
        if (departmentMapper.countByExample(departmentExample) == 0 && employeeMapper.countByExample(employeeExample) == 0) {

            DepartmentExample example = new DepartmentExample();
            DepartmentExample.Criteria criteria1 = example.createCriteria();
            criteria1.andDeptIdEqualTo(deptId);
            criteria1.andProductInstIdEqualTo(productInstId);
            return departmentMapper.deleteByExample(example);

        } else {
            return -1;
        }
    }

    //修改部门信息
    public int update(String productInstId, Department department) {

        //如果有传入superiorDeptId,要先查看superiorDeptId是否存在，并获取到superiorDept的level
        if (department.getSuperiorDeptId() != null) {

            DepartmentExample departmentExample = new DepartmentExample();
            DepartmentExample.Criteria dCriteria = departmentExample.createCriteria();
            dCriteria.andProductInstIdEqualTo(productInstId);
            dCriteria.andDeptIdEqualTo(department.getSuperiorDeptId());
            List<Department> departmentList = departmentMapper.selectByExample(departmentExample);
            if (departmentList.size() == 0) {
                //没有superiorDeptId部门存在
                return -1;
            } else {
                //有,计算出level
                department.setLevel(departmentList.get(0).getLevel() + 1);
            }
        }

        DepartmentExample example = new DepartmentExample();
        DepartmentExample.Criteria criteria = example.createCriteria();
        //产品实例ID，必须填入
        criteria.andProductInstIdEqualTo(productInstId);
        criteria.andDeptIdEqualTo(department.getDeptId());
        int num = departmentMapper.updateByExampleSelective(department, example);
        if (num == 1) {
            //更新redis
            addRedisValue(productInstId, department.getDeptId());
        }
        return num;
    }

    //从redis中查询所有部门名称，以树形结构返回
    public Map<String, Object> rapidSearchDepartmentTree(String productInstId) {

        String redisKey = PREFIX_DEPARTMENT_NAME_KEY + productInstId;
        Map<Long, String> topLevelDepartmentMap = (Map<Long, String>) redisTemplate.opsForHash().entries(redisKey);

        List<Object> topList = new ArrayList<>();
        for (Map.Entry<Long, String> entry : topLevelDepartmentMap.entrySet()) {

            Map<String, Object> topDepartmentMap = new HashMap<>();
            topDepartmentMap.put(MAP_DEPARTMENT_ID, entry.getKey());
            topDepartmentMap.put(MAP_DEPARTMENT_NAME, entry.getValue());
            getSubDepartment(topDepartmentMap, productInstId, entry.getKey());
            topList.add(topDepartmentMap);
        }

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("data", topList);
        return result;
    }

    //从redis中查询所有部门名称，以二维数组结构返回
    public Map<String, Object> rapidSearchDepartmentTreeNew(String productInstId) {

        String redisKey = PREFIX_DEPARTMENT_NAME_KEY + productInstId;
        Map<Long, String> topLevelDepartmentMap = (Map<Long, String>) redisTemplate.opsForHash().entries(redisKey);

        List<Object> dataList = new LinkedList<>();
        Map<String, Object> dptMap;
        for (Map.Entry<Long, String> entry : topLevelDepartmentMap.entrySet()) {

            dptMap = new LinkedHashMap<>();
            dptMap.put(MAP_DEPARTMENT_ID, entry.getKey());
            dptMap.put(MAP_DEPARTMENT_NAME, entry.getValue());
            dataList.add(dptMap);
            getSubDepartmentNew(dataList, productInstId, entry.getKey(), 1);
        }

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("data", dataList);
        return result;
    }

    private void getSubDepartmentNew(List<Object> dataList, String productInstId, Long superiorDeptId, int level) {

        String redisKey = PREFIX_DEPARTMENT_NAME_KEY + productInstId + "_" + superiorDeptId;
        level = level + 1;
        String prefix = ""; //部门名前面加空格，以示区别是几级部门
        for(int i=0; i<level-1; i++){
            prefix = prefix + " ";
        }

        //取下一级所有的部门
        Map<Long, String> subDepartmentMap = (Map<Long, String>) redisTemplate.opsForHash().entries(redisKey);
        if (subDepartmentMap == null) {
            //如果没有下级部门，则返回
            return;

        } else {
            Map<String, Object> dptMap;
            for (Map.Entry<Long, String> entry : subDepartmentMap.entrySet()) {

                dptMap = new LinkedHashMap<>();
                dptMap.put(MAP_DEPARTMENT_ID, entry.getKey());
                dptMap.put(MAP_DEPARTMENT_NAME, prefix + entry.getValue());
                dataList.add(dptMap);

                //取该部门的再下级部门
                getSubDepartmentNew(dataList, productInstId, entry.getKey(), level);
            }
        }

    }

    private void getSubDepartment(Map<String, Object> superiorDepartmentMap, String productInstId, Long superiorDeptId) {

        String redisKey = PREFIX_DEPARTMENT_NAME_KEY + productInstId + "_" + superiorDeptId;

        //取下一级所有的部门
        Map<Long, String> subDepartmentMap = (Map<Long, String>) redisTemplate.opsForHash().entries(redisKey);

        if (subDepartmentMap == null) {

            //如果没有下级部门，则返回
            return;

        } else {

            //有下级部门
            List<Object> subList = new ArrayList<>(); //list 存放下级部门列表
            for (Map.Entry<Long, String> entry : subDepartmentMap.entrySet()) {

                //每一个下级部门又生成一个Map
                Map<String, Object> departmentMap = new HashMap<>();
                departmentMap.put(MAP_DEPARTMENT_ID, entry.getKey());
                departmentMap.put(MAP_DEPARTMENT_NAME, entry.getValue());

                //取该部门的再下级部门
                getSubDepartment(departmentMap, productInstId, entry.getKey());

                //加到list中去
                subList.add(departmentMap);
            }

            //加入到上级部门的map中
            superiorDepartmentMap.put(MAP_DEPARTMENT_SUBDEPARTMENT, subList);
        }

    }
}
