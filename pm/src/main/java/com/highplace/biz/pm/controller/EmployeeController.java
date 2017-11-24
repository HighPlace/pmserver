package com.highplace.biz.pm.controller;

import com.highplace.biz.pm.domain.org.Employee;
import com.highplace.biz.pm.domain.ui.EmployeeSearchBean;
import com.highplace.biz.pm.service.EmployeeService;
import com.highplace.biz.pm.service.common.TaskStatusService;
import com.highplace.biz.pm.service.util.SecurityUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Map;

@RestController
public class EmployeeController {

    public static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private TaskStatusService taskStatusService;

    @RequestMapping(path = "/employee", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('/employee;GET','/employee;ALL','/employee/**;GET','/employee/**;ALL','ADMIN')")
    public Map<String, Object> getEmployee(@Valid EmployeeSearchBean searchBean,
                                           Principal principal) {

        logger.debug("EmployeeSearchBean: {}" , searchBean.toString());
        return employeeService.query(SecurityUtils.getCurrentProductInstId(principal), searchBean, false, false);
    }

    @RequestMapping(path = "/employee/{entity}", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('/employee;GET','/employee;ALL','/employee/**;GET','/employee/**;ALL','ADMIN')")
    public Map<String, Object> getEntityList(@PathVariable String entity,
                                             @RequestParam(value = "input", required = true) String input,
                                             @RequestParam(value = "deptId", required = false) Long deptId,
                                             Principal principal) {
        //entity 支持position/name/phone 三种
        return employeeService.rapidSearch(SecurityUtils.getCurrentProductInstId(principal), entity, input, deptId);
    }

    @RequestMapping(path = "/employee", method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('/employee;POST','/employee;ALL','/employee/**;POST','/employee/**;ALL','ADMIN')")
    public Employee createEmployee(@Valid @RequestBody Employee employee,
                                   Principal principal) throws Exception {

        logger.debug("pre employee: {}" ,employee.toString());
        if (StringUtils.isEmpty(employee.getEmployeeName())) throw new Exception("employeeName is empty");

        //插入记录
        int rows = employeeService.insert(SecurityUtils.getCurrentProductInstId(principal), employee);
        logger.debug("employee insert return num: {}" , rows);
        logger.debug("post employee: {}" , employee.toString());
        if (rows == -1)
            throw new Exception("部门id不存在,请检查");
        else if (rows != 1)
            throw new Exception("create failed, effected num:" + rows);
        return employee;
    }

    @RequestMapping(path = "/employee", method = RequestMethod.DELETE)
    @PreAuthorize("hasAnyAuthority('/employee;DELETE','/employee;ALL','/employee/**;DELETE','/employee/**;ALL','ADMIN')")
    public void deleteEmployee(@RequestParam(value = "employeeId", required = true) Long employeeId,
                               Principal principal) throws Exception {

        //删除记录
        int rows = employeeService.delete(SecurityUtils.getCurrentProductInstId(principal), employeeId);
        logger.debug("employee delete return num: {}" , rows);
        if (rows != 1) {
            if (rows == -1)
                throw new Exception("不能删除该员工资料,请检查该员工资料是否存在,并且是否已离职");
            else
                throw new Exception("delete failed, effected num:" + rows);
        }
    }

    @RequestMapping(path = "/employee", method = RequestMethod.PUT)
    @PreAuthorize("hasAnyAuthority('/employee;PUT','/employee;ALL','/employee/**;PUT','/employee/**;ALL','ADMIN')")
    public Employee changeEmployee(@RequestBody Employee employee,
                                   Principal principal) throws Exception {

        if (employee.getEmployeeId() == null) throw new Exception("employeeId is null");

        logger.debug("pre department: {}" , employee.toString());

        //插入记录
        int rows = employeeService.update(SecurityUtils.getCurrentProductInstId(principal), employee);
        logger.debug("employee insert return num: {}" , rows);
        logger.debug("post employee: {}" , employee.toString());
        if (rows == -1)
            throw new Exception("部门id不存在,请检查");
        else if (rows != 1)
            throw new Exception("change failed, effected num:" + rows);

        return employee;
    }

    ///// employee import and export task ////

    @RequestMapping(path = "/employee/import", method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('/employee/import;POST','/employee/import;ALL','/employee/**;POST','/employee/**;ALL','ADMIN')")
    public Map<String, Object> importRequest(@RequestParam(value = "fileUrl", required = true) String fileUrl,
                                             @RequestParam(value = "vendor", required = false) Integer vendor,
                                             Principal principal) {

        //对象存储服务供应商 0: 腾讯云 1:阿里云 ，默认为0
        if (vendor == null) vendor = new Integer(0);

        return taskStatusService.sendTaskToMQ(TaskStatusService.TaskTargetEnum.EMPLOYEE,
                TaskStatusService.TaskTypeEnum.IMPORT,
                SecurityUtils.getCurrentProductInstId(principal),
                fileUrl,
                vendor,
                null);

    }

    @RequestMapping(path = "/employee/export", method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('/employee/export;POST','/employee/export;ALL','/employee/**;POST','/employee/**;ALL','ADMIN')")
    public Map<String, Object> exportRequest(@RequestParam(value = "vendor", required = false) Integer vendor,
                                             Principal principal) {

        //对象存储服务供应商 0: 腾讯云 1:阿里云 ，默认为0
        if (vendor == null) vendor = new Integer(0);

        return taskStatusService.sendTaskToMQ(TaskStatusService.TaskTargetEnum.EMPLOYEE,
                TaskStatusService.TaskTypeEnum.EXPORT,
                SecurityUtils.getCurrentProductInstId(principal),
                null,
                vendor,
                null);
    }

    @RequestMapping(path = "/employee/import", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('/employee/import;GET','/employee/import;ALL','/employee/**;GET','/employee/**;ALL','ADMIN')")
    public Map<Object, Object> getImportTaskResult(@RequestParam(value = "taskId", required = true) String taskId,
                                                   Principal principal) {

        return taskStatusService.getTaskStatus(TaskStatusService.TaskTargetEnum.EMPLOYEE,
                TaskStatusService.TaskTypeEnum.IMPORT,
                SecurityUtils.getCurrentProductInstId(principal),
                taskId);
    }

    @RequestMapping(path = "/employee/export", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('/employee/export;GET','/employee/export;ALL','/employee/**;GET','/employee/**;ALL','ADMIN')")
    public Map<Object, Object> getExportTaskResult(@RequestParam(value = "taskId", required = true) String taskId,
                                                   Principal principal) {

        return taskStatusService.getTaskStatus(TaskStatusService.TaskTargetEnum.EMPLOYEE,
                TaskStatusService.TaskTypeEnum.EXPORT,
                SecurityUtils.getCurrentProductInstId(principal),
                taskId);
    }
}
