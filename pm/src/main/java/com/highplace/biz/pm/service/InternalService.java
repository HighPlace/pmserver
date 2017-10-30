package com.highplace.biz.pm.service;

import com.github.pagehelper.PageHelper;
import com.highplace.biz.pm.dao.base.CarMapper;
import com.highplace.biz.pm.dao.base.CustomerMapper;
import com.highplace.biz.pm.dao.base.PropertyMapper;
import com.highplace.biz.pm.dao.org.DepartmentMapper;
import com.highplace.biz.pm.dao.org.EmployeeMapper;
import com.highplace.biz.pm.dao.service.NoticeMapper;
import com.highplace.biz.pm.dao.service.RequestMapper;
import com.highplace.biz.pm.domain.base.*;
import com.highplace.biz.pm.domain.org.Department;
import com.highplace.biz.pm.domain.org.DepartmentExample;
import com.highplace.biz.pm.domain.org.Employee;
import com.highplace.biz.pm.domain.org.EmployeeExample;
import com.highplace.biz.pm.domain.service.Notice;
import com.highplace.biz.pm.domain.service.Request;
import com.highplace.biz.pm.service.util.CommonUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static com.highplace.biz.pm.service.CustomerService.*;
import static com.highplace.biz.pm.service.DepartmentService.PREFIX_DEPARTMENT_NAME_KEY;
import static com.highplace.biz.pm.service.EmployeeService.*;
import static com.highplace.biz.pm.service.NoticeService.*;
import static com.highplace.biz.pm.service.PropertyService.*;
import static com.highplace.biz.pm.service.util.RequestService.*;

@Component
public class InternalService {

    public static final Logger logger = LoggerFactory.getLogger(InternalService.class);

    //reload内存时每次从数据库读取的的记录条数
    public static final int CACHE_RELOAD_BATCH_SIZE = 100; //防止内存溢出,一次操作100条记录

    //定时任务全局锁在redis key前缀
    public static final String PREFIX_REDIS_KEY_LOCK = "SCHEDULED_TASK_RUN_LOCK_";
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private CarMapper carMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private PropertyMapper propertyMapper;
    @Autowired
    private DepartmentMapper departmentMapper;
    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private NoticeMapper noticeMapper;
    @Autowired
    private RequestMapper requestMapper;

    //可能存在多实例的情况，为避免多实例的定时任务重复执行，需要加一个全局锁
    public boolean canRun(String taskName, TASK_PERIOD_ENUM taskPeriod) {

        String redisKeyPrefix = PREFIX_REDIS_KEY_LOCK + taskName;
        String periodKey;
        TimeUnit expireTimeUnit;
        long expireTime;

        switch (taskPeriod) {
            case PER_SECOND:
                periodKey = CommonUtils.getTimeString(CommonUtils.FORMAT_SECOND).replaceAll(" ", "");
                //10分钟后清除redis
                expireTimeUnit = TimeUnit.MINUTES;
                expireTime = 10;
                break;

            case PER_MINUTE:
                periodKey = CommonUtils.getTimeString(CommonUtils.FORMAT_MINUTE).replaceAll(" ", "");
                //10分钟后清除redis
                expireTimeUnit = TimeUnit.MINUTES;
                expireTime = 10;
                break;

            case PER_HOUR:
                periodKey = CommonUtils.getTimeString(CommonUtils.FORMAT_HOUR).replaceAll(" ", "");
                //2小时后清除redis
                expireTimeUnit = TimeUnit.HOURS;
                expireTime = 2;
                break;

            case PER_DAY:
                periodKey = CommonUtils.getTimeString(CommonUtils.FORMAT_DAY).replaceAll(" ", "");
                //2天后清除redis
                expireTimeUnit = TimeUnit.DAYS;
                expireTime = 2;
                break;

            case PER_MONTH:
                periodKey = CommonUtils.getTimeString(CommonUtils.FORMAT_MONTH).replaceAll(" ", "");
                //2月后清除redis
                expireTimeUnit = TimeUnit.DAYS;
                expireTime = 62;
                break;

            default:
                return false;
        }

        String redisKey = redisKeyPrefix + periodKey;

        /* 改代码解决不了分布式问题,get后,可能有多个client set
        String value = stringRedisTemplate.opsForValue().get(redisKey);
        //如果没有锁,则加锁
        if (value == null) {
            stringRedisTemplate.opsForValue().set(redisKey, "locked", expireTime, expireTimeUnit);
            logger.info(taskName + " get lock, can run");
            return true;
        } else { //有锁,则返回false
            logger.info(taskName + " lock exists, can't run");
            return false;
        }
        */

        //引入分布式锁机制
        if (stringRedisTemplate.opsForValue().setIfAbsent(redisKey, "locked")) {
            //setIfAbsent成功，说明没有锁，可以执行
            stringRedisTemplate.expire(redisKey, expireTime, expireTimeUnit);
            logger.info(taskName + " get lock, can run");
            return true;
        } else {
            //否则，不可执行
            logger.info(taskName + " lock exists, can't run");
            return false;
        }
    }

    @Scheduled(cron = "0 12 1 * * ?")   //每天1点12分执行一次，全量更新客户资料相关cache内容
    public void reloadCustomerRedisValue() {

        if (canRun("reloadCustomerRedisValue", TASK_PERIOD_ENUM.PER_DAY)) {
            ///// reload 车牌号 cache ////////
            long totalCount = carMapper.countByExample(new CarExample());
            long pages = (totalCount % CACHE_RELOAD_BATCH_SIZE == 0) ? totalCount / 100 : totalCount / 100 + 1;

            //维护一个已经清除了cache的productInstId列表
            Set<String> emptyProductInstIdSet = new HashSet<>();

            List<Car> carList;
            for (int i = 1; i <= pages; i++) {
                PageHelper.startPage(i, CACHE_RELOAD_BATCH_SIZE);
                carList = carMapper.selectAllProductInstIdAndPlateNo();
                for (Car car : carList) {

                    //如果已经清除了cache的productInstId列表不包含当前id，则清除cache，并加入到列表中
                    if (!emptyProductInstIdSet.contains(car.getProductInstId())) {
                        stringRedisTemplate.delete(PREFIX_CUSTOMER_PLATENO_KEY + car.getProductInstId());
                        emptyProductInstIdSet.add(car.getProductInstId());
                    }
                    stringRedisTemplate.opsForSet().add(PREFIX_CUSTOMER_PLATENO_KEY + car.getProductInstId(), car.getPlateNo());
                }
            }
            logger.info("reload customer plateNo cache success");

            ///// reload 客户姓名和电话 ///////
            totalCount = customerMapper.countByExample(new CustomerExample());
            pages = (totalCount % CACHE_RELOAD_BATCH_SIZE == 0) ? totalCount / 100 : totalCount / 100 + 1;

            //维护一个已经清除了cache的productInstId列表
            emptyProductInstIdSet = new HashSet<>();

            List<Customer> customerList;
            for (int i = 1; i <= pages; i++) {
                PageHelper.startPage(i, CACHE_RELOAD_BATCH_SIZE);
                customerList = customerMapper.selectAllProductInstIdAndNameAndPhone();
                for (Customer customer : customerList) {

                    //如果已经清除了cache的productInstId列表不包含当前id，则清除cache，并加入到列表中
                    if (!emptyProductInstIdSet.contains(customer.getProductInstId())) {
                        stringRedisTemplate.delete(PREFIX_CUSTOMER_NAME_KEY + customer.getProductInstId());
                        stringRedisTemplate.delete(PREFIX_CUSTOMER_PHONE_KEY + customer.getProductInstId());
                        emptyProductInstIdSet.add(customer.getProductInstId());
                    }
                    stringRedisTemplate.opsForSet().add(PREFIX_CUSTOMER_NAME_KEY + customer.getProductInstId(), customer.getCustomerName());
                    stringRedisTemplate.opsForSet().add(PREFIX_CUSTOMER_PHONE_KEY + customer.getProductInstId(), customer.getPhone());
                }
            }
            logger.info("reload customer name and phone cache success");
        }
    }

    @Scheduled(cron = "0 18 1 * * ?")   //每天1点18分执行一次，全量更新房产资料相关cache内容
    public void reloadPropertyRedisValue() {

        if (canRun("reloadPropertyRedisValue", TASK_PERIOD_ENUM.PER_DAY)) {
            ///// reload 分区/楼号/单元号 cache ////////
            long totalCount = propertyMapper.countByExample(new PropertyExample());
            long pages = (totalCount % CACHE_RELOAD_BATCH_SIZE == 0) ? totalCount / 100 : totalCount / 100 + 1;

            //维护一个已经清除了cache的key列表
            Set<String> emptyZoneKeySet = new HashSet<>();
            Set<String> emptyZoneBuildingKeySet = new HashSet<>();
            Set<String> emptyZoneBuildingUnitIdKeySet = new HashSet<>();

            List<Property> propertyList;
            for (int i = 1; i <= pages; i++) {
                PageHelper.startPage(i, CACHE_RELOAD_BATCH_SIZE);
                propertyList = propertyMapper.selectDistinctProductInstIdAndIDs();
                for (Property property : propertyList) {

                    String redisKeyForZoneId = PREFIX_PROPERTY_ZONEID_KEY + property.getProductInstId();
                    String redisKeyForBuildIdPrefix = PREFIX_PROPERTY_BUILDINGID_KEY + property.getProductInstId() + property.getZoneId();
                    String redisKeyForUnitIdPrefix = PREFIX_PROPERTY_UNITID_KEY + property.getProductInstId() + property.getBuildingId();

                    //zoneId
                    if (!emptyZoneKeySet.contains(redisKeyForZoneId)) {
                        stringRedisTemplate.delete(redisKeyForZoneId);
                        emptyZoneKeySet.add(redisKeyForZoneId);
                    }
                    stringRedisTemplate.opsForSet().add(redisKeyForZoneId, property.getZoneId());

                    //buildingId
                    if (!emptyZoneBuildingKeySet.contains(redisKeyForBuildIdPrefix)) {
                        stringRedisTemplate.delete(redisKeyForBuildIdPrefix);
                        emptyZoneKeySet.add(redisKeyForBuildIdPrefix);
                    }
                    stringRedisTemplate.opsForSet().add(redisKeyForBuildIdPrefix, property.getBuildingId());

                    //unitId
                    if (!emptyZoneBuildingUnitIdKeySet.contains(redisKeyForUnitIdPrefix)) {
                        stringRedisTemplate.delete(redisKeyForUnitIdPrefix);
                        emptyZoneKeySet.add(redisKeyForUnitIdPrefix);
                    }
                    stringRedisTemplate.opsForSet().add(redisKeyForUnitIdPrefix, property.getUnitId());
                }
            }
            logger.info("reload property zoneId buildingId unitId cache success");
        }
    }

    @Scheduled(cron = "0 24 1 * * ?")   //每天1点24分执行一次，全量更新部门资料相关cache内容
    public void reloadDepartmentRedisValue() {

        if (canRun("reloadDepartmentRedisValue", TASK_PERIOD_ENUM.PER_DAY)) {
            ///// reload 部门名称和ID cache ////////
            long totalCount = departmentMapper.countByExample(new DepartmentExample());
            long pages = (totalCount % CACHE_RELOAD_BATCH_SIZE == 0) ? totalCount / 100 : totalCount / 100 + 1;

            //清空所有的部门key
            Set<String> keys = redisTemplate.keys(PREFIX_DEPARTMENT_NAME_KEY + "*");
            redisTemplate.delete(keys);

            List<Department> departmentList;
            String redisKey;
            for (int i = 1; i <= pages; i++) {
                PageHelper.startPage(i, CACHE_RELOAD_BATCH_SIZE);
                departmentList = departmentMapper.selectByExample(new DepartmentExample());
                for (Department department : departmentList) {

                    if (department.getLevel() == 1) {  //一级部门
                        redisKey = PREFIX_DEPARTMENT_NAME_KEY + department.getProductInstId();
                    } else {
                        if (department.getSuperiorDeptId() == null) return;
                        redisKey = PREFIX_DEPARTMENT_NAME_KEY + department.getProductInstId() + "_" + department.getSuperiorDeptId();
                    }
                    redisTemplate.opsForHash().put(redisKey, department.getDeptId(), department.getDeptName());
                }
            }
            logger.info("reload department cache success");
        }
    }

    @Scheduled(cron = "0 29 1 * * ?")   //每天1点29分执行一次，全量更新员工资料相关cache内容
    public void reloadEmployeeRedisValue() {

        if (canRun("reloadEmployeeRedisValue", TASK_PERIOD_ENUM.PER_DAY)) {
            ///// reload position/employeeName/phone cache ////////
            long totalCount = employeeMapper.countByExample(new EmployeeExample());
            long pages = (totalCount % CACHE_RELOAD_BATCH_SIZE == 0) ? totalCount / 100 : totalCount / 100 + 1;

            //清空所有的position key(包括全量的 和 部门下的)
            Set<String> keys = redisTemplate.keys(PREFIX_EMPLOYEE_POSITION_KEY + "*");
            redisTemplate.delete(keys);

            //清空所有的employeeName key
            keys = redisTemplate.keys(PREFIX_EMPLOYEE_NAME_KEY + "*");
            redisTemplate.delete(keys);

            //清空所有的phone key
            keys = redisTemplate.keys(PREFIX_EMPLOYEE_PHONE_KEY + "*");
            redisTemplate.delete(keys);

            List<Employee> employeeList;
            for (int i = 1; i <= pages; i++) {
                PageHelper.startPage(i, CACHE_RELOAD_BATCH_SIZE);
                employeeList = employeeMapper.selectByExample(new EmployeeExample());
                for (Employee employee : employeeList) {

                    if (StringUtils.isNotEmpty(employee.getPosition()))
                        stringRedisTemplate.opsForSet().add(PREFIX_EMPLOYEE_POSITION_KEY + employee.getProductInstId(), employee.getPosition());

                    if (employee.getDeptId() != null)
                        stringRedisTemplate.opsForSet().add(PREFIX_EMPLOYEE_POSITION_KEY + employee.getProductInstId() + "_" + employee.getDeptId(), employee.getPosition());

                    if (StringUtils.isNotEmpty(employee.getEmployeeName()))
                        stringRedisTemplate.opsForSet().add(PREFIX_EMPLOYEE_NAME_KEY + employee.getProductInstId(), employee.getEmployeeName());

                    if (StringUtils.isNotEmpty(employee.getPhone()))
                        stringRedisTemplate.opsForSet().add(PREFIX_EMPLOYEE_PHONE_KEY + employee.getProductInstId(), employee.getPhone());
                }
            }
            logger.info("reload employee cache success");
        }
    }

    @Scheduled(cron = "0 34 1 * * ?")   //每天1点34分执行一次，全量更新公告类型相关cache内容
    public void reloadNoticeRedisValue() {

        if (canRun("reloadNoticeRedisValue", TASK_PERIOD_ENUM.PER_DAY)) {

            List<Notice> noticeList = noticeMapper.selectDistinctProductInstIdAndType();

            //维护一个已加了缺省值的productInstId列表
            Set<String> defaultProductInstIdSet = new HashSet<>();
            for (Notice notice : noticeList) {

                stringRedisTemplate.opsForSet().add(PREFIX_NOTICE_TYPE_KEY + notice.getProductInstId(), notice.getType());

                //设置缺省的type
                if(!defaultProductInstIdSet.contains(notice.getProductInstId())) {
                    stringRedisTemplate.opsForSet().add(PREFIX_NOTICE_TYPE_KEY + notice.getProductInstId(), DEFAULT_NOTICE_TYPE1);
                    stringRedisTemplate.opsForSet().add(PREFIX_NOTICE_TYPE_KEY + notice.getProductInstId(), DEFAULT_NOTICE_TYPE2);
                    defaultProductInstIdSet.add(notice.getProductInstId());
                }
            }
        }
        logger.info("reload notice cache success");
    }

    @Scheduled(cron = "0 39 1 * * ?")   //每天1点39分执行一次，全量更新服务工单大类和小类相关cache内容
    public void reloadRequestRedisValue() {

        if (canRun("reloadRequestRedisValue", TASK_PERIOD_ENUM.PER_DAY)) {

            List<Request> requestList = requestMapper.selectDistinctProductInstIdAndTypeAndSubType();

            //维护一个已加了缺省值的productInstId列表
            Set<String> defaultProductInstIdSet = new HashSet<>();
            for (Request request : requestList) {

                stringRedisTemplate.opsForSet().add(PREFIX_REQUEST_TYPE_KEY + request.getProductInstId(), request.getType());
                stringRedisTemplate.opsForSet().add(PREFIX_REQUEST_SUB_TYPE_KEY + request.getProductInstId() + "_" + request.getType(), request.getSubType());

                //设置缺省的type
                if(!defaultProductInstIdSet.contains(request.getProductInstId())) {
                    stringRedisTemplate.opsForSet().add(PREFIX_REQUEST_TYPE_KEY + request.getProductInstId(), DEFAULT_REQUEST_TYPE1);
                    stringRedisTemplate.opsForSet().add(PREFIX_REQUEST_TYPE_KEY + request.getProductInstId(), DEFAULT_REQUEST_TYPE2);
                    stringRedisTemplate.opsForSet().add(PREFIX_REQUEST_TYPE_KEY + request.getProductInstId(), DEFAULT_REQUEST_TYPE3);
                    defaultProductInstIdSet.add(request.getProductInstId());
                }
            }
        }
        logger.info("reload request cache success");
    }

    //定时任务周期
    public static enum TASK_PERIOD_ENUM {
        PER_SECOND, PER_MINUTE, PER_HOUR, PER_DAY, PER_MONTH;
    }
}
