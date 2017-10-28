package com.highplace.biz.pm.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.highplace.biz.pm.dao.notice.NoticeMapper;
import com.highplace.biz.pm.domain.service.Notice;
import com.highplace.biz.pm.domain.service.NoticeExample;
import com.highplace.biz.pm.domain.ui.NoticeSearchBean;
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
public class NoticeService {

    public static final Logger logger = LoggerFactory.getLogger(NoticeService.class);

    //写入redis的key前缀, 后面加上productInstId
    public static final String PREFIX_NOTICE_TYPE_KEY = "NOTICE_TYPE_KEY_";
    public static final String PREFIX_NOTICE_TITLE_KEY = "NOTICE_TITLE_KEY_";

    @Autowired
    private NoticeMapper noticeMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    //查询信息公告列表
    public Map<String, Object> query(String productInstId, NoticeSearchBean searchBean, boolean noPageSortFlag) {

        NoticeExample example = new NoticeExample();
        NoticeExample.Criteria criteria = example.createCriteria();

        //产品实例ID，必须填入
        criteria.andProductInstIdEqualTo(productInstId);

        //查询条件判断
        if (StringUtils.isNotEmpty(searchBean.getType()))
            criteria.andTypeEqualTo(searchBean.getType());

        if (StringUtils.isNotEmpty(searchBean.getTitle()))
            criteria.andTitleLike("%" + searchBean.getTitle() + "%");

        if ((searchBean.getPublishDateFrom() != null) && (searchBean.getPublishDateTo() != null))
            criteria.andPublishDateBetween(searchBean.getPublishDateFrom(), searchBean.getPublishDateTo());

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
        List<Notice> noticeList = noticeMapper.selectByExampleWithBLOBs(example);

        //总记录数
        long totalCount;

        //判断是否有分页
        if (!noPageSortFlag && searchBean.getPageNum() != null && searchBean.getPageSize() != null) {
            totalCount = ((Page) noticeList).getTotal();
        } else {
            totalCount = noticeList.size();
        }

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("totalCount", totalCount);
        result.put("data", noticeList);
        return result;
    }

    //插入信息公告
    public int insert(String productInstId, Notice notice) {

        //设置产品实例ID
        notice.setProductInstId(productInstId);

        int num = noticeMapper.insertSelective(notice);
        if (num == 1) {
            //更新redis
            addRedisValue(productInstId, notice);
        }
        return num;
    }

    //删除信息公告
    public int delete(String productInstId, Long noticeId) {

        NoticeExample example = new NoticeExample();
        NoticeExample.Criteria criteria = example.createCriteria();
        criteria.andProductInstIdEqualTo(productInstId);
        criteria.andNoticeIdEqualTo(noticeId);
        return noticeMapper.deleteByExample(example);
    }

    //修改信息公告
    public int update(String productInstId, Notice notice) {

        NoticeExample example = new NoticeExample();
        NoticeExample.Criteria criteria = example.createCriteria();
        //产品实例ID，必须填入
        criteria.andProductInstIdEqualTo(productInstId);
        criteria.andNoticeIdEqualTo(notice.getNoticeId());
        int num = noticeMapper.updateByExampleSelective(notice, example);
        if (num == 1) {
            //更新redis
            addRedisValue(productInstId, notice);
        }
        return num;
    }

    //title/type以set数据结构缓存到redis中
    public void addRedisValue(String productInstId, Notice notice) {
        if (productInstId == null) return;

        if (StringUtils.isNotEmpty(notice.getTitle()))
            stringRedisTemplate.opsForSet().add(PREFIX_NOTICE_TITLE_KEY + productInstId, notice.getTitle());

        if (StringUtils.isNotEmpty(notice.getType()))
            stringRedisTemplate.opsForSet().add(PREFIX_NOTICE_TYPE_KEY + productInstId, notice.getType());
    }

    //从redis中查询title/type列表，用于前端在检索时快速提示(title为模糊匹配10条,type为全量信息)
    public Map<String, Object> rapidSearch(String productInstId, String entity, String searchValue) {

        String redisKey;
        //对于position,如果传入了deptId，则查对应deptId下的position
        if (entity.equals("type")) {
            redisKey = PREFIX_NOTICE_TYPE_KEY + productInstId;
        } else if (entity.equals("title")) {
            redisKey = PREFIX_NOTICE_TITLE_KEY + productInstId;
        } else {
            return null;
        }

        Map<String, Object> result = new HashMap<String, Object>();
        Set<String> sEntity = stringRedisTemplate.opsForSet().members(redisKey);
        if (sEntity == null) {
            result.put("data", null);
        } else {
            //对于type,返回全量type列表
            if (entity.equals("type")) {
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

}
