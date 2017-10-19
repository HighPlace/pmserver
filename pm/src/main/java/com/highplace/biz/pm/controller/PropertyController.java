package com.highplace.biz.pm.controller;

import com.highplace.biz.pm.domain.Property;
import com.highplace.biz.pm.domain.ui.PropertySearchBean;
import com.highplace.biz.pm.service.PropertyService;
import com.highplace.biz.pm.service.util.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
public class PropertyController {

    public static final Logger logger = LoggerFactory.getLogger(PropertyController.class);

    @Autowired
    private PropertyService propertyService;

    @RequestMapping(path = "/property", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('/property;GET','/property;ALL','/property/**;GET','/property/**;ALL','ADMIN')")
    public List<Property> getProperty(@Valid PropertySearchBean searchBean,
                                        Principal principal) {

        return propertyService.searchProperty(SecurityUtils.getCurrentProductInstId(principal), searchBean);

    }

    @RequestMapping(path = "/property/zone", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('/property/zone;GET','/property/zone;ALL','/property/**;GET','/property/**;ALL','ADMIN')")
    public Collection<String> getPropertyZone(Principal principal) {

        return propertyService.getAllZoneId(SecurityUtils.getCurrentProductInstId(principal));

    }




    @RequestMapping(path = "/current", method = RequestMethod.GET)
    public Map<String, String> getCurrentAccount(Principal principal) {

        String pid = SecurityUtils.getCurrentProductInstId(principal);

        Map<String, String> result = new LinkedHashMap<>();
        result.put("username", principal.getName());
        result.put("productInstId", pid);

        return result;
    }


}
