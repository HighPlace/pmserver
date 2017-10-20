package com.highplace.biz.pm.controller;

import com.highplace.biz.pm.domain.Property;
import com.highplace.biz.pm.domain.ui.PropertySearchBean;
import com.highplace.biz.pm.service.PropertyService;
import com.highplace.biz.pm.service.util.SecurityUtils;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.security.Principal;
import java.util.*;

@RestController
public class PropertyController {

    public static final Logger logger = LoggerFactory.getLogger(PropertyController.class);

    @Autowired
    private PropertyService propertyService;

    @RequestMapping(path = "/property", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('/property;GET','/property;ALL','/property/**;GET','/property/**;ALL','ADMIN')")
    public Map<String, Object> getProperty(@Valid PropertySearchBean searchBean,
                                        Principal principal) {

        logger.debug("searchBean:" + searchBean.toString());
        logger.debug("productInstId:" + SecurityUtils.getCurrentProductInstId(principal));
        return propertyService.query(SecurityUtils.getCurrentProductInstId(principal), searchBean);

    }

    @RequestMapping(path = "/property", method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('/property;POST','/property;ALL','/property/**;POST','/property/**;ALL','ADMIN')")
    public Property createProperty(@Valid @RequestBody Property property,
                                    Principal principal) {

        logger.debug("pre property:" + property.toString());

        //插入记录
        int rows = propertyService.insert(SecurityUtils.getCurrentProductInstId(principal), property);

        logger.debug("property insert return num:" + rows);
        logger.debug("post property:" + property.toString());

        return property;
    }

    @RequestMapping(path = "/property", method = RequestMethod.PUT)
    @PreAuthorize("hasAnyAuthority('/property;PUT','/property;ALL','/property/**;PUT','/property/**;ALL','ADMIN')")
    public Property changeProperty(@RequestBody Property property,
                                   Principal principal) throws Exception {

        if(property.getPropertyId() == null ) throw new Exception("propertyId is null");

        logger.debug("pre property:" + property.toString());

        //插入记录
        int rows = propertyService.update(SecurityUtils.getCurrentProductInstId(principal), property);
        logger.debug("property insert return num:" + rows);
        logger.debug("post property:" + property.toString());
        if(rows != 1) throw new Exception("change failed, effected num:" + rows);
        return property;
    }

    @RequestMapping(path = "/property", method = RequestMethod.DELETE)
    @PreAuthorize("hasAnyAuthority('/property;DELETE','/property;ALL','/property/**;DELETE','/property/**;ALL','ADMIN')")
    public void deleteProperty(@RequestParam(value = "propertyId", required = true) Long propertyId,
                                   Principal principal) throws Exception {

        //删除记录
        int rows = propertyService.delete(SecurityUtils.getCurrentProductInstId(principal), propertyId);
        logger.debug("property delete return num:" + rows);
        if(rows != 1) throw new Exception("delete failed, effected num:" + rows);
    }

    @RequestMapping(path = "/property/catalog", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('/property/catalog;GET','/property/catalog;ALL','/property/**;GET','/property/**;ALL','ADMIN')")
    public Map<String, Object> getPropertyCatalog(Principal principal) {

        return propertyService.getAllZoneBuildingUnitId(SecurityUtils.getCurrentProductInstId(principal));

    }
}
