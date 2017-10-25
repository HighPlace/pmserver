package com.highplace.biz.pm.service;

import com.github.pagehelper.PageHelper;
import com.highplace.biz.pm.dao.base.CarMapper;
import com.highplace.biz.pm.dao.base.CustomerMapper;
import com.highplace.biz.pm.dao.base.PropertyMapper;
import com.highplace.biz.pm.domain.base.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.highplace.biz.pm.service.CustomerService.*;
import static com.highplace.biz.pm.service.PropertyService.*;

@Component
public class InternalService {

    public static final Logger logger = LoggerFactory.getLogger(InternalService.class);

    //reload内存时每次从数据库读取的的记录条数
    public static final int CACHE_RELOAD_BATCH_SIZE = 100; //防止内存溢出,一次操作100条记录

    @Autowired
    private CarMapper carMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private PropertyMapper propertyMapper;
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Scheduled(cron = "0 38 17 * * ?")   //每天1点15分执行一次，全量更新cache内容
    public void reloadCustomerRedisValue() {

        ///// reload 车牌号 cache ////////
        long totalCount = carMapper.countByExample(new CarExample());
        long pages = (totalCount % CACHE_RELOAD_BATCH_SIZE == 0)? totalCount / 100 : totalCount / 100 + 1 ;

        //维护一个已经清除了cache的productInstId列表
        Set<String> emptyProductInstIdSet = new HashSet<>();

        List<Car> carList;
        for(int i=1 ; i <= pages; i++) {
            PageHelper.startPage(i, CACHE_RELOAD_BATCH_SIZE);
            carList = carMapper.selectAllProductInstIdAndPlateNo();
            for(Car car : carList) {

                //如果已经清除了cache的productInstId列表不包含当前id，则清除cache，并加入到列表中
                if(!emptyProductInstIdSet.contains(car.getProductInstId())) {
                    stringRedisTemplate.opsForSet().remove(PREFIX_CUSTOMER_PLATENO_KEY + car.getProductInstId());
                    emptyProductInstIdSet.add(car.getProductInstId());
                }
                stringRedisTemplate.opsForSet().add(PREFIX_CUSTOMER_PLATENO_KEY + car.getProductInstId(), car.getPlateNo());
            }
        }
        logger.info("reload customer plateNo cache success");

        ///// reload 客户姓名和电话 ///////
        totalCount = customerMapper.countByExample(new CustomerExample());
        pages = (totalCount % CACHE_RELOAD_BATCH_SIZE == 0)? totalCount / 100 : totalCount / 100 + 1 ;

        //维护一个已经清除了cache的productInstId列表
        emptyProductInstIdSet = new HashSet<>();

        List<Customer> customerList;
        for(int i=1 ; i <= pages; i++) {
            PageHelper.startPage(i, CACHE_RELOAD_BATCH_SIZE);
            customerList = customerMapper.selectAllProductInstIdAndNameAndPhone();
            for(Customer customer : customerList) {

                //如果已经清除了cache的productInstId列表不包含当前id，则清除cache，并加入到列表中
                if(!emptyProductInstIdSet.contains(customer.getProductInstId())) {
                    stringRedisTemplate.opsForSet().remove(PREFIX_CUSTOMER_NAME_KEY + customer.getProductInstId());
                    stringRedisTemplate.opsForSet().remove(PREFIX_CUSTOMER_PHONE_KEY + customer.getProductInstId());
                    emptyProductInstIdSet.add(customer.getProductInstId());
                }
                stringRedisTemplate.opsForSet().add(PREFIX_CUSTOMER_NAME_KEY + customer.getProductInstId(), customer.getCustomerName());
                stringRedisTemplate.opsForSet().add(PREFIX_CUSTOMER_PHONE_KEY + customer.getProductInstId(), customer.getPhone());
            }
        }
        logger.info("reload customer name and phone cache success");
    }

    @Scheduled(cron = "0 40 17 * * ?")   //每天1点15分执行一次，全量更新cache内容
    public void reloadPropertyRedisValue() {
        ///// reload 分区/楼号/单元号 cache ////////
        long totalCount = propertyMapper.countByExample(new PropertyExample());
        long pages = (totalCount % CACHE_RELOAD_BATCH_SIZE == 0)? totalCount / 100 : totalCount / 100 + 1 ;

        //维护一个已经清除了cache的key列表
        Set<String> emptyZoneKeySet = new HashSet<>();
        Set<String> emptyZoneBuildingKeySet = new HashSet<>();
        Set<String> emptyZoneBuildingUnitIdKeySet = new HashSet<>();

        List<Property> propertyList;
        for(int i=1 ; i <= pages; i++) {
            PageHelper.startPage(i, CACHE_RELOAD_BATCH_SIZE);
            propertyList = propertyMapper.selectDistinctProductInstIdAndIDs();
            for(Property property : propertyList) {

                String redisKeyForZoneId = PREFIX_PROPERTY_ZONEID_KEY + property.getProductInstId();
                String redisKeyForBuildIdPrefix = PREFIX_PROPERTY_BUILDINGID_KEY + property.getProductInstId() + property.getZoneId();
                String redisKeyForUnitIdPrefix = PREFIX_PROPERTY_UNITID_KEY + property.getProductInstId() + property.getBuildingId();

                //zoneId
                if(!emptyZoneKeySet.contains(redisKeyForZoneId)) {
                    stringRedisTemplate.opsForSet().remove(redisKeyForZoneId);
                    emptyZoneKeySet.add(redisKeyForZoneId);
                }
                stringRedisTemplate.opsForSet().add(redisKeyForZoneId, property.getZoneId());

                //buildingId
                if(!emptyZoneBuildingKeySet.contains(redisKeyForBuildIdPrefix)) {
                    stringRedisTemplate.opsForSet().remove(redisKeyForBuildIdPrefix);
                    emptyZoneKeySet.add(redisKeyForBuildIdPrefix);
                }
                stringRedisTemplate.opsForSet().add(redisKeyForBuildIdPrefix, property.getBuildingId());

                //unitId
                if(!emptyZoneBuildingUnitIdKeySet.contains(redisKeyForUnitIdPrefix)) {
                    stringRedisTemplate.opsForSet().remove(redisKeyForUnitIdPrefix);
                    emptyZoneKeySet.add(redisKeyForUnitIdPrefix);
                }
                stringRedisTemplate.opsForSet().add(redisKeyForUnitIdPrefix, property.getUnitId());
            }
        }
        logger.info("reload property zoneId buildingId unitId cache success");
    }
}
