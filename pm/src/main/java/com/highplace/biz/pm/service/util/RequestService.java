package com.highplace.biz.pm.service.util;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.highplace.biz.pm.dao.service.RequestMapper;
import com.highplace.biz.pm.domain.service.Request;
import com.highplace.biz.pm.domain.service.RequestExample;
import com.highplace.biz.pm.domain.ui.RequestSearchBean;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import tk.mybatis.orderbyhelper.OrderByHelper;

import java.util.*;

@Service
public class RequestService {

    public static final Logger logger = LoggerFactory.getLogger(RequestService.class);

    //写入redis的key前缀, 后面加上productInstId
    public static final String PREFIX_REQUEST_TYPE_KEY = "REQUEST_TYPE_KEY_";
    public static final String PREFIX_REQUEST_SUB_TYPE_KEY = "REQUEST_SUB_TYPE_KEY_";

    public static final String DEFAULT_REQUEST_TYPE1 = "报事";
    public static final String DEFAULT_REQUEST_TYPE2 = "报修";
    public static final String DEFAULT_REQUEST_TYPE3 = "投诉";

    public static final String MAP_REQUEST_TYPE_KEY = "type";
    public static final String MAP_REQUEST_SUB_TYPE_KEY = "subType";

    @Autowired
    private RequestMapper requestMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    //查询服务工地列表
    public Map<String, Object> query(String productInstId, RequestSearchBean searchBean, boolean noPageSortFlag) {

        RequestExample example = new RequestExample();
        RequestExample.Criteria criteria = example.createCriteria();

        //产品实例ID，必须填入
        criteria.andProductInstIdEqualTo(productInstId);

        //查询条件判断
        if (StringUtils.isNotEmpty(searchBean.getType()))
            criteria.andTypeEqualTo(searchBean.getType());

        if (StringUtils.isNotEmpty(searchBean.getSubType()))
            criteria.andTypeEqualTo(searchBean.getSubType());

        if ( searchBean.getSource() != null)
            criteria.andSourceEqualTo(searchBean.getSource());

        if ( searchBean.getStatus() != null)
            criteria.andStatusEqualTo(searchBean.getStatus());

        if ( searchBean.getPriority() != null)
            criteria.andPriorityEqualTo(searchBean.getPriority());

        if ((searchBean.getRequestDateFrom() != null) && (searchBean.getRequestDateTo() != null))
            criteria.andStartTimeBetween(searchBean.getRequestDateFrom(), searchBean.getRequestDateTo());

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
        List<Request> requestList = requestMapper.selectByExampleWithBLOBs(example);

        //总记录数
        long totalCount;

        //判断是否有分页
        if (!noPageSortFlag && searchBean.getPageNum() != null && searchBean.getPageSize() != null) {
            totalCount = ((Page) requestList).getTotal();
        } else {
            totalCount = requestList.size();
        }

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("totalCount", totalCount);
        result.put("data", requestList);
        return result;
    }

    //插入服务工单
    public int insert(String productInstId, Request request) {

        //设置产品实例ID
        request.setProductInstId(productInstId);

        int num = requestMapper.insertSelective(request);
        if (num == 1) {
            //更新redis
            addRedisValue(productInstId, request);
        }
        return num;
    }

    //删除服务工单
    public int delete(String productInstId, Long requestId) {

        RequestExample example = new RequestExample();
        RequestExample.Criteria criteria = example.createCriteria();
        criteria.andProductInstIdEqualTo(productInstId);
        criteria.andRequestIdEqualTo(requestId);
        return requestMapper.deleteByExample(example);
    }

    //修改服务工单
    public int update(String productInstId, Request request) {

        RequestExample example = new RequestExample();
        RequestExample.Criteria criteria = example.createCriteria();
        //产品实例ID，必须填入
        criteria.andProductInstIdEqualTo(productInstId);
        criteria.andRequestIdEqualTo(request.getRequestId());
        int num = requestMapper.updateByExampleSelective(request, example);
        if (num == 1) {
            //更新redis
            addRedisValue(productInstId, request);
        }
        return num;
    }

    //type/subType以set数据结构缓存到redis中
    public void addRedisValue(String productInstId, Request request) {
        if (productInstId == null) return;

        if (StringUtils.isNotEmpty(request.getType())) {
            //先设置缺省的
            stringRedisTemplate.opsForSet().add(PREFIX_REQUEST_TYPE_KEY + productInstId, DEFAULT_REQUEST_TYPE1);
            stringRedisTemplate.opsForSet().add(PREFIX_REQUEST_TYPE_KEY + productInstId, DEFAULT_REQUEST_TYPE2);
            stringRedisTemplate.opsForSet().add(PREFIX_REQUEST_TYPE_KEY + productInstId, DEFAULT_REQUEST_TYPE3);

            stringRedisTemplate.opsForSet().add(PREFIX_REQUEST_TYPE_KEY + productInstId, request.getType());

            if (StringUtils.isNotEmpty(request.getSubType())) {
                stringRedisTemplate.opsForSet().add(PREFIX_REQUEST_SUB_TYPE_KEY + productInstId + "_" + request.getType(), request.getSubType());
            }
        }

    }

    //从redis中查询type/subType列表，用于前端快速查询
    public Map<String, Object> rapidSearch(String productInstId) {

        String typeRedisKey = PREFIX_REQUEST_TYPE_KEY + productInstId;
        String subTypeRedisKey;
        String subTypeRedisKeyPrefix = PREFIX_REQUEST_SUB_TYPE_KEY + productInstId;

        Set<String> sType = stringRedisTemplate.opsForSet().members(typeRedisKey);
        Set<String> sSubType;
        List<Object> typeList = new ArrayList<>();
        for (String type : sType) {
            subTypeRedisKey = subTypeRedisKeyPrefix + "_" + type;
            sSubType = stringRedisTemplate.opsForSet().members(subTypeRedisKey);

            Map<String, Object> typeMap = new HashMap<>();
            typeMap.put(MAP_REQUEST_TYPE_KEY, type);
            typeMap.put(MAP_REQUEST_SUB_TYPE_KEY, sSubType);
            typeList.add(typeMap);
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("data", typeList);

        return result;
    }

}
