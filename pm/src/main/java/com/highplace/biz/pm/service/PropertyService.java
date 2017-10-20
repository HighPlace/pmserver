package com.highplace.biz.pm.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.highplace.biz.pm.dao.PropertyMapper;
import com.highplace.biz.pm.domain.Property;
import com.highplace.biz.pm.domain.PropertyExample;
import com.highplace.biz.pm.domain.ui.PropertySearchBean;
import com.highplace.biz.pm.service.util.CommonUtils;
import com.highplace.biz.pm.service.util.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import tk.mybatis.orderbyhelper.OrderByHelper;

import java.util.*;

@Service
public class PropertyService {

    public static final Logger logger = LoggerFactory.getLogger(PropertyService.class);

    //写入redis的key前缀, 后面加上productInstId
    public static final String PREFIX_PROPERTY_ZONEID_KEY = "PROPERTY_ZONEID_KEY_";
    public static final String PREFIX_PROPERTY_BUILDINGID_KEY = "PROPERTY_BUILDINGID_KEY_";
    public static final String PREFIX_PROPERTY_UNITID_KEY = "PROPERTY_UNITID_KEY_";
    public static final String PREFIX_PROPERTY_ROOMID_KEY = "PROPERTY_ZONEID_KEY_";

    @Autowired
    private PropertyMapper propertyMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    //查询房产分区信息
    public Collection<String> getAllZoneId(String productInstId) {
        /*
        ZSetOperations.TypedTuple<String> objectTypedTuple1 = new DefaultTypedTuple<String>("玫瑰苑",0.0);
        ZSetOperations.TypedTuple<String> objectTypedTuple2 = new DefaultTypedTuple<String>("菊花苑",1.0);
        Set<ZSetOperations.TypedTuple<String>> tuples = new HashSet<ZSetOperations.TypedTuple<String>>();
        tuples.add(objectTypedTuple1);
        tuples.add(objectTypedTuple2);
        stringRedisTemplate.opsForZSet().add(PREFIX_PROPERTY_ZONEID_KEY + productInstId, tuples);
        */
        String redisKeyForZondId = PREFIX_PROPERTY_ZONEID_KEY + productInstId;

        String[] strarrays = new String[]{"一区","二区","三区","四区","玫瑰苑"};

        stringRedisTemplate.opsForSet().add(redisKeyForZondId, strarrays);
        Set<String> s = stringRedisTemplate.opsForSet().members(redisKeyForZondId);
        //long size = stringRedisTemplate.opsForSet().size(redisKeyForZondId);
        //List<String> l = stringRedisTemplate.opsForSet().randomMembers(redisKeyForZondId, size);
        List<String> l = new ArrayList<>(s);
        Collections.sort(l);
        return l;

        //template.opsForSet().isMember("setTest","ccc")
        /*
        template.opsForList().rightPush("listRight","java");

        String[] stringarrays = new String[]{"1","2","3"};
        template.opsForList().rightPushAll("listarrayright",stringarrays);
        System.out.println(template.opsForList().range("listarrayright",0,-1));
        结果:[1, 2, 3]

        List<Object> strings = new ArrayList<Object>();
        strings.add("1");
        strings.add("2");
        strings.add("3");
        template.opsForList().rightPushAll("listcollectionright", strings);
        System.out.println(template.opsForList().range("listcollectionright",0,-1));

        template.opsForList().remove("listRight",1,"setValue");//将删除列表中存储的列表中第一次次出现的“setValue”。
        */

    }

    //查询房产信息列表
    public Map<String, Object> query(String productInstId, PropertySearchBean searchBean) {

        PropertyExample example = new PropertyExample();
        PropertyExample.Criteria criteria = example.createCriteria();

        //产品实例ID，必须填入
        criteria.andProductInstIdEqualTo(productInstId);

        //查询条件判断
        if(searchBean.getZoneId() != null && !searchBean.getZoneId().equals("") )
            criteria.andZoneIdEqualTo(searchBean.getZoneId());

        if(searchBean.getBuildingId() != null && !searchBean.getBuildingId().equals("") )
            criteria.andBuildingIdEqualTo(searchBean.getBuildingId());

        if(searchBean.getUnitId() != null && !searchBean.getUnitId().equals("") )
            criteria.andUnitIdEqualTo(searchBean.getUnitId());

        if(searchBean.getRoomId() != null && !searchBean.getRoomId().equals("") )
            criteria.andRoomIdEqualTo(searchBean.getRoomId());

        if(searchBean.getStatus() != null )
            criteria.andStatusEqualTo(searchBean.getStatus());

        //设置分页参数
        if(searchBean.getPageNum() != null && searchBean.getPageSize() != null )
            PageHelper.startPage(searchBean.getPageNum(), searchBean.getPageSize());

        //设置排序字段,注意前端传入的是驼峰风格字段名,需要转换成数据库下划线风格字段名
        if(searchBean.getSortField() != null ) {
            if(searchBean.getSortType() == null ) {
                OrderByHelper.orderBy(CommonUtils.underscoreString(searchBean.getSortField()) + " asc"); //默认升序
            } else {
                OrderByHelper.orderBy(CommonUtils.underscoreString(searchBean.getSortField()) + " " + searchBean.getSortType());
            }
        }

        //查询结果
        List<Property> properties = propertyMapper.selectByExample(example);

        //总记录数
        long totalCount;

        //判断是否有分页
        if(searchBean.getPageNum() != null && searchBean.getPageSize() != null ) {
            totalCount = ((Page) properties).getTotal();
        } else {
            totalCount = properties.size();
        }

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("totalCount", totalCount);
        result.put("data", properties);
        return result;
    }

    //插入房产信息
    public int insert(String productInstId, Property property) {

        //设置产品实例ID
        property.setProductInstId(productInstId);
        return propertyMapper.insertSelective(property);
    }

    //修改房产信息
    public int update(String productInstId, Property property) {

        PropertyExample example = new PropertyExample();
        PropertyExample.Criteria criteria = example.createCriteria();
        criteria.andPropertyIdEqualTo(property.getPropertyId());

        //产品实例ID，必须填入
        criteria.andProductInstIdEqualTo(productInstId);
        return propertyMapper.updateByExampleSelective(property, example);
    }

    //删除房产信息
    public int delete(String productInstId, Long propertyId) {

        //删除之前需要加入业务逻辑判断,不能随便删除
        //to-do

        PropertyExample example = new PropertyExample();
        PropertyExample.Criteria criteria = example.createCriteria();
        criteria.andPropertyIdEqualTo(propertyId);
        criteria.andProductInstIdEqualTo(productInstId);
        return propertyMapper.deleteByExample(example);
    }

}
