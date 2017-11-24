package com.highplace.biz.pm.controller;

import com.highplace.biz.pm.domain.base.Property;
import com.highplace.biz.pm.domain.ui.PropertySearchBean;
import com.highplace.biz.pm.service.PropertyService;
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
public class PropertyController {

    public static final Logger logger = LoggerFactory.getLogger(PropertyController.class);

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private TaskStatusService taskStatusService;

    @RequestMapping(path = "/property", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('/property;GET','/property;ALL','/property/**;GET','/property/**;ALL','ADMIN')")
    public Map<String, Object> getProperty(@Valid PropertySearchBean searchBean,
                                           Principal principal) {

        logger.debug("PropertySearchBean: {}" , searchBean.toString());
        return propertyService.query(SecurityUtils.getCurrentProductInstId(principal), searchBean, false);

    }

    @RequestMapping(path = "/property", method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('/property;POST','/property;ALL','/property/**;POST','/property/**;ALL','ADMIN')")
    public Property createProperty(@Valid @RequestBody Property property,
                                   Principal principal) throws Exception {

        logger.debug("pre property:" + property.toString());
        if (StringUtils.isEmpty(property.getBuildingId())) throw new Exception("buildingId is empty");
        if (StringUtils.isEmpty(property.getRoomId())) throw new Exception("roomId is empty");

        //插入记录
        int rows = propertyService.insert(SecurityUtils.getCurrentProductInstId(principal), property);
        logger.debug("property insert return num: {}" , rows);
        logger.debug("post property: {}" , property.toString());
        if (rows != 1)
            throw new Exception("create failed, effected num:" + rows);
        return property;
    }

    @RequestMapping(path = "/property", method = RequestMethod.PUT)
    @PreAuthorize("hasAnyAuthority('/property;PUT','/property;ALL','/property/**;PUT','/property/**;ALL','ADMIN')")
    public Property changeProperty(@RequestBody Property property,
                                   Principal principal) throws Exception {

        if (property.getPropertyId() == null) throw new Exception("propertyId is null");

        logger.debug("pre property: {}" , property.toString());

        //插入记录
        int rows = propertyService.update(SecurityUtils.getCurrentProductInstId(principal), property);
        logger.debug("property insert return num: {}" , rows);
        logger.debug("post property: {}" , property.toString());
        if (rows != 1) throw new Exception("change failed, effected num:" + rows);
        return property;
    }

    @RequestMapping(path = "/property", method = RequestMethod.DELETE)
    @PreAuthorize("hasAnyAuthority('/property;DELETE','/property;ALL','/property/**;DELETE','/property/**;ALL','ADMIN')")
    public void deleteProperty(@RequestParam(value = "propertyId", required = true) Long propertyId,
                               Principal principal) throws Exception {

        //删除记录
        int rows = propertyService.delete(SecurityUtils.getCurrentProductInstId(principal), propertyId);
        logger.debug("property delete return num: {}" , rows);
        if (rows != 1) {
            if (rows == -1)
                throw new Exception("该房产存在客户关系,请先删除客户关系");
            else
                throw new Exception("delete failed, effected num:" + rows);
        }

    }

    @RequestMapping(path = "/property/catalog", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('/property/catalog;GET','/property/catalog;ALL','/property/**;GET','/property/**;ALL','ADMIN')")
    public Map<String, Object> getPropertyCatalog(Principal principal) {

        return propertyService.rapidSearchAllZoneBuildingUnitId(SecurityUtils.getCurrentProductInstId(principal));

    }

    @RequestMapping(path = "/property/import", method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('/property/import;POST','/property/import;ALL','/property/**;POST','/property/**;ALL','ADMIN')")
    public Map<String, Object> importRequest(@RequestParam(value = "fileUrl", required = true) String fileUrl,
                                             @RequestParam(value = "vendor", required = false) Integer vendor,
                                             Principal principal) {
        //对象存储服务供应商 0: 腾讯云 1:阿里云 ，默认为0
        if (vendor == null) vendor = new Integer(0);

        return taskStatusService.sendTaskToMQ(TaskStatusService.TaskTargetEnum.PROPERTY,
                TaskStatusService.TaskTypeEnum.IMPORT,
                SecurityUtils.getCurrentProductInstId(principal),
                fileUrl,
                vendor,
                null);
    }

    @RequestMapping(path = "/property/export", method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('/property/export;POST','/property/export;ALL','/property/**;POST','/property/**;ALL','ADMIN')")
    public Map<String, Object> exportRequest(@RequestParam(value = "vendor", required = false) Integer vendor,
                                             Principal principal) {

        //对象存储服务供应商 0: 腾讯云 1:阿里云 ，默认为0
        if (vendor == null) vendor = new Integer(0);

        return taskStatusService.sendTaskToMQ(TaskStatusService.TaskTargetEnum.PROPERTY,
                TaskStatusService.TaskTypeEnum.EXPORT,
                SecurityUtils.getCurrentProductInstId(principal),
                null,
                vendor,
                null);
    }

    @RequestMapping(path = "/property/import", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('/property/import;GET','/property/import;ALL','/property/**;GET','/property/**;ALL','ADMIN')")
    public Map<Object, Object> getImportTaskResult(@RequestParam(value = "taskId", required = true) String taskId,
                                                   Principal principal) {

        return taskStatusService.getTaskStatus(TaskStatusService.TaskTargetEnum.PROPERTY,
                TaskStatusService.TaskTypeEnum.IMPORT,
                SecurityUtils.getCurrentProductInstId(principal),
                taskId);
    }

    @RequestMapping(path = "/property/export", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('/property/export;GET','/property/export;ALL','/property/**;GET','/property/**;ALL','ADMIN')")
    public Map<Object, Object> getExportTaskResult(@RequestParam(value = "taskId", required = true) String taskId,
                                                   Principal principal) {

        return taskStatusService.getTaskStatus(TaskStatusService.TaskTargetEnum.PROPERTY,
                TaskStatusService.TaskTypeEnum.EXPORT,
                SecurityUtils.getCurrentProductInstId(principal),
                taskId);
    }
}
