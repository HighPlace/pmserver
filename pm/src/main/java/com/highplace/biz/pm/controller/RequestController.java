package com.highplace.biz.pm.controller;

import com.highplace.biz.pm.domain.service.Request;
import com.highplace.biz.pm.domain.ui.RequestSearchBean;
import com.highplace.biz.pm.service.util.RequestService;
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
public class RequestController {

    public static final Logger logger = LoggerFactory.getLogger(RequestController.class);

    @Autowired
    private RequestService requestService;

    @RequestMapping(path = "/request", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('/request;GET','/request;ALL','/request/**;GET','/request/**;ALL','ADMIN')")
    public Map<String, Object> getRequest(@Valid RequestSearchBean searchBean,
                                         Principal principal) throws Exception {

        logger.debug("RequestSearchBean:" + searchBean.toString());

        //注意：工单的信息量比较大，必须设置分页
        if (searchBean.getPageNum() == null) throw new Exception("pageNum is null");
        if (searchBean.getPageSize() == null) throw new Exception("pageSize is null");

        return requestService.query(SecurityUtils.getCurrentProductInstId(principal), searchBean, false);
    }

    @RequestMapping(path = "/request", method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('/request;POST','/request;ALL','/request/**;POST','/request/**;ALL','ADMIN')")
    public Request creatRequest(@Valid @RequestBody Request request,
                              Principal principal) throws Exception {

        logger.debug("pre service:" + request.toString());

        //插入记录
        int rows = requestService.insert(SecurityUtils.getCurrentProductInstId(principal), request);
        logger.debug("service insert return num:" + rows);
        logger.debug("post request:" + request.toString());
        if (rows != 1) {
            throw new Exception("create failed, effected num:" + rows);
        }
        return request;
    }

    @RequestMapping(path = "/request", method = RequestMethod.DELETE)
    @PreAuthorize("hasAnyAuthority('/request;DELETE','/request;ALL','/request/**;DELETE','/request/**;ALL','ADMIN')")
    public void deleteRequest(@RequestParam(value = "requestId", required = true) Long requestId,
                             Principal principal) throws Exception {

        //删除记录
        int rows = requestService.delete(SecurityUtils.getCurrentProductInstId(principal), requestId);
        logger.debug("requestId delete return num:" + rows);
        if (rows != 1) {
            throw new Exception("delete failed, effected num:" + rows);
        }
    }

    @RequestMapping(path = "/request", method = RequestMethod.PUT)
    @PreAuthorize("hasAnyAuthority('/request;PUT','/request;ALL','/request/**;PUT','/request/**;ALL','ADMIN')")
    public Request changeRequest(@RequestBody Request request,
                               Principal principal) throws Exception {

        if (request.getRequestId() == null) throw new Exception("requestId is null");

        logger.debug("pre request:" + request.toString());

        //插入记录
        int rows = requestService.update(SecurityUtils.getCurrentProductInstId(principal), request);
        logger.debug("service insert return num:" + rows);
        logger.debug("post service:" + request.toString());
        if (rows != 1) {
            throw new Exception("change failed, effected num:" + rows);
        }
        return request;
    }

    @RequestMapping(path = "/request/type", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('/request;GET','/request;ALL','/request/**;GET','/request/**;ALL','ADMIN')")
    public Map<String, Object> getTypeList(Principal principal) {

        return requestService.rapidSearch(SecurityUtils.getCurrentProductInstId(principal));
    }
}
