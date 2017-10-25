package com.highplace.biz.pm.service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.highplace.biz.pm.config.QCloudConfig;
import com.highplace.biz.pm.dao.base.CarMapper;
import com.highplace.biz.pm.dao.base.CustomerMapper;
import com.highplace.biz.pm.dao.base.RelationMapper;
import com.highplace.biz.pm.domain.base.*;
import com.highplace.biz.pm.domain.ui.CustomerSearchBean;
import com.highplace.biz.pm.domain.ui.PropertySearchBean;
import com.highplace.biz.pm.service.common.MQService;
import com.highplace.biz.pm.service.common.TaskStatusService;
import com.highplace.biz.pm.service.util.CommonUtils;
import com.highplace.biz.pm.service.util.ExcelUtils;
import com.highplace.biz.pm.service.util.QCloudCosHelper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.orderbyhelper.OrderByHelper;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

@Service
public class CustomerService {

    public static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    @Autowired
    QCloudConfig qCloudConfig;
    @Autowired
    private PropertyService propertyService;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private RelationMapper relationMapper;
    @Autowired
    private CarMapper carMapper;
    @Autowired
    private TaskStatusService taskStatusService;
    @Autowired
    private MQService mqService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    //写入redis的key前缀, 后面加上productInstId
    public static final String PREFIX_CUSTOMER_NAME_KEY = "CUSTOMER_NAME_KEY_";
    public static final String PREFIX_CUSTOMER_PHONE_KEY = "CUSTOMER_PHONE_KEY_";
    public static final String PREFIX_CUSTOMER_PLATENO_KEY = "CUSTOMER_PLATENO_KEY_";

    //返回空的查询结果
    public static Map<String, Object> queryEmpty() {
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
        if (relationList != null) {
            for (Relation relation : relationList) {
                List<Car> carList = relation.getCarList();
                if (carList != null) {
                    for (Car car : carList) {
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
        if (entity.equals("name")) {
            redisKey = PREFIX_CUSTOMER_NAME_KEY + productInstId;
        } else if (entity.equals("phone")) {
            redisKey = PREFIX_CUSTOMER_PHONE_KEY + productInstId;
        } else if (entity.equals("plateNo")) {
            redisKey = PREFIX_CUSTOMER_PLATENO_KEY + productInstId;
        } else {
            return null;
        }

        Set<String> sEntity = stringRedisTemplate.opsForSet().members(redisKey);
        if (sEntity == null) return null;

        List<String> dataList = new ArrayList();
        Pattern pattern = Pattern.compile(searchValue, Pattern.CASE_INSENSITIVE); //大小写不敏感
        int i = 0;
        for (String entityValue : sEntity) {
            i++;
            if (pattern.matcher(entityValue).find()) dataList.add(entityValue);  //find()模糊匹配  matches()精确匹配
            if (i >= 10) break; //匹配到超过10条记录，退出
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
        if (StringUtils.isNotEmpty(searchBean.getZoneId())
                || StringUtils.isNotEmpty(searchBean.getBuildingId())
                || StringUtils.isNotEmpty(searchBean.getUnitId())
                || StringUtils.isNotEmpty(searchBean.getRoomId())
                || searchBean.getStatus() != null) {

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
            if (propertyList.size() == 0) return queryEmpty();

            List<Long> propertyIdList = new ArrayList<>();
            for (Property property : propertyList) {
                propertyIdList.add(property.getPropertyId());
            }

            //2 从customer property关系表中查询所有的customer ID
            RelationExample relationExample = new RelationExample();
            RelationExample.Criteria criteria1 = relationExample.createCriteria();
            criteria1.andPropertyIdIn(propertyIdList);
            List<Relation> relationList = relationMapper.selectByExample(relationExample);
            //如果没有查到房产和客户的对应关系，直接返回
            if (relationList.size() == 0) return queryEmpty();

            for (Relation relation : relationList) {
                customerIdListByProperty.add(relation.getCustomerId());
            }
            hasCustomerIdListByProperty = true;
            logger.debug("customerIdListByProperty: " + customerIdListByProperty);
        }

        //如果有传入汽车相关查询信息,查出对应的客户ID List
        if (StringUtils.isNotEmpty(searchBean.getPlateNo())) {

            CarExample example1 = new CarExample();
            CarExample.Criteria criteria1 = example1.createCriteria();
            criteria1.andPlateNoLike("%" + searchBean.getPlateNo() + "%"); //模糊查询
            List<Car> carList = carMapper.selectByExampleWithRelation(example1);
            //如果没有查到车，直接返回
            if (carList.size() == 0) return queryEmpty();

            for (Car car : carList) {
                customerIdListByCar.add(car.getRelation().getCustomerId());
            }
            hasustomerIdListByCar = true;
            logger.debug("customerIdListByCar: " + customerIdListByCar);
        }

        //加入客户ID List的and条件查询
        if (hasCustomerIdListByProperty) {
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

        if (StringUtils.isNotEmpty(searchBean.getCustomerName())) {
            criteria.andCustomerNameLike("%" + searchBean.getCustomerName() + "%"); //模糊查询
        }
        if (StringUtils.isNotEmpty(searchBean.getPhone())) {
            criteria.andPhoneLike("%" + searchBean.getPhone() + "%"); //模糊查询
        }

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
        List<Customer> customerList = customerMapper.selectByExampleWithRelationAndCarWithBLOBs(example);

        //总记录数
        long totalCount;

        //判断是否有分页
        if (!noPageSortFlag && searchBean.getPageNum() != null && searchBean.getPageSize() != null) {
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
    //interalFlag: true 内部调用，需要判断对应的主键id是否为null，为null则不插入
    @Transactional
    public int insert(String productInstId, Customer customer) {

        //设置产品实例ID
        customer.setProductInstId(productInstId);
        int num = customerMapper.insertSelective(customer);
        if (num == 1) {
            //批量插入客户和房产对应关系以及客户房产下的车辆信息
            List<Relation> relationList = customer.getRelationList();
            if (relationList != null) {
                for (Relation relation : relationList) {

                    relation.setProductInstId(productInstId);
                    relation.setCustomerId(customer.getCustomerId());
                    relationMapper.insertSelective(relation);

                    List<Car> carList = relation.getCarList();
                    if (carList != null) {
                        for (Car car : carList) {
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


    //通过层级删除客户信息
    // level: 0:删除客户信息/客户房产关系/客户房产下的车辆信息
    // level: 1:客户房产关系/客户房产下的车辆信息
    // level: 2:客户房产下的车辆信息
    @Transactional
    public int delete(String productInstId, Long customerId, Long relationId, int level) {

        //删除之前需要加入业务逻辑判断,不能随便删除
        //to-do

        CustomerExample customerExample = new CustomerExample();
        CustomerExample.Criteria criteria = customerExample.createCriteria();
        criteria.andProductInstIdEqualTo(productInstId);
        criteria.andCustomerIdEqualTo(customerId);
        List<Customer> customerList = customerMapper.selectByExampleWithRelationAndCarWithBLOBs(customerExample);

        if (customerList.size() == 1) {
            List<Relation> relationList = customerList.get(0).getRelationList();
            if (relationList != null) {
                for (Relation relation : relationList) {

                    List<Car> carList = relation.getCarList();
                    if (carList != null) {
                        for (Car car : carList) {
                            if (relationId == null) {
                                carMapper.deleteByPrimaryKey(car.getCarId());
                            } else if (relationId.longValue() == car.getRelationId().longValue()) {
                                carMapper.deleteByPrimaryKey(car.getCarId());
                            }
                        }
                    }
                    if (level == 0 || level == 1) relationMapper.deleteByPrimaryKey(relation.getRelationId());
                }
            }
            return (level == 0) ? customerMapper.deleteByPrimaryKey(customerList.get(0).getCustomerId()) : 0;
        } else {
            return 0;
        }
    }

    //修改客户信息
    @Transactional
    public int update(String productInstId, Customer customer) {

        //先更新客户信息表
        CustomerExample example = new CustomerExample();
        CustomerExample.Criteria criteria = example.createCriteria();
        criteria.andCustomerIdEqualTo(customer.getCustomerId()); //客户ID
        criteria.andProductInstIdEqualTo(productInstId); //产品实例ID，必须填入
        int num = customerMapper.updateByExampleSelective(customer, example);

        //如果更新成功,则继续更新客户和房产关系，以及客户房产下的车辆信息
        if (num == 1) {

            //获取提交的客户和房产关系列表
            List<Relation> relationList = customer.getRelationList();

            //如果列表不为null，则进行更新操作
            if (relationList != null) {

                //如果relationList内容为空,则清掉所有客户和房产对应关系，以及客户房产下的车辆信息
                if (relationList.size() == 0) {
                    delete(productInstId, customer.getCustomerId(), null, 1);

                    //否则，进行更新操作
                } else {

                    //记录更新前的所有relationID列表
                    List<Long> beforeRelationIdList = new ArrayList<>();
                    List<Long> afterRelationIdList = new ArrayList<>();
                    for (Relation relation : relationList) {
                        //为空是属于新增的relation
                        if (relation.getRelationId() != null) {
                            beforeRelationIdList.add(relation.getRelationId());
                        }
                    }

                    //遍历relatinoList，进行更新操作
                    for (Relation relation : relationList) {

                        //不为null 表示修改relation信息
                        if (relation.getRelationId() != null) {

                            //1 更新relation表
                            relation.setModifyTime(new Date()); //防止update失败
                            if (relationMapper.updateByPrimaryKeySelective(relation) != 1) {
                                continue; //说明更新失败,relatinoId不对
                            }

                            //2 更新relation下的carList表
                            List<Car> carList = relation.getCarList();
                            if (carList != null) {
                                //传入了carList，但内容为空，,则清掉客户在该房产下的车辆信息
                                if (carList.size() == 0) {
                                    delete(productInstId, customer.getCustomerId(), relation.getRelationId(), 2);
                                } else {

                                    List<Long> beforeCarIdList = new ArrayList<>();
                                    List<Long> afterCarIdList = new ArrayList<>();

                                    //更新前的所有carId列表
                                    for (Car car : carList) {
                                        if (car.getCarId() != null) {
                                            beforeCarIdList.add(car.getCarId());
                                        }
                                    }

                                    //更新car信息
                                    for (Car car : carList) {
                                        if (car.getCarId() != null) { //有传入carId，说明是修改car信息
                                            car.setModifyTime(new Date());
                                            carMapper.updateByPrimaryKeySelective(car);
                                        } else {  //没有传入carId，是新增car信息
                                            car.setRelationId(relation.getRelationId());
                                            car.setProductInstId(productInstId);
                                            carMapper.insertSelective(car);
                                            beforeCarIdList.add(car.getCarId());
                                        }
                                    }

                                    //更新后的所有carId列表
                                    List<Car> afterCarList = carMapper.selectByRelationId(relation.getRelationId());
                                    for (Car car : afterCarList) {
                                        afterCarIdList.add(car.getCarId());
                                    }

                                    //求补集，删除afterCarIdList多余的部分
                                    afterCarIdList.removeAll(beforeCarIdList);
                                    if (afterCarIdList.size() > 0) {
                                        CarExample example1 = new CarExample();
                                        CarExample.Criteria criteria1 = example1.createCriteria();
                                        criteria1.andProductInstIdEqualTo(productInstId);
                                        criteria1.andCarIdIn(afterCarIdList);
                                        carMapper.deleteByExample(example1);
                                    }
                                }
                            }


                            //为null 表示新增relation信息
                        } else {

                            //1 新增relation表
                            relation.setProductInstId(productInstId);
                            relation.setCustomerId(customer.getCustomerId());
                            relationMapper.insertSelective(relation);

                            //将新增的relationId加入到beforeRelationIdList列表中
                            beforeRelationIdList.add(relation.getRelationId());

                            //2 新增relation下的carList表
                            List<Car> carList = relation.getCarList();
                            if (carList != null) {
                                for (Car car : carList) {
                                    car.setRelationId(relation.getRelationId());
                                    car.setProductInstId(productInstId);
                                    carMapper.insertSelective(car);
                                }
                            }
                        }
                    }

                    //更新后的所有relatinoId列表
                    List<Relation> afterRelationList = relationMapper.selectByCustomerIdWithCar(customer.getCustomerId());
                    for (Relation relation : afterRelationList) {
                        afterRelationIdList.add(relation.getRelationId());
                    }

                    //求补集，删除afterCarIdList多余的部分
                    afterRelationIdList.removeAll(beforeRelationIdList);
                    if (afterRelationIdList.size() > 0) {
                        RelationExample example1 = new RelationExample();
                        RelationExample.Criteria criteria1 = example1.createCriteria();
                        criteria1.andProductInstIdEqualTo(productInstId);
                        criteria1.andCustomerIdEqualTo(customer.getCustomerId());
                        criteria1.andRelationIdIn(afterRelationIdList);
                        relationMapper.deleteByExample(example1);
                    }

                }
            }

            //更新redis
            addRedisValue(customer);
        }
        return num;
    }

    //将批量导入请求通过消息队列发出
    public String batchImportCall(String productInstId, String fileUrl, Integer vendor) {

        //生成消息和任务ID，使用同一个ID
        String msgAndTaskId = UUID.randomUUID().toString();

        //发送请求到消息队列
        mqService.sendImportMessage(msgAndTaskId,
                productInstId,
                TaskStatusService.TASK_TARGET_CUSTOMER,
                fileUrl,
                vendor);

        //设置任务为等待处理中
        taskStatusService.setTaskStatus(TaskStatusService.TASK_TARGET_CUSTOMER,
                TaskStatusService.TaskTypeEnum.IMPORT,
                productInstId,
                msgAndTaskId,
                TaskStatusService.TaskStatusEnum.WAIT,
                null);

        return msgAndTaskId;
    }

    //将批量导出请求通过消息队列发出
    public String batchExportCall(String productInstId, Integer vendor) {

        //生成消息和任务ID，使用同一个ID
        String msgAndTaskId = UUID.randomUUID().toString();

        //发送请求到消息队列
        mqService.sendExportMessage(msgAndTaskId,
                productInstId,
                TaskStatusService.TASK_TARGET_CUSTOMER,
                vendor);

        //设置任务为等待处理中
        taskStatusService.setTaskStatus(TaskStatusService.TASK_TARGET_CUSTOMER,
                TaskStatusService.TaskTypeEnum.EXPORT,
                productInstId,
                msgAndTaskId,
                TaskStatusService.TaskStatusEnum.WAIT,
                null);

        return msgAndTaskId;
    }

    //查询task状态
    //flag: 0:import 1:export
    public Map<Object, Object> getTaskStatus(String productInstId, String taskId, String taskType) {

        if (taskType.equals("import")) {
            return taskStatusService.getTaskStatus(TaskStatusService.TASK_TARGET_CUSTOMER,
                    TaskStatusService.TaskTypeEnum.IMPORT,
                    productInstId,
                    taskId);

        } else if (taskType.equals("export")) {
            return taskStatusService.getTaskStatus(TaskStatusService.TASK_TARGET_CUSTOMER,
                    TaskStatusService.TaskTypeEnum.EXPORT,
                    productInstId,
                    taskId);
        } else {
            return null;
        }
    }

    public void batchImport(JSONObject jsonObject) {

    }

    //从消息队列接收消息后进行导出数据库操作
    public void batchExport(JSONObject jsonObject) {

        //获取任务ID
        String taskId = jsonObject.getString(MQService.MSG_KEY_MSGID);

        //获取productInstID
        String productInstId = jsonObject.getString(MQService.MSG_KEY_PRODUCTINSTID);

        //设置任务为处理中
        taskStatusService.setTaskStatus(TaskStatusService.TASK_TARGET_CUSTOMER,
                TaskStatusService.TaskTypeEnum.EXPORT,
                productInstId,
                taskId,
                TaskStatusService.TaskStatusEnum.DOING,
                null);

        //处理结果Map
        Map<String, Object> result = new HashMap<>();

        //读取到excel并上传到cos
        JSONObject jsonResult = writeExcelAndUploadCosNew(productInstId);
        int code = jsonResult.getIntValue("code");
        if (code != 0) {
            String errMsg = jsonResult.getString("message");
            String resultMsg = "上传文件失败(qcloud:" + code + "," + errMsg + ")";

            //处理结果为失败
            result.put(TaskStatusService.TASK_RESULT_CODE_KEY, 20000);
            result.put(TaskStatusService.TASK_RESULT_MESSAGE_KEY, resultMsg);

        } else {
            //处理结果为成功
            result.put(TaskStatusService.TASK_RESULT_CODE_KEY, 0);
            result.put(TaskStatusService.TASK_RESULT_MESSAGE_KEY, "SUCCESS");
            result.put(TaskStatusService.TASK_RESULT_FILEURL_KEY, jsonResult.getString(TaskStatusService.TASK_RESULT_FILEURL_KEY));
        }

        //设置任务状态为1：处理完成
        taskStatusService.setTaskStatus(TaskStatusService.TASK_TARGET_CUSTOMER,
                TaskStatusService.TaskTypeEnum.EXPORT,
                productInstId,
                taskId,
                TaskStatusService.TaskStatusEnum.DONE,
                result);
    }

    //读取房产资料并上传到cos,基于注解方式
    private JSONObject writeExcelAndUploadCosNew(String productInstId) {

        String targetFilename = "customer_" + productInstId + "-" + new SimpleDateFormat("ddHHmmssS").format(new Date()) + ".xls";
        String cosFolder = "/" + new SimpleDateFormat("yyyyMM").format(new Date()) + "/";
        String cosFilePath = cosFolder + targetFilename;
        String localFilePath = "/tmp/" + targetFilename;

        //获取数据
        CustomerExample customerExample = new CustomerExample();
        CustomerExample.Criteria criteria = customerExample.createCriteria();
        criteria.andProductInstIdEqualTo(productInstId);
        //OrderByHelper.orderBy(" property_type, zone_id, building_id, unit_id, room_id asc");
        List<Customer> customerList = customerMapper.selectByExample(customerExample);

        //不按模板导出excel, 基于注解
        ExcelUtils.getInstance().exportObj2Excel(localFilePath, customerList, Customer.class);

        //按模板导出excel
        //Map<String, String> map = new HashMap<String, String>();
        //map.put("title", "房产档案");
        //map.put("total", propertyList.size()+" 条");
        //map.put("date", new SimpleDateFormat("yyyy年MM月dd日").format(new Date()));
        //ExcelUtils.getInstance().exportObj2ExcelByTemplate(map, "default-template.xls", localFilePath, propertyList, Property.class, true);

        //创建qcloud cos操作Helper对象
        QCloudCosHelper qCloudCosHelper = new QCloudCosHelper(qCloudConfig.getAppId(), qCloudConfig.getSecretId(), qCloudConfig.getSecretKey());
        //创建cos folder
        qCloudCosHelper.createFolder(qCloudConfig.getCosBucketName(), cosFolder);
        //上传文件
        JSONObject jsonUploadResult = qCloudCosHelper.uploadFile(qCloudConfig.getCosBucketName(), cosFilePath, localFilePath);

        if (jsonUploadResult.getIntValue("code") == 0) {
            //生成下载导出结果文件的url
            String downloadUrl = qCloudCosHelper.getDownLoadUrl(qCloudConfig.getCosBucketName(), cosFilePath, jsonUploadResult.getJSONObject("data").getString("source_url"));
            jsonUploadResult.put(TaskStatusService.TASK_RESULT_FILEURL_KEY, downloadUrl);
        }

        // 关闭释放资源
        qCloudCosHelper.releaseCosClient();

        //删除本地文件
        File localFile = new File(localFilePath);
        localFile.delete();

        return jsonUploadResult;
    }

}
