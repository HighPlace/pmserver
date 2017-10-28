package com.highplace.biz.pm.controller;

import com.highplace.biz.pm.domain.service.Notice;
import com.highplace.biz.pm.domain.ui.NoticeSearchBean;
import com.highplace.biz.pm.service.NoticeService;
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
public class NoticeController {

    public static final Logger logger = LoggerFactory.getLogger(NoticeController.class);

    @Autowired
    private NoticeService noticeService;

    @RequestMapping(path = "/notice", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('/notice;GET','/notice;ALL','/notice/**;GET','/notice/**;ALL','ADMIN')")
    public Map<String, Object> getNotice(@Valid NoticeSearchBean searchBean,
                                         Principal principal) throws Exception {

        logger.debug("NoticeSearchBean:" + searchBean.toString());

        //注意：公告的信息量比较大，必须设置分页
        if (searchBean.getPageNum() == null) throw new Exception("pageNum is null");
        if (searchBean.getPageSize() == null) throw new Exception("pageSize is null");

        return noticeService.query(SecurityUtils.getCurrentProductInstId(principal), searchBean, false);
    }

    @RequestMapping(path = "/notice", method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('/notice;POST','/notice;ALL','/notice/**;POST','/notice/**;ALL','ADMIN')")
    public Notice creatNotice(@Valid @RequestBody Notice notice,
                              Principal principal) throws Exception {

        logger.debug("pre notice:" + notice.toString());

        //插入记录
        int rows = noticeService.insert(SecurityUtils.getCurrentProductInstId(principal), notice);
        logger.debug("notice insert return num:" + rows);
        logger.debug("post notice:" + notice.toString());
        if (rows != 1) {
            throw new Exception("create failed, effected num:" + rows);
        }
        return notice;
    }

    @RequestMapping(path = "/notice", method = RequestMethod.DELETE)
    @PreAuthorize("hasAnyAuthority('/notice;DELETE','/notice;ALL','/notice/**;DELETE','/notice/**;ALL','ADMIN')")
    public void deleteNotice(@RequestParam(value = "noticeId", required = true) Long noticeId,
                             Principal principal) throws Exception {

        //删除记录
        int rows = noticeService.delete(SecurityUtils.getCurrentProductInstId(principal), noticeId);
        logger.debug("notice delete return num:" + rows);
        if (rows != 1) {
            throw new Exception("delete failed, effected num:" + rows);
        }
    }

    @RequestMapping(path = "/notice", method = RequestMethod.PUT)
    @PreAuthorize("hasAnyAuthority('/notice;PUT','/notice;ALL','/notice/**;PUT','/notice/**;ALL','ADMIN')")
    public Notice changeNotice(@RequestBody Notice notice,
                               Principal principal) throws Exception {

        if (notice.getNoticeId() == null) throw new Exception("noticeId is null");

        logger.debug("pre notice:" + notice.toString());

        //插入记录
        int rows = noticeService.update(SecurityUtils.getCurrentProductInstId(principal), notice);
        logger.debug("notice insert return num:" + rows);
        logger.debug("post notice:" + notice.toString());
        if (rows == 1) {
            throw new Exception("change failed, effected num:" + rows);
        }
        return notice;
    }

    @RequestMapping(path = "/notice/{entity}", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('/notice;GET','/notice;ALL','/notice/**;GET','/notice/**;ALL','ADMIN')")
    public Map<String, Object> getEntityList(@PathVariable String entity,
                                             @RequestParam(value = "input", required = true) String input,
                                             Principal principal) {
        //entity 支持title/type 两种
        return noticeService.rapidSearch(SecurityUtils.getCurrentProductInstId(principal), entity, input);
    }
}
