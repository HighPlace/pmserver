package com.highplace.biz.pm.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.highplace.biz.pm.dao.CustomerCarMapper;
import com.highplace.biz.pm.dao.CustomerMapper;
import com.highplace.biz.pm.dao.CustomerPropertyRelMapper;
import com.highplace.biz.pm.domain.*;
import com.highplace.biz.pm.domain.ui.CustomerSearchBean;
import com.highplace.biz.pm.domain.ui.PropertySearchBean;
import com.highplace.biz.pm.service.util.CommonUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.orderbyhelper.OrderByHelper;

import java.util.*;

@Service
public class CustomerService {

    public static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private CustomerPropertyRelMapper customerPropertyRelMapper;

    @Autowired
    private CustomerCarMapper customerCarMapper;

    //返回空的查询结果
    public static Map<String, Object> queryEmpty () {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("totalCount", 0);
        result.put("data", "");
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
        List<Customer> customerList = customerMapper.selectByExampleWithPropertyAndCar(example);

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

}
