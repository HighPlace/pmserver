package com.highplace.biz.pm.controller;

import com.highplace.biz.pm.domain.org.Department;
import com.highplace.biz.pm.service.DepartmentService;
import com.highplace.biz.pm.service.util.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Map;

@RestController
public class DepartmentController {

    public static final Logger logger = LoggerFactory.getLogger(DepartmentController.class);

    @Autowired
    private DepartmentService departmentService;

    @RequestMapping(path = "/department/catalog", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('/department/catalog;GET','/department/catalog;ALL','/department/**;GET','/department/**;ALL','ADMIN')")
    public Map<String, Object> getDepartmentCatalog(Principal principal) {

        return departmentService.rapidSearchDepartmentTree(SecurityUtils.getCurrentProductInstId(principal));
    }

    @RequestMapping(path = "/department", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('/department;GET','/department;ALL','/department/**;GET','/department/**;ALL','ADMIN')")
    public Map<String, Object> getDepartment(@RequestParam(value = "superiorDeptId", required = false) Long superiorDeptId,
                                             Principal principal) {

        return departmentService.query(SecurityUtils.getCurrentProductInstId(principal), superiorDeptId);
    }

    @RequestMapping(path = "/department", method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('/department;POST','/department;ALL','/department/**;POST','/department/**;ALL','ADMIN')")
    public Department createDepartment(@Valid @RequestBody Department department,
                                       Principal principal) throws Exception {

        logger.debug("pre Department:" + department.toString());

        //插入记录
        int rows = departmentService.insert(SecurityUtils.getCurrentProductInstId(principal), department);
        logger.debug("department insert return num:" + rows);
        logger.debug("post department:" + department.toString());
        if (rows == -1)
            throw new Exception("上级部门不存在,请检查");
        return department;
    }

    @RequestMapping(path = "/department", method = RequestMethod.PUT)
    @PreAuthorize("hasAnyAuthority('/department;PUT','/department;ALL','/department/**;PUT','/department/**;ALL','ADMIN')")
    public Department changeDepartment(@RequestBody Department department,
                                       Principal principal) throws Exception {

        if (department.getDeptId() == null) throw new Exception("deptId is null");

        logger.debug("pre department:" + department.toString());

        //插入记录
        int rows = departmentService.update(SecurityUtils.getCurrentProductInstId(principal), department);
        logger.debug("department insert return num:" + rows);
        logger.debug("post department:" + department.toString());
        if (rows == -1)
            throw new Exception("上级部门不存在,请检查");
        else if (rows != 1)
            throw new Exception("change failed, effected num:" + rows);

        return department;
    }

    @RequestMapping(path = "/department", method = RequestMethod.DELETE)
    @PreAuthorize("hasAnyAuthority('/department;DELETE','/department;ALL','/department/**;DELETE','/department/**;ALL','ADMIN')")
    public void deleteDepartment(@RequestParam(value = "deptId", required = true) Long deptId,
                                 Principal principal) throws Exception {

        //删除记录
        int rows = departmentService.delete(SecurityUtils.getCurrentProductInstId(principal), deptId);
        logger.debug("department delete return num:" + rows);
        if (rows != 1) {
            if (rows == -1)
                throw new Exception("不能删除该部门,请检查该部门是否存在下级部门,或该部门下是否存在员工");
            else
                throw new Exception("delete failed, effected num:" + rows);
        }

    }

}
