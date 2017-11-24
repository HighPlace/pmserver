package com.highplace.biz.pm.service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.highplace.biz.pm.config.AliyunConfig;
import com.highplace.biz.pm.config.QCloudConfig;
import com.highplace.biz.pm.dao.base.CarMapper;
import com.highplace.biz.pm.dao.base.CustomerMapper;
import com.highplace.biz.pm.dao.base.PropertyMapper;
import com.highplace.biz.pm.dao.base.RelationMapper;
import com.highplace.biz.pm.domain.base.*;
import com.highplace.biz.pm.domain.ui.CustomerExcelBean;
import com.highplace.biz.pm.domain.ui.CustomerSearchBean;
import com.highplace.biz.pm.domain.ui.PropertySearchBean;
import com.highplace.biz.pm.service.common.MQService;
import com.highplace.biz.pm.service.common.TaskStatusService;
import com.highplace.biz.pm.service.util.CommonUtils;
import com.highplace.biz.pm.service.util.cloud.UploadDownloadTool;
import com.highplace.biz.pm.service.util.excel.ExcelUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.orderbyhelper.OrderByHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

@Service
public class CustomerService {

    public static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    //写入redis的key前缀, 后面加上productInstId
    public static final String PREFIX_CUSTOMER_NAME_KEY = "CUSTOMER_NAME_KEY_";
    public static final String PREFIX_CUSTOMER_PHONE_KEY = "CUSTOMER_PHONE_KEY_";
    public static final String PREFIX_CUSTOMER_PLATENO_KEY = "CUSTOMER_PLATENO_KEY_";

    @Autowired
    QCloudConfig qCloudConfig;
    @Autowired
    private AliyunConfig aliyunConfig;
    @Autowired
    private PropertyService propertyService;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private RelationMapper relationMapper;
    @Autowired
    private CarMapper carMapper;
    @Autowired
    private PropertyMapper propertyMapper;
    @Autowired
    private TaskStatusService taskStatusService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

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

    //查询客户信息列表
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
                logger.error("propertyService.query cast data key to List<Property> failed:{}" , e.getMessage());
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
            logger.debug("customerIdListByProperty: {}" ,customerIdListByProperty);
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
            logger.debug("customerIdListByCar: {}" ,customerIdListByCar);
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

    public void batchImport(JSONObject jsonObject) {

        //获取任务ID
        String taskId = jsonObject.getString(MQService.MSG_KEY_MSGID);

        //获取productInstID
        String productInstId = jsonObject.getString(MQService.MSG_KEY_PRODUCTINSTID);

        //获取文件在cos上的路径
        String cosFilePath = jsonObject.getString(MQService.MSG_KEY_FILEURL);

        //获取vendor
        Integer vendor = jsonObject.getInteger(MQService.MSG_KEY_VENDOR);

        //设置本地存储路径
        String localFilePath = "/tmp/" + cosFilePath;

        //设置任务状态为处理中
        taskStatusService.setTaskStatus(TaskStatusService.TaskTargetEnum.CUSTOMER,
                TaskStatusService.TaskTypeEnum.IMPORT,
                productInstId,
                taskId,
                TaskStatusService.TaskStatusEnum.DOING,
                null);

        //处理结果Map
        Map<String, Object> result = new HashMap<>();
        boolean dlResult;
        if (vendor == 0) {
            dlResult = UploadDownloadTool.downloadFromQCloud(qCloudConfig, cosFilePath, localFilePath);
        } else {
            dlResult = UploadDownloadTool.downloadFromAliyun(aliyunConfig, cosFilePath, localFilePath);
        }

        if (!dlResult) {   //下载文件失败
            //处理结果为失败
            result.put(TaskStatusService.TASK_RESULT_CODE_KEY, 20000);
            result.put(TaskStatusService.TASK_RESULT_MESSAGE_KEY, "download failed:" + cosFilePath);
        } else {
            //解析本地文件并导入数据库
            JSONObject jsonResult = readCustomerFromExcel(productInstId, localFilePath);

            //从cos应答中获取处理结果
            result.put(TaskStatusService.TASK_RESULT_CODE_KEY, jsonResult.getIntValue("code"));
            result.put(TaskStatusService.TASK_RESULT_MESSAGE_KEY, jsonResult.getString("message"));

            //删除本地文件
            File localFile = new File(localFilePath);
            localFile.delete();
        }

        //设置任务状态为1：处理完成
        taskStatusService.setTaskStatus(TaskStatusService.TaskTargetEnum.CUSTOMER,
                TaskStatusService.TaskTypeEnum.IMPORT,
                productInstId,
                taskId,
                TaskStatusService.TaskStatusEnum.DONE,
                result);
    }

    //读取Excel文件
    public JSONObject readCustomerFromExcel(String productInstID, String localFilePath) {

        //初始化输入流
        InputStream is = null;

        try {
            is = new FileInputStream(localFilePath);

            //根据版本选择创建Workbook的方式
            Workbook wb = null;
            //根据文件名判断文件是2003版本还是2007版本
            if (ExcelUtils.isExcel2007(localFilePath)) {
                wb = new XSSFWorkbook(is);
            } else if (ExcelUtils.isExcel2003(localFilePath)) {
                wb = new HSSFWorkbook(is);
            } else {
                JSONObject j = new JSONObject();
                j.put("code", 101);
                j.put("message", "文件格式错误");
                logger.error("readExcel fail:{}, localFilePath:{}", j.toJSONString() ,localFilePath);
                return j;
            }
            return loadExcelValue(productInstID, wb);

        } catch (Exception e) {

            JSONObject j = new JSONObject();
            j.put("code", 102);
            j.put("message", "文件读取错误");
            logger.error("readExcel fail:{}, localFilePath:{}, error:{}" , j.toJSONString() , localFilePath , e.getMessage());
            e.printStackTrace();
            return j;

        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    is = null;
                    logger.error("readExcel finally:{}" , e.getMessage());
                }
            }
        }
    }

    //从excel文件读取数据,并导入到数据库中
    //如果解析文件出错，将不会导入数据
    //批处理增加事务
    @Transactional
    protected JSONObject loadExcelValue(String productInstID, Workbook wb) {

        JSONObject result = new JSONObject();
        String errorMsg = "";
        String br = "<br/>";

        //得到第一个shell
        Sheet sheet = wb.getSheetAt(0);
        //总行数
        int totalRows = sheet.getPhysicalNumberOfRows();
        //总列数
        int totalCells = 0;
        //得到Excel的列数(前提是有行数)，从第二行算起
        if (totalRows >= 2 && sheet.getRow(1) != null) {
            totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
        }

        List<CustomerExcelBean> customerExcelBeanList = new ArrayList<>();
        CustomerExcelBean tempCustomerExcelBean;

        boolean errorFlag = false; //只要出现错误,都跳出循环

        //循环Excel行数,从第二行开始。标题不入库
        for (int r = 1; r < totalRows; r++) {

            Row row = sheet.getRow(r);
            if (row == null) {
                errorMsg += "第" + (r + 1) + "行数据有问题, 请仔细检查.";
                errorFlag = true;
                break;  //跳出循环
            }

            tempCustomerExcelBean = new CustomerExcelBean();

            //循环Excel的列
            String cellValue;
            for (int c = 0; c < totalCells; c++) {
                Cell cell = row.getCell(c);

                if (cell == null) {  //null的列,默认为空
                    cellValue = "";
                } else {
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    cellValue = cell.getStringCellValue().trim();
                }

                switch (c) {
                    case 0:  //房产名(分区+楼号+单元+房号)(必填)
                        if (StringUtils.isNotEmpty(cellValue)) {
                            tempCustomerExcelBean.relation.setPropertyName(cellValue);
                            Property property = propertyMapper.selectByPropertyName(productInstID, cellValue);
                            if (property == null) {
                                errorMsg += "第" + (r + 1) + "行" + (c + 1) + "列房产记录不存在, 请先创建房产档案;";
                                errorFlag = true;
                            } else {
                                //设置房产ID
                                tempCustomerExcelBean.relation.setPropertyId(property.getPropertyId());
                            }
                        } else {
                            errorFlag = true;
                            errorMsg += "第" + (r + 1) + "行" + (c + 1) + "列房产名不能为空, 请仔细检查;";
                        }
                        break;

                    case 1:  //客户类型(业主/租户/其他)(必填)
                        if (StringUtils.isNotEmpty(cellValue)) {
                            tempCustomerExcelBean.relation.setType(Relation.transferDescToType(cellValue));
                        } else {
                            errorMsg += "第" + (r + 1) + "行" + (c + 1) + "列数据有问题,客户类型请填写(业主/租户/其他),请仔细检查;";
                            errorFlag = true;
                        }
                        break;

                    case 2:  //客户姓名(必填)
                        if (StringUtils.isNotEmpty(cellValue)) {
                            tempCustomerExcelBean.customer.setCustomerName(cellValue);
                        } else {
                            errorMsg += "第" + (r + 1) + "行" + (c + 1) + "列数据有问题,客户姓名不能为空,请仔细检查;";
                            errorFlag = true;
                        }
                        break;

                    case 3:  //证件类型(居民身份证/护照/港澳回乡证/台胞证) (非必填)
                        if (StringUtils.isNotEmpty(cellValue)) {
                            tempCustomerExcelBean.customer.setIdentityType(Customer.transferDescToIdentityType(cellValue));
                        }
                        break;

                    case 4:  //证件号码 (必填)
                        if (StringUtils.isNotEmpty(cellValue)) {
                            tempCustomerExcelBean.customer.setIdentityNo(cellValue);
                        } else {
                            errorMsg += "第" + (r + 1) + "行" + (c + 1) + "列数据有问题,证件号码不能为空,请仔细检查;";
                            errorFlag = true;
                        }
                        break;

                    case 5:  //联系电话 (必填)
                        if (StringUtils.isNotEmpty(cellValue)) {
                            tempCustomerExcelBean.customer.setPhone(cellValue);
                        } else {
                            errorMsg += "第" + (r + 1) + "行" + (c + 1) + "列数据有问题,联系电话不能为空,请仔细检查;";
                            errorFlag = true;
                        }
                        break;

                    case 6:  //备用联系电话 (非必填)
                        if (StringUtils.isNotEmpty(cellValue)) {
                            tempCustomerExcelBean.customer.setBackupPhone1(cellValue);
                        }
                        break;

                    case 7:  //电子邮箱地址 (非必填)
                        if (StringUtils.isNotEmpty(cellValue)) {
                            tempCustomerExcelBean.customer.setEmail(cellValue);
                        }
                        break;

                    case 8:  //国籍 (非必填)
                        if (StringUtils.isNotEmpty(cellValue)) {
                            tempCustomerExcelBean.customer.setNation(cellValue);
                        }
                        break;

                    case 9:  //性别 (非必填)
                        if (StringUtils.isNotEmpty(cellValue)) {
                            tempCustomerExcelBean.customer.setGender(cellValue);
                        }
                        break;

                    case 10:  //车牌号 (非必填)
                        if (StringUtils.isNotEmpty(cellValue)) {
                            tempCustomerExcelBean.car.setPlateNo(cellValue);
                        }
                        break;

                    case 11:  //车位信息 (非必填)
                        if (StringUtils.isNotEmpty(cellValue)) {
                            tempCustomerExcelBean.car.setParkInfo(cellValue);
                        }
                        break;

                    case 12:  //车位类型(公共产权/自有产权) (非必填)
                        if (StringUtils.isNotEmpty(cellValue)) {
                            tempCustomerExcelBean.car.setType(Car.transferDescToType(cellValue));
                        }
                        break;

                    default:
                        break;
                }
            }
            if (errorFlag) {
                break;  //只要出现错误，跳出循环
            }
            customerExcelBeanList.add(tempCustomerExcelBean);
        }

        if (errorFlag) {
            result.put("code", 103);
            result.put("message", errorMsg);
            logger.error("loadExcelValue error:{}, productInstID:{}" ,result.toJSONString() ,productInstID);
        } else {
            int number = 0;
            CustomerExample example;
            for (CustomerExcelBean customerExcelBean : customerExcelBeanList) {

                //重要,先写入productInstId
                customerExcelBean.customer.setProductInstId(productInstID);
                customerExcelBean.relation.setProductInstId(productInstID);
                customerExcelBean.car.setProductInstId(productInstID);

                //根据组合主键查询是否存在记录
                example = new CustomerExample();
                CustomerExample.Criteria criteria = example.createCriteria();
                criteria.andProductInstIdEqualTo(customerExcelBean.relation.getProductInstId());
                criteria.andIdentityTypeEqualTo(customerExcelBean.customer.getIdentityType());
                criteria.andIdentityNoEqualTo(customerExcelBean.customer.getIdentityNo());
                List<Customer> find = customerMapper.selectByExample(example);

                if (find.size() == 0) {  //不存在记录,直接insert
                    number += insert(productInstID, customerExcelBean.customer);

                    //插入客户房产关系表
                    customerExcelBean.relation.setCustomerId(customerExcelBean.customer.getCustomerId());
                    relationMapper.insertSelective(customerExcelBean.relation);

                    //如果有写入车牌号信息，插入客户房产下的车辆信息表
                    if (customerExcelBean.car.getPlateNo() != null) {
                        customerExcelBean.car.setRelationId(customerExcelBean.relation.getRelationId());
                        carMapper.insertSelective(customerExcelBean.car);
                    }

                } else if (find.size() == 1) { //存在记录,进行update

                    //写入customId
                    customerExcelBean.customer.setCustomerId(find.get(0).getCustomerId());
                    //进行更新
                    number += update(productInstID, customerExcelBean.customer);

                    //查看是否已经存在房产和客户的关系
                    customerExcelBean.relation.setCustomerId(customerExcelBean.customer.getCustomerId());
                    RelationExample relationExample = new RelationExample();
                    RelationExample.Criteria relationCriteria = relationExample.createCriteria();
                    relationCriteria.andProductInstIdEqualTo(customerExcelBean.relation.getProductInstId());
                    relationCriteria.andCustomerIdEqualTo(customerExcelBean.relation.getCustomerId());
                    relationCriteria.andPropertyIdEqualTo(customerExcelBean.relation.getPropertyId());
                    List<Relation> relationList = relationMapper.selectByExample(relationExample);

                    if (relationList.size() == 0) {
                        //没有房产和客户的关系，插入记录
                        relationMapper.insertSelective(customerExcelBean.relation);
                    } else {
                        //有记录，写入relationId, update
                        customerExcelBean.relation.setRelationId(relationList.get(0).getRelationId());
                        customerExcelBean.relation.setModifyTime(new Date());
                        relationMapper.updateByPrimaryKeySelective(customerExcelBean.relation);
                    }

                    //如果有写入车牌号信息，先查看是否存在房产下的车牌号信息
                    if (customerExcelBean.car.getPlateNo() != null) {
                        customerExcelBean.car.setRelationId(customerExcelBean.relation.getRelationId());

                        CarExample carExample = new CarExample();
                        CarExample.Criteria carCriteria = carExample.createCriteria();
                        carCriteria.andProductInstIdEqualTo(customerExcelBean.car.getProductInstId());
                        carCriteria.andRelationIdEqualTo(customerExcelBean.car.getRelationId());
                        carCriteria.andPlateNoEqualTo(customerExcelBean.car.getPlateNo());
                        List<Car> carList = carMapper.selectByExample(carExample);
                        if (carList.size() == 0) {
                            //没有记录，插入
                            carMapper.insertSelective(customerExcelBean.car);
                        } else {
                            //有记录，update
                            customerExcelBean.car.setCarId(carList.get(0).getCarId());
                            customerExcelBean.car.setModifyTime(new Date());
                            carMapper.updateByPrimaryKeySelective(customerExcelBean.car);
                        }
                    }
                }

            }
            if (number < customerExcelBeanList.size()) {
                errorMsg = "导入成功，共" + number + "条数据, 重复" + (customerExcelBeanList.size() - number) + "条数据";
            } else {
                errorMsg = "导入成功，共" + number + "条数据";
            }
            result.put("code", 0);
            result.put("message", errorMsg);
            result.put("totalNum", customerExcelBeanList.size());
            result.put("insertNum", number);
            logger.debug("loadExcelValue success:{}, productInstID:{}" ,result.toJSONString() ,productInstID);
        }

        return result;
    }

    //从消息队列接收消息后进行导出数据库操作
    public void batchExport(JSONObject jsonObject) {

        //获取任务ID
        String taskId = jsonObject.getString(MQService.MSG_KEY_MSGID);

        //获取productInstID
        String productInstId = jsonObject.getString(MQService.MSG_KEY_PRODUCTINSTID);

        //获取vendor
        Integer vendor = jsonObject.getInteger(MQService.MSG_KEY_VENDOR);

        //设置任务为处理中
        taskStatusService.setTaskStatus(TaskStatusService.TaskTargetEnum.CUSTOMER,
                TaskStatusService.TaskTypeEnum.EXPORT,
                productInstId,
                taskId,
                TaskStatusService.TaskStatusEnum.DOING,
                null);

        //设置存放的目录和文件名
        String targetFilename = "customer_" + productInstId + "-" + new SimpleDateFormat("ddHHmmssS").format(new Date()) + ".xls";
        String cosFolder = "/" + new SimpleDateFormat("yyyyMM").format(new Date()) + "/";
        String cosFilePath = cosFolder + targetFilename;
        String localFilePath = "/tmp/" + targetFilename;

        //获取数据
        CustomerExample customerExample = new CustomerExample();
        CustomerExample.Criteria criteria = customerExample.createCriteria();
        criteria.andProductInstIdEqualTo(productInstId);
        //OrderByHelper.orderBy(" property_type, zone_id, building_id, unit_id, room_id asc");
        List<Customer> customerList = customerMapper.selectByExampleWithRelationAndCarWithBLOBs(customerExample);
        List<CustomerExcelBean> customerExcelBeanList = new ArrayList<>();
        for (Customer customer : customerList) {
            List<Relation> relationList = customer.getRelationList();
            if (relationList != null && relationList.size() > 0) {
                for (Relation relation : relationList) {
                    List<Car> carList = relation.getCarList();
                    if (carList != null && carList.size() > 0) {
                        for (Car car : carList) {
                            customerExcelBeanList.add(new CustomerExcelBean(customer, relation, car));
                        }
                    } else {
                        customerExcelBeanList.add(new CustomerExcelBean(customer, relation));
                    }
                }
            } else {
                customerExcelBeanList.add(new CustomerExcelBean(customer));
            }
        }

        //不按模板导出excel, 基于注解
        ExcelUtils.getInstance().exportObj2Excel(localFilePath, customerExcelBeanList, CustomerExcelBean.class);

        //上传到云OSS,并删除本地文件
        Map<String, Object> result;
        if (vendor == 0) {  //腾讯云
            result = UploadDownloadTool.uploadToQCloud(qCloudConfig, cosFolder, cosFilePath, localFilePath);
        } else {  //vendor=1 阿里云
            result = UploadDownloadTool.uploadToAliyun(aliyunConfig, cosFolder, cosFilePath, localFilePath);
        }

        //设置任务状态为1：处理完成
        taskStatusService.setTaskStatus(TaskStatusService.TaskTargetEnum.CUSTOMER,
                TaskStatusService.TaskTypeEnum.EXPORT,
                productInstId,
                taskId,
                TaskStatusService.TaskStatusEnum.DONE,
                result);
    }
}
