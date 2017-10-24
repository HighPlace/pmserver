package com.highplace.biz.pm.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.highplace.biz.pm.dao.CustomerCarMapper;
import com.highplace.biz.pm.dao.CustomerMapperOld;
import com.highplace.biz.pm.dao.CustomerPropertyRelMapper;
import com.highplace.biz.pm.domain.*;
import com.highplace.biz.pm.domain.base.Property;
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
public class CustomerServiceOld {

    public static final Logger logger = LoggerFactory.getLogger(CustomerServiceOld.class);

    @Autowired
    private PropertyService propertyService;
    @Autowired
    private CustomerMapperOld customerMapperOld;
    @Autowired
    private CustomerPropertyRelMapper customerPropertyRelMapper;
    @Autowired
    private CustomerCarMapper customerCarMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    //写入redis的key前缀, 后面加上productInstId
    public static final String PREFIX_CUSTOMER_NAME_KEY = "CUSTOMER_NAME_KEY_";
    public static final String PREFIX_CUSTOMER_PHONE_KEY = "CUSTOMER_PHONE_KEY_";
    public static final String PREFIX_CUSTOMER_PLATENO_KEY = "CUSTOMER_PLATENO_KEY_";

    //返回空的查询结果
    public static Map<String, Object> queryEmpty () {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("totalCount", 0);
        result.put("data", "");
        return result;
    }

    //name/phone/plateNo以set数据结构缓存到redis中
    public void addRedisValue(Customer customer) {
        if (customer.getProductInstId() == null) return;

        stringRedisTemplate.opsForSet().add(PREFIX_CUSTOMER_NAME_KEY + customer.getProductInstId(), customer.getCustomerName());
        stringRedisTemplate.opsForSet().add(PREFIX_CUSTOMER_PHONE_KEY + customer.getProductInstId(), customer.getPhone());
        List<CustomerCar> carList = customer.getCustomerCars();
        if(carList != null && carList.size()>0 ) {
            for(CustomerCar customerCar : carList) {
                stringRedisTemplate.opsForSet().add(PREFIX_CUSTOMER_PLATENO_KEY + customer.getProductInstId(), customerCar.getPlateNo());
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
            CustomerPropertyRelExample customerPropertyRelExample = new CustomerPropertyRelExample();
            CustomerPropertyRelExample.Criteria criteria1 = customerPropertyRelExample.createCriteria();
            criteria1.andPropertyIdIn(propertyIdList);
            List<CustomerPropertyRel> customerPropertyRelList = customerPropertyRelMapper.selectByExample(customerPropertyRelExample);
            //如果没有查到房产和客户的对应关系，直接返回
            if(customerPropertyRelList.size() == 0)  return queryEmpty();

            for (CustomerPropertyRel customerPropertyRel: customerPropertyRelList) {
                customerIdListByProperty.add(customerPropertyRel.getCustomerId());
            }
            hasCustomerIdListByProperty = true;
            logger.debug("customerIdListByProperty: " + customerIdListByProperty);
        }

        //如果有传入汽车相关查询信息,查出对应的客户ID List
        if ( StringUtils.isNotEmpty(searchBean.getPlateNo())) {

            CustomerCarExample example1 = new CustomerCarExample();
            CustomerCarExample.Criteria criteria1 = example1.createCriteria();
            criteria1.andPlateNoLike("%" + searchBean.getPlateNo() + "%"); //模糊查询
            List<CustomerCar> customerCarList = customerCarMapper.selectByExample(example1);
            //如果没有查到客户和车的对应关系，直接返回
            if(customerCarList.size() == 0)  return queryEmpty();

            for (CustomerCar customerCar: customerCarList) {
                customerIdListByCar.add(customerCar.getCustomerId());
            }
            hasustomerIdListByCar = true;
            logger.debug("customerIdListByCar: " + customerIdListByProperty);
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
        List<Customer> customerList = customerMapperOld.selectByExampleWithPropertyAndCar(example);

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
        int num = customerMapperOld.insertSelective(customer);
        if(num == 1) {
            //批量插入客户和房产对应关系信息
            List<CustomerPropertyRel> customerPropertyRelList = customer.getCustomerPropertyRels();
            if(customerPropertyRelList != null && customerPropertyRelList.size()>0 ) {
                for(CustomerPropertyRel customerPropertyRel : customerPropertyRelList) {

                    customerPropertyRel.setProductInstId(productInstId);
                    customerPropertyRel.setCustomerId(customer.getCustomerId());
                    customerPropertyRelMapper.insertSelective(customerPropertyRel);
                }
            }
            //批量插入客户汽车信息
            List<CustomerCar> carList = customer.getCustomerCars();
            if(carList != null && carList.size()>0 ) {
                for(CustomerCar customerCar : carList) {
                    customerCar.setProductInstId(productInstId);
                    customerCar.setCustomerId(customer.getCustomerId());
                    customerCarMapper.insertSelective(customerCar);
                }
            }

            //更新redis
            addRedisValue(customer);
        }
        return num;
    }

    //修改客户信息
    @Transactional
    public int update(String productInstId, Customer customer) {

        CustomerExample example = new CustomerExample();
        CustomerExample.Criteria criteria = example.createCriteria();
        criteria.andCustomerIdEqualTo(customer.getCustomerId()); //客户ID
        criteria.andProductInstIdEqualTo(productInstId); //产品实例ID，必须填入

        int num = customerMapperOld.updateByExampleSelective(customer, example);
        if(num == 1) {

            //批量更新客户和房产对应关系信息
            List<CustomerPropertyRel> customerPropertyRelList = customer.getCustomerPropertyRels();
            //if(customerPropertyRelList != null && customerPropertyRelList.size()>0 ) {
            if(customerPropertyRelList != null ) {

                //传入了customerPropertyRelList,但内容为空,则清掉客户和房产对应关系
                if (customerPropertyRelList.size() == 0) {
                    CustomerPropertyRelExample example1 = new CustomerPropertyRelExample();
                    CustomerPropertyRelExample.Criteria criteria1 = example1.createCriteria();
                    criteria1.andProductInstIdEqualTo(productInstId);
                    criteria1.andCustomerIdEqualTo(customer.getCustomerId());
                    customerPropertyRelMapper.deleteByExample(example1);

                } else { //传入了内容，先更新，再删除

                    //提交的请求数据
                    List<Long> prePropertyList = new ArrayList<>();

                    for(CustomerPropertyRel customerPropertyRel : customerPropertyRelList) {
                        prePropertyList.add(customerPropertyRel.getPropertyId());
                        customerPropertyRel.setProductInstId(productInstId);
                        customerPropertyRel.setCustomerId(customer.getCustomerId());
                        customerPropertyRel.setModifyTime(new Date()); //避免update table set为空,导致update失败
                        //有可能是新增加的对应关系,所以先更新,更新不了再插入
                        if (customerPropertyRelMapper.updateByPrimaryKeySelective(customerPropertyRel) == 0) {
                            customerPropertyRelMapper.insertSelective(customerPropertyRel);
                        }
                    }

                    //更新后数据库中的数据
                    List<Long> postPropertyList = getPropertyIdListByCustomerId(productInstId, customer.getCustomerId());

                    //求补集，删除postPropertyList多余的部分
                    postPropertyList.removeAll(prePropertyList);
                    if(postPropertyList.size() >0 ) {
                        CustomerPropertyRelExample example1 = new CustomerPropertyRelExample();
                        CustomerPropertyRelExample.Criteria criteria1 = example1.createCriteria();
                        criteria1.andProductInstIdEqualTo(productInstId);
                        criteria1.andCustomerIdEqualTo(customer.getCustomerId());
                        criteria1.andPropertyIdIn(postPropertyList);
                        customerPropertyRelMapper.deleteByExample(example1);
                    }
                }
            }

            //批量插入客户汽车信息
            List<CustomerCar> carList = customer.getCustomerCars();
            //if(carList != null && carList.size()>0 ) {
            if(carList != null){
                //传入了customerCarList,但内容为空,则清掉客户的汽车数据
                if(carList.size() == 0) {
                    CustomerCarExample example1 = new CustomerCarExample();
                    CustomerCarExample.Criteria criteria1 = example1.createCriteria();
                    criteria1.andProductInstIdEqualTo(productInstId);
                    criteria1.andCustomerIdEqualTo(customer.getCustomerId());
                    customerCarMapper.deleteByExample(example1);

                } else  {  //传入了内容，先更新，再删除

                    //提交的请求数据
                    List<String> prePropertyIdAndPlateNoList = new ArrayList<>();

                    for(CustomerCar customerCar : carList) {
                        prePropertyIdAndPlateNoList.add(customerCar.getPropertyId().toString() + "|" + customerCar.getPlateNo());

                        customerCar.setProductInstId(productInstId);
                        customerCar.setCustomerId(customer.getCustomerId());
                        customerCar.setModifyTime(new Date()); //避免update table set为空,导致update失败
                        //有可能是新增加的车,所以先更新,更新不了再插入
                        if (customerCarMapper.updateByPrimaryKeySelective(customerCar) == 0) {
                            customerCarMapper.insertSelective(customerCar);
                        }
                    }
                    logger.debug("prePropertyIdAndPlateNoList:" + prePropertyIdAndPlateNoList);

                    //更新后的数据
                    List<String> postPropertyIdAndPlateNoList = getPropertyIdAndPlateNoConcatByCustomerId(productInstId, customer.getCustomerId());
                    logger.debug("postPropertyIdAndPlateNoList:" + postPropertyIdAndPlateNoList);
                    //求补集，删除postPropertyIdAndPlateNoList多余的部分
                    postPropertyIdAndPlateNoList.removeAll(prePropertyIdAndPlateNoList);
                    logger.debug("postPropertyIdAndPlateNoList removed:" + postPropertyIdAndPlateNoList);
                    if(postPropertyIdAndPlateNoList.size() > 0) {
                        for(String propertyIdAndPlateNo : postPropertyIdAndPlateNoList) {
                            //String[] d = propertyIdAndPlateNo.split("|");
                            String[] d = StringUtils.split(propertyIdAndPlateNo, "|");
                            customerCarMapper.deleteByPrimaryKey(productInstId, customer.getCustomerId(), Long.parseLong(d[0]), d[1]);
                        }
                    }
                }

            }
            //更新redis
            addRedisValue(customer);
        }
        return num;
    }

    //获取客户房产关系的property_id组合
    //PRIMARY KEY (`product_inst_id`,`customer_id`,`property_id`)
    private List<Long> getPropertyIdListByCustomerId(String productInstId, Long customerId){

        List<Long> result = new ArrayList<>();
        CustomerPropertyRelExample example = new CustomerPropertyRelExample();
        CustomerPropertyRelExample.Criteria criteria = example.createCriteria();
        criteria.andProductInstIdEqualTo(productInstId);
        criteria.andCustomerIdEqualTo(customerId);
        List<CustomerPropertyRel> customerPropertyRelList = customerPropertyRelMapper.selectByExample(example);
        for(CustomerPropertyRel customerPropertyRel : customerPropertyRelList) {
            result.add(customerPropertyRel.getPropertyId());
        }
        return result;
    }

    //获取客户汽车关系的property_id和plate_no组合主键组合,以"|"分割
    //PRIMARY KEY (`product_inst_id`,`customer_id`,`property_id`, `plate_no`)
    private List<String> getPropertyIdAndPlateNoConcatByCustomerId(String productInstId, Long customerId){
        List<String> result = new ArrayList<>();
        CustomerCarExample example = new CustomerCarExample();
        CustomerCarExample.Criteria criteria = example.createCriteria();
        criteria.andProductInstIdEqualTo(productInstId);
        criteria.andCustomerIdEqualTo(customerId);
        List<CustomerCar> customerCarList = customerCarMapper.selectByExample(example);
        for(CustomerCar customerCar : customerCarList) {
            result.add(customerCar.getPropertyId().toString() + "|" + customerCar.getPlateNo());
        }
        return result;
    }

    //删除客户信息
    @Transactional
    public int delete(String productInstId, Long customerId) {

        //删除之前需要加入业务逻辑判断,不能随便删除
        //to-do
        //删除客户与房产关系
        CustomerPropertyRelExample customerPropertyRelExample = new CustomerPropertyRelExample();
        CustomerPropertyRelExample.Criteria criteria1 = customerPropertyRelExample.createCriteria();
        criteria1.andProductInstIdEqualTo(productInstId);
        criteria1.andCustomerIdEqualTo(customerId);
        customerPropertyRelMapper.deleteByExample(customerPropertyRelExample);

        //删除客户汽车信息
        CustomerCarExample customerCarExample = new CustomerCarExample();
        CustomerCarExample.Criteria criteria2 = customerCarExample.createCriteria();
        criteria1.andProductInstIdEqualTo(productInstId);
        criteria1.andCustomerIdEqualTo(customerId);
        customerCarMapper.deleteByExample(customerCarExample);

        //删除客户信息
        CustomerExample example = new CustomerExample();
        CustomerExample.Criteria criteria3 = example.createCriteria();
        criteria3.andCustomerIdEqualTo(customerId);
        criteria3.andProductInstIdEqualTo(productInstId);
        return customerMapperOld.deleteByExample(example);
    }
}
