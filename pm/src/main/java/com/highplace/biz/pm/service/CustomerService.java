package com.highplace.biz.pm.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.highplace.biz.pm.dao.base.CarMapper;
import com.highplace.biz.pm.dao.base.CustomerMapper;
import com.highplace.biz.pm.dao.base.RelationMapper;
import com.highplace.biz.pm.domain.base.*;
import com.highplace.biz.pm.domain.ui.CustomerSearchBean;
import com.highplace.biz.pm.domain.ui.PropertySearchBean;
import com.highplace.biz.pm.service.util.CommonUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.orderbyhelper.OrderByHelper;

import java.util.*;
import java.util.regex.Pattern;

@Service
public class CustomerService {

    public static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    @Autowired
    private PropertyService propertyService;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private RelationMapper relationMapper;
    @Autowired
    private CarMapper carMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    //写入redis的key前缀, 后面加上productInstId
    public static final String PREFIX_CUSTOMER_NAME_KEY = "CUSTOMER_NAME_KEY_";
    public static final String PREFIX_CUSTOMER_PHONE_KEY = "CUSTOMER_PHONE_KEY_";
    public static final String PREFIX_CUSTOMER_PLATENO_KEY = "CUSTOMER_PLATENO_KEY_";

    //返回空的查询结果
    public static Map<String, Object> queryEmpty () {
        List<Object> data = new ArrayList<>();
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("totalCount", 0);
        result.put("data", data);
        return result;
    }

    //name/phone/plateNo以set数据结构缓存到redis中
    public void addRedisValue(Customer customer) {
        if (customer.getProductInstId() == null) return;

        stringRedisTemplate.opsForSet().add(PREFIX_CUSTOMER_NAME_KEY + customer.getProductInstId(), customer.getCustomerName());
        stringRedisTemplate.opsForSet().add(PREFIX_CUSTOMER_PHONE_KEY + customer.getProductInstId(), customer.getPhone());
        List<Relation> relationList = customer.getRelationList();
        if(relationList != null ) {
            for(Relation relation : relationList) {
                List<Car> carList = relation.getCarList();
                if(carList != null) {
                    for(Car car : carList) {
                        stringRedisTemplate.opsForSet().add(PREFIX_CUSTOMER_PLATENO_KEY + customer.getProductInstId(), car.getPlateNo());
                    }
                }
            }
        }
    }

    //从redis中查询客户姓名或客户电话或客户汽车列表，用于前端在检索时快速提示(模糊查询)
    // entity: name/phone/plateNo
    public Map<String, Object> rapidSearch(String productInstId, String entity, String searchValue) {

        String redisKey;
        if(entity.equals("name")) {
            redisKey = PREFIX_CUSTOMER_NAME_KEY + productInstId;
        } else if(entity.equals("phone")) {
            redisKey = PREFIX_CUSTOMER_PHONE_KEY + productInstId;
        } else if(entity.equals("plateNo")) {
            redisKey = PREFIX_CUSTOMER_PLATENO_KEY + productInstId;
        } else {
            return null;
        }

        Set<String> sEntity = stringRedisTemplate.opsForSet().members(redisKey);
        if (sEntity == null) return null;

        List<String> dataList = new ArrayList();
        Pattern pattern = Pattern.compile(searchValue, Pattern.CASE_INSENSITIVE); //大小写不敏感
        int i = 0;
        for (String entityValue: sEntity) {
            i ++;
            if(pattern.matcher(entityValue).find()) dataList.add(entityValue);  //find()模糊匹配  matches()精确匹配
            if(i >= 10) break; //匹配到超过10条记录，退出
        }

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("data", dataList);
        return result;
    }

    //查询房产信息列表
    public Map<String, Object> query(String productInstId, CustomerSearchBean searchBean, boolean noPageSortFlag) {

        CustomerExample example = new CustomerExample();
        CustomerExample.Criteria criteria = example.createCriteria();

        //产品实例ID，必须填入
        criteria.andProductInstIdEqualTo(productInstId);

        List<Long> customerIdListByProperty = new ArrayList<>();
        List<Long> customerIdListByCar = new ArrayList<>();

        boolean hasCustomerIdListByProperty = false;
        boolean hasustomerIdListByCar = false;

        //如果有传入房产相关查询信息,查出对应的客户ID List
        if ( StringUtils.isNotEmpty(searchBean.getZoneId())
                ||  StringUtils.isNotEmpty(searchBean.getBuildingId())
                ||  StringUtils.isNotEmpty(searchBean.getUnitId())
                ||  StringUtils.isNotEmpty(searchBean.getRoomId())
                ||  searchBean.getStatus() != null ) {

            //1 从property表中查询所有的property ID
            List<Property> propertyList;
            try {
                propertyList = (List<Property>) (propertyService.query(productInstId, (PropertySearchBean) searchBean, true).get("data"));
            } catch (ClassCastException e) {
                logger.error("propertyService.query cast data key to List<Property> failed:" + e.getMessage());
                e.printStackTrace();
                return queryEmpty();
            }
            //如果没有查到房产信息，直接返回
            if(propertyList.size() == 0) return queryEmpty();

            List<Long> propertyIdList = new ArrayList<>();
            for (Property property: propertyList) {
                propertyIdList.add(property.getPropertyId());
            }

            //2 从customer property关系表中查询所有的customer ID
            RelationExample relationExample = new RelationExample();
            RelationExample.Criteria criteria1 = relationExample.createCriteria();
            criteria1.andPropertyIdIn(propertyIdList);
            List<Relation> relationList = relationMapper.selectByExample(relationExample);
            //如果没有查到房产和客户的对应关系，直接返回
            if(relationList.size() == 0)  return queryEmpty();

            for (Relation relation: relationList) {
                customerIdListByProperty.add(relation.getCustomerId());
            }
            hasCustomerIdListByProperty = true;
            logger.debug("customerIdListByProperty: " + customerIdListByProperty);
        }

        //如果有传入汽车相关查询信息,查出对应的客户ID List
        if ( StringUtils.isNotEmpty(searchBean.getPlateNo())) {

            CarExample example1 = new CarExample();
            CarExample.Criteria criteria1 = example1.createCriteria();
            criteria1.andPlateNoLike("%" + searchBean.getPlateNo() + "%"); //模糊查询
            List<Car> carList = carMapper.selectByExampleWithRelation(example1);
            //如果没有查到车，直接返回
            if(carList.size() == 0)  return queryEmpty();

            for (Car car: carList) {
                customerIdListByCar.add(car.getRelation().getCustomerId());
            }
            hasustomerIdListByCar = true;
            logger.debug("customerIdListByCar: " + customerIdListByCar);
        }

        //加入客户ID List的and条件查询
        if( hasCustomerIdListByProperty) {
            if (hasustomerIdListByCar) {
                //求两个客户id list的交集
                customerIdListByProperty.retainAll(customerIdListByCar);
                //如果没有交集,返回空
                if (customerIdListByProperty.size() == 0) return queryEmpty();

                criteria.andCustomerIdIn(customerIdListByProperty);
            } else {
                criteria.andCustomerIdIn(customerIdListByProperty);
            }
        } else {
            if (hasustomerIdListByCar) criteria.andCustomerIdIn(customerIdListByCar);
        }

        if( StringUtils.isNotEmpty(searchBean.getCustomerName())) {
            criteria.andCustomerNameLike("%"  + searchBean.getCustomerName() + "%" ); //模糊查询
        }
        if( StringUtils.isNotEmpty(searchBean.getPhone())) {
            criteria.andPhoneLike("%"  + searchBean.getPhone() + "%" ); //模糊查询
        }

        //如果noPageSortFlag 不为true
        if(!noPageSortFlag) {
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
        List<Customer> customerList = customerMapper.selectByExampleWithRelationAndCarWithBLOBs(example);

        //总记录数
        long totalCount;

        //判断是否有分页
        if ( !noPageSortFlag && searchBean.getPageNum() != null && searchBean.getPageSize() != null) {
            totalCount = ((Page) customerList).getTotal();
        } else {
            totalCount = customerList.size();
        }

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("totalCount", totalCount);
        result.put("data", customerList);
        return result;
    }

    //插入客户信息
    //多表插入，需要增加事务
    @Transactional
    public int insert(String productInstId, Customer customer) {

        //设置产品实例ID
        customer.setProductInstId(productInstId);
        int num = customerMapper.insertSelective(customer);
        if(num == 1) {
            //批量插入客户和房产对应关系以及客户房产下的车辆信息
            List<Relation> relationList = customer.getRelationList();
            if(relationList != null) {
                for(Relation relation : relationList) {

                    relation.setProductInstId(productInstId);
                    relation.setCustomerId(customer.getCustomerId());
                    relationMapper.insertSelective(relation);

                    List<Car> carList = relation.getCarList();
                    if(carList != null) {
                        for(Car car : carList) {
                            car.setProductInstId(productInstId);
                            car.setRelationId(relation.getRelationId());
                            carMapper.insertSelective(car);
                        }
                    }
                }
            }

            //更新redis
            addRedisValue(customer);
        }
        return num;
    }
}
