package com.highplace.biz.pm.service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.highplace.biz.pm.config.QCloudConfig;
import com.highplace.biz.pm.dao.base.PropertyMapper;
import com.highplace.biz.pm.dao.base.RelationMapper;
import com.highplace.biz.pm.domain.base.Property;
import com.highplace.biz.pm.domain.base.PropertyExample;
import com.highplace.biz.pm.domain.base.RelationExample;
import com.highplace.biz.pm.domain.ui.PropertySearchBean;
import com.highplace.biz.pm.service.common.MQService;
import com.highplace.biz.pm.service.common.TaskStatusService;
import com.highplace.biz.pm.service.util.CommonUtils;
import com.highplace.biz.pm.service.util.ExcelUtils;
import com.highplace.biz.pm.service.util.QCloudCosHelper;
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


@Service
public class PropertyService {

    public static final Logger logger = LoggerFactory.getLogger(PropertyService.class);

    //写入redis的key前缀, 后面加上productInstId
    public static final String PREFIX_PROPERTY_ZONEID_KEY = "PROPERTY_ZONEID_KEY_";
    public static final String PREFIX_PROPERTY_BUILDINGID_KEY = "PROPERTY_BUILDINGID_KEY_";
    public static final String PREFIX_PROPERTY_UNITID_KEY = "PROPERTY_UNITID_KEY_";

    @Autowired
    QCloudConfig qCloudConfig;
    @Autowired
    private PropertyMapper propertyMapper;
    @Autowired
    private RelationMapper relationMapper;
    @Autowired
    private TaskStatusService taskStatusService;
    @Autowired
    private MQService mqService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    //zoneId/buildingId/unitId以set数据结构缓存到redis中
    public void addRedisValue(Property property) {

        if (property.getProductInstId() == null) return;

        String redisKeyForZoneId = PREFIX_PROPERTY_ZONEID_KEY + property.getProductInstId();
        String redisKeyForBuildIdPrefix = PREFIX_PROPERTY_BUILDINGID_KEY + property.getProductInstId();
        String redisKeyForUnitIdPrefix = PREFIX_PROPERTY_UNITID_KEY + property.getProductInstId();

        String zoneId = (property.getZoneId() == null) ? "" : (property.getZoneId());
        String unitId = (property.getUnitId() == null) ? "" : (property.getUnitId());

        stringRedisTemplate.opsForSet().add(redisKeyForZoneId, zoneId);

        if (property.getBuildingId() != null) {
            stringRedisTemplate.opsForSet().add(redisKeyForBuildIdPrefix + zoneId, property.getBuildingId());
            stringRedisTemplate.opsForSet().add(redisKeyForUnitIdPrefix + property.getBuildingId(), unitId);
        }
    }

    //从redis中查询房产分区/楼号/单元信息
    public Map<String, Object> rapidSearchAllZoneBuildingUnitId(String productInstId) {

        String redisKeyForZondId = PREFIX_PROPERTY_ZONEID_KEY + productInstId;
        Set<String> sZoneId = stringRedisTemplate.opsForSet().members(redisKeyForZondId);
        if (sZoneId == null) return null;

        List<String> lZondId = new ArrayList<>(sZoneId);
        Collections.sort(lZondId);

        List<Object> zoneList = new ArrayList<>();

        for (int i = 0; i < lZondId.size(); i++) {

            Map<String, Object> zoneMap = new LinkedHashMap<>();

            String zoneId = lZondId.get(i);
            String redisKeyForBuildingId = PREFIX_PROPERTY_BUILDINGID_KEY + productInstId + zoneId;
            Set<String> sBuildingId = stringRedisTemplate.opsForSet().members(redisKeyForBuildingId);
            //if(sBuildingId == null) continue;

            List<String> lBuildingId = new ArrayList<>(sBuildingId);
            Collections.sort(lBuildingId);

            List<Object> buildingList = new ArrayList<>();

            for (int j = 0; j < lBuildingId.size(); j++) {

                String buildingId = lBuildingId.get(j);
                String redisKeyForUnitId = PREFIX_PROPERTY_UNITID_KEY + productInstId + buildingId;
                Set<String> sUnitId = stringRedisTemplate.opsForSet().members(redisKeyForUnitId);
                //if(sUnitId == null) continue;

                List<String> lUnitId = new ArrayList<>(sUnitId);
                Collections.sort(lUnitId);

                Map<String, Object> buildingMap = new LinkedHashMap<>();
                buildingMap.put("buildingId", buildingId);
                buildingMap.put("unitIdList", lUnitId);

                buildingList.add(buildingMap);
            }

            zoneMap.put("zoneId", zoneId);
            zoneMap.put("buildingIdList", buildingList);

            zoneList.add(zoneMap);
        }

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("zoneIdList", zoneList);
        return result;

        /*
        String[] strarrays = new String[]{"一区","二区","三区","四区","玫瑰苑"};

        stringRedisTemplate.opsForSet().add(redisKeyForZondId, strarrays);
        Set<String> s = stringRedisTemplate.opsForSet().members(redisKeyForZondId);
        //long size = stringRedisTemplate.opsForSet().size(redisKeyForZondId);
        //List<String> l = stringRedisTemplate.opsForSet().randomMembers(redisKeyForZondId, size);
        List<String> l = new ArrayList<>(s);
        Collections.sort(l);
        return l;
        */

        /*
        ZSetOperations.TypedTuple<String> objectTypedTuple1 = new DefaultTypedTuple<String>("玫瑰苑",0.0);
        ZSetOperations.TypedTuple<String> objectTypedTuple2 = new DefaultTypedTuple<String>("菊花苑",1.0);
        Set<ZSetOperations.TypedTuple<String>> tuples = new HashSet<ZSetOperations.TypedTuple<String>>();
        tuples.add(objectTypedTuple1);
        tuples.add(objectTypedTuple2);
        stringRedisTemplate.opsForZSet().add(PREFIX_PROPERTY_ZONEID_KEY + productInstId, tuples);
        */

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
    public Map<String, Object> query(String productInstId, PropertySearchBean searchBean, boolean noPageSortFlag) {

        PropertyExample example = new PropertyExample();
        PropertyExample.Criteria criteria = example.createCriteria();

        //产品实例ID，必须填入
        criteria.andProductInstIdEqualTo(productInstId);

        //查询条件判断
        if (searchBean.getZoneId() != null && !searchBean.getZoneId().equals(""))
            criteria.andZoneIdEqualTo(searchBean.getZoneId());

        if (searchBean.getBuildingId() != null && !searchBean.getBuildingId().equals(""))
            criteria.andBuildingIdEqualTo(searchBean.getBuildingId());

        if (searchBean.getUnitId() != null && !searchBean.getUnitId().equals(""))
            criteria.andUnitIdEqualTo(searchBean.getUnitId());

        if (searchBean.getRoomId() != null && !searchBean.getRoomId().equals(""))
            criteria.andRoomIdEqualTo(searchBean.getRoomId());

        if (searchBean.getStatus() != null)
            criteria.andStatusEqualTo(searchBean.getStatus());

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
        List<Property> properties = propertyMapper.selectByExample(example);

        //总记录数
        long totalCount;

        //判断是否有分页
        if (!noPageSortFlag && searchBean.getPageNum() != null && searchBean.getPageSize() != null) {
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

        int num = propertyMapper.insertSelective(property);
        if (num == 1) {
            //更新redis
            addRedisValue(property);
        }
        return num;
    }

    //删除房产信息
    public int delete(String productInstId, Long propertyId) {

        //删除之前需要加入业务逻辑判断,不能随便删除
        //判断客户和房产关系是否存在，如果存在，则不能删除房产
        RelationExample relationExample = new RelationExample();
        RelationExample.Criteria criteria = relationExample.createCriteria();
        criteria.andProductInstIdEqualTo(productInstId);
        criteria.andPropertyIdEqualTo(propertyId);

        //如果不存在客户房产关系，则可以删除
        if (relationMapper.countByExample(relationExample) == 0) {
            PropertyExample example = new PropertyExample();
            PropertyExample.Criteria criteria1 = example.createCriteria();
            criteria1.andPropertyIdEqualTo(propertyId);
            criteria1.andProductInstIdEqualTo(productInstId);
            return propertyMapper.deleteByExample(example);

        } else {
            return -1;
        }
    }

    //修改房产信息
    public int update(String productInstId, Property property) {

        PropertyExample example = new PropertyExample();
        PropertyExample.Criteria criteria = example.createCriteria();
        criteria.andPropertyIdEqualTo(property.getPropertyId());

        //产品实例ID，必须填入
        criteria.andProductInstIdEqualTo(productInstId);

        int num = propertyMapper.updateByExampleSelective(property, example);
        if (num == 1) {
            //更新redis
            addRedisValue(property);
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
                TaskStatusService.TASK_TARGET_PROPERTY,
                fileUrl,
                vendor);

        //设置任务为等待处理中
        taskStatusService.setTaskStatus(TaskStatusService.TASK_TARGET_PROPERTY,
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
                TaskStatusService.TASK_TARGET_PROPERTY,
                vendor);

        //设置任务为等待处理中
        taskStatusService.setTaskStatus(TaskStatusService.TASK_TARGET_PROPERTY,
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
            return taskStatusService.getTaskStatus(TaskStatusService.TASK_TARGET_PROPERTY,
                    TaskStatusService.TaskTypeEnum.IMPORT,
                    productInstId,
                    taskId);

        } else if (taskType.equals("export")) {
            return taskStatusService.getTaskStatus(TaskStatusService.TASK_TARGET_PROPERTY,
                    TaskStatusService.TaskTypeEnum.EXPORT,
                    productInstId,
                    taskId);
        } else {
            return null;
        }
    }

    //从消息队列接收消息后进行导入数据库操作
    //腾讯云操作,参考https://github.com/tencentyun/cos-java-sdk-v4/blob/master/src/main/java/com/qcloud/cos/demo/Demo.java
    public void batchImport(JSONObject jsonObject) {

        //获取任务ID
        String taskId = jsonObject.getString(MQService.MSG_KEY_MSGID);

        //获取productInstID
        String productInstId = jsonObject.getString(MQService.MSG_KEY_PRODUCTINSTID);

        //获取文件在cos上的路径
        String cosFilePath = jsonObject.getString(MQService.MSG_KEY_FILEURL);

        //设置本地存储路径
        String localFilePath = "/tmp/" + cosFilePath;

        //设置任务状态为处理中
        taskStatusService.setTaskStatus(TaskStatusService.TASK_TARGET_PROPERTY,
                TaskStatusService.TaskTypeEnum.IMPORT,
                productInstId,
                taskId,
                TaskStatusService.TaskStatusEnum.DOING,
                null);

        //处理结果Map
        Map<String, Object> result = new HashMap<>();

        //创建qcloud cos操作Helper对象
        QCloudCosHelper qCloudCosHelper = new QCloudCosHelper(qCloudConfig.getAppId(), qCloudConfig.getSecretId(), qCloudConfig.getSecretKey());

        //下载文件到本地
        JSONObject jsonGetFileResult = qCloudCosHelper.getFile(qCloudConfig.getCosBucketName(), cosFilePath, localFilePath);
        int code = jsonGetFileResult.getIntValue("code");

        if (code != 0) {
            //写结果数据,返回失败
            String errMsg = jsonGetFileResult.getString("message");
            String resultMsg = "获取文件失败(qcloud:" + code + "," + errMsg + ")";

            //处理结果为失败
            result.put(TaskStatusService.TASK_RESULT_CODE_KEY, 20000);
            result.put(TaskStatusService.TASK_RESULT_MESSAGE_KEY, resultMsg);
        } else {

            //解析本地文件并导入数据库
            JSONObject jsonResult = readExcel(productInstId, localFilePath);
            logger.debug("readExcel result:" + jsonResult.toJSONString());

            //从cos应答中获取处理结果
            result.put(TaskStatusService.TASK_RESULT_CODE_KEY, jsonResult.getIntValue("code"));
            result.put(TaskStatusService.TASK_RESULT_MESSAGE_KEY, jsonResult.getString("message"));

            //删除本地文件
            File localFile = new File(localFilePath);
            localFile.delete();

            //删除远程的文件
            qCloudCosHelper.deleteFile(qCloudConfig.getCosBucketName(), cosFilePath);
        }

        //设置任务状态为1：处理完成
        taskStatusService.setTaskStatus(TaskStatusService.TASK_TARGET_PROPERTY,
                TaskStatusService.TaskTypeEnum.IMPORT,
                productInstId,
                taskId,
                TaskStatusService.TaskStatusEnum.DONE,
                result);

        // 关闭释放资源
        qCloudCosHelper.releaseCosClient();
    }

    //读取Excel文件
    public JSONObject readExcel(String productInstID, String localFilePath) {

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
                logger.error("readExcel fail:" + j.toJSONString() + " localFilePath:" + localFilePath);
                return j;
            }
            return loadExcelValue(productInstID, wb);

        } catch (Exception e) {

            JSONObject j = new JSONObject();
            j.put("code", 102);
            j.put("message", "文件读取错误");
            logger.error("readExcel fail:" + j.toJSONString() + " localFilePath:" + localFilePath + " error:" + e.getMessage());
            e.printStackTrace();
            return j;

        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    is = null;
                    logger.error("readExcel finally:" + e.getMessage());
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

        List<Property> propertyList = new ArrayList<Property>();
        Property tempProperty;

        boolean errorFlag = false; //只要出现错误,都跳出循环

        //循环Excel行数,从第二行开始。标题不入库
        for (int r = 1; r < totalRows; r++) {

            Row row = sheet.getRow(r);
            if (row == null) {
                errorMsg += "第" + (r + 1) + "行数据有问题, 请仔细检查.";
                errorFlag = true;
                break;  //跳出循环
            }

            tempProperty = new Property();

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
                    case 0:  //分区名称 (非必填)
                        if (StringUtils.isNotEmpty(cellValue)) {
                            tempProperty.setZoneId(cellValue);
                        } else {
                            tempProperty.setZoneId("");
                        }
                        break;

                    case 1:  //楼号 (必填)
                        if (StringUtils.isNotEmpty(cellValue)) {
                            tempProperty.setBuildingId(cellValue);
                        } else {
                            errorMsg += "第" + (r + 1) + "行" + (c + 1) + "列数据有问题, 请仔细检查;";
                            errorFlag = true;
                        }
                        break;

                    case 2:  //单元(座) (非必填)
                        if (StringUtils.isNotEmpty(cellValue)) {
                            tempProperty.setUnitId(cellValue);
                        } else {
                            tempProperty.setUnitId("");
                        }
                        break;

                    case 3:  //房号 (必填)
                        if (StringUtils.isNotEmpty(cellValue)) {
                            tempProperty.setRoomId(cellValue);
                        } else {
                            errorMsg += "第" + (r + 1) + "行" + (c + 1) + "列数据有问题, 请仔细检查;";
                            errorFlag = true;
                        }
                        break;

                    case 4:  //产权面积 (必填)
                        if (StringUtils.isNotEmpty(cellValue)) {
                            try {
                                tempProperty.setPropertyArea(Double.parseDouble(cellValue));
                            } catch (NumberFormatException e) {
                                errorMsg += "第" + (r + 1) + "行" + (c + 1) + "列数据有问题, 请仔细检查;";
                                errorFlag = true;
                                logger.error("read PropertyArea failed: cellvalue:" + cellValue + " err:" + e.getMessage());
                                e.printStackTrace();
                            }
                        } else {
                            errorMsg += "第" + (r + 1) + "行" + (c + 1) + "列数据有问题, 请仔细检查;";
                            errorFlag = true;
                        }
                        break;

                    case 5:  //套内面积 (非必填)

                        if (StringUtils.isNotEmpty(cellValue)) {
                            try {
                                tempProperty.setFloorArea(Double.parseDouble(cellValue));
                            } catch (NumberFormatException e) {
                                errorMsg += "第" + (r + 1) + "行" + (c + 1) + "列数据有问题, 请仔细检查;";
                                errorFlag = true;
                                logger.error("read FloorArea failed: cellvalue:" + cellValue + " err:" + e.getMessage());
                                e.printStackTrace();
                            }
                        }
                        break;

                    case 6:  //户型 (非必填)
                        if (StringUtils.isNotEmpty(cellValue)) {
                            tempProperty.setHouseType(cellValue);
                        }
                        break;

                    case 7:  //房产状态(未售/未装修/装修中/已入住/已出租) (非必填)
                        if (StringUtils.isNotEmpty(cellValue)) {
                            tempProperty.setStatus(Property.transferDescToStatus(cellValue));
                        }
                        break;

                    default:
                        break;
                }
            }
            if (errorFlag) break;  //只要出现错误，跳出循环
            tempProperty.setPropertyType(0);
            propertyList.add(tempProperty);
        }

        if (errorFlag) {
            result.put("code", 103);
            result.put("message", errorMsg);
            logger.error("loadExcelValue error:" + result.toJSONString() + " productInstID:" + productInstID);

        } else {
            int number = 0;
            PropertyExample example;
            for (Property property : propertyList) {

                //根据组合主键查询是否存在记录
                example = new PropertyExample();
                PropertyExample.Criteria criteria = example.createCriteria();
                criteria.andProductInstIdEqualTo(productInstID);
                criteria.andPropertyTypeEqualTo(property.getPropertyType());
                criteria.andZoneIdEqualTo(property.getZoneId());
                criteria.andBuildingIdEqualTo(property.getBuildingId());
                criteria.andUnitIdEqualTo(property.getUnitId());
                criteria.andRoomIdEqualTo(property.getRoomId());

                List<Property> find = propertyMapper.selectByExample(example);
                if (find.size() == 0) {  //不存在记录,直接insert
                    number += insert(productInstID, property);
                } else if (find.size() == 1) { //存在记录,进行update
                    property.setPropertyId(find.get(0).getPropertyId());
                    number += update(productInstID, property);
                }

            }
            if (number < propertyList.size()) {
                errorMsg = "导入成功，共" + number + "条数据, 重复" + (propertyList.size() - number) + "条数据";
            } else {
                errorMsg = "导入成功，共" + number + "条数据";
            }
            result.put("code", 0);
            result.put("message", errorMsg);
            result.put("totalNum", propertyList.size());
            result.put("insertNum", number);
            logger.debug("loadExcelValue success:" + result.toJSONString() + " productInstID:" + productInstID);
        }

        return result;
    }

    //从消息队列接收消息后进行导出数据库操作
    public void batchExport(JSONObject jsonObject) {

        //获取任务ID
        String taskId = jsonObject.getString(MQService.MSG_KEY_MSGID);

        //获取productInstID
        String productInstId = jsonObject.getString(MQService.MSG_KEY_PRODUCTINSTID);

        //设置任务为处理中
        taskStatusService.setTaskStatus(TaskStatusService.TASK_TARGET_PROPERTY,
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
        taskStatusService.setTaskStatus(TaskStatusService.TASK_TARGET_PROPERTY,
                TaskStatusService.TaskTypeEnum.EXPORT,
                productInstId,
                taskId,
                TaskStatusService.TaskStatusEnum.DONE,
                result);
    }

    //读取房产资料并上传到cos,基于注解方式
    private JSONObject writeExcelAndUploadCosNew(String productInstID) {

        String targetFilename = "property_" + productInstID + "-" + new SimpleDateFormat("ddHHmmssS").format(new Date()) + ".xls";
        String cosFolder = "/" + new SimpleDateFormat("yyyyMM").format(new Date()) + "/";
        String cosFilePath = cosFolder + targetFilename;
        String localFilePath = "/tmp/" + targetFilename;

        //获取数据
        PropertyExample propertyExample = new PropertyExample();
        PropertyExample.Criteria criteria = propertyExample.createCriteria();
        criteria.andProductInstIdEqualTo(productInstID);
        OrderByHelper.orderBy(" property_type, zone_id, building_id, unit_id, room_id asc");
        List<Property> propertyList = propertyMapper.selectByExample(propertyExample);

        //不按模板导出excel, 基于注解
        ExcelUtils.getInstance().exportObj2Excel(localFilePath, propertyList, Property.class);

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
            //生成下载url
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

    /*
    //读取房产资料并上传到cos
    private JSONObject writeExcelAndUploadCos(String productInstID, String cosFilePath, String localFilePath) {

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("房产档案");
        sheet.autoSizeColumn(1, true);//自适应列宽度
        createExcelTitle(workbook, sheet);

        //获取数据
        PropertyExample propertyExample = new PropertyExample();
        PropertyExample.Criteria criteria = propertyExample.createCriteria();
        criteria.andProductInstIdEqualTo(productInstID);
        OrderByHelper.orderBy(" property_type, zone_id, building_id, unit_id, room_id asc");
        List<Property> propertyList = propertyMapper.selectByExample(propertyExample);

        //设置日期格式
        //HSSFCellStyle style=workbook.createCellStyle();
        //style.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));

        //新增数据行，并且设置单元格数据
        int rowNum = 1;
        for (Property property : propertyList) {

            HSSFRow row = sheet.createRow(rowNum);
            row.createCell(0).setCellValue(property.getZoneId());
            row.createCell(1).setCellValue(property.getBuildingId());
            row.createCell(2).setCellValue(property.getUnitId());
            row.createCell(3).setCellValue(property.getRoomId());
            row.createCell(4).setCellValue(property.getPropertyArea().toString());
            row.createCell(5).setCellValue((property.getFloorArea() == null)?"" : property.getFloorArea().toString());
            row.createCell(6).setCellValue(property.getHouseType());
            row.createCell(7).setCellValue(getPropertyStatusDesc(property.getStatus()));
            //cell.setCellStyle(style);
            rowNum++;
        }

        try {
            OutputStream out = new FileOutputStream(localFilePath);
            workbook.write(out);
        } catch (Exception e) {
            logger.error("generate local excel failed: " + e.getMessage());
            JSONObject j = new JSONObject();
            j.put("code", 20001);
            j.put("message", "generate local excel failed");
            return j;
        }

        //创建qcloud cos操作Helper对象,并上传文件
        QCloudCosHelper qCloudCosHelper = new QCloudCosHelper(qCloudConfig.getAppId(), qCloudConfig.getSecretId(), qCloudConfig.getSecretKey());
        JSONObject jsonUploadResult =  qCloudCosHelper.uploadFile(qCloudConfig.getCosBucketName(), cosFilePath, localFilePath);
        //JSONObject jsonUploadResult = qCloudCosHelper.uploadBuffer(qCloudConfig.getCosBucketName(), cosFilePath, workbook.getBytes());

        // 关闭释放资源
        qCloudCosHelper.releaseCosClient();

        //删除本地文件
        File localFile = new File(localFilePath);
        localFile.delete();

        return jsonUploadResult;
    }

    //创建excel表头
    private void createExcelTitle(HSSFWorkbook workbook, HSSFSheet sheet) {

        HSSFRow row = sheet.createRow(0);
        //设置列宽，setColumnWidth的第二个参数要乘以256，这个参数的单位是1/256个字符宽度
        sheet.setColumnWidth(2, 12 * 256);
        sheet.setColumnWidth(3, 17 * 256);

        //设置为居中加粗
        HSSFCellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setFont(font);

        HSSFCell cell;
        cell = row.createCell(0);
        cell.setCellValue("分区名称");
        cell.setCellStyle(style);

        cell = row.createCell(1);
        cell.setCellValue("楼号");
        cell.setCellStyle(style);

        cell = row.createCell(2);
        cell.setCellValue("单元(座)");
        cell.setCellStyle(style);

        cell = row.createCell(3);
        cell.setCellValue("房号");
        cell.setCellStyle(style);

        cell = row.createCell(4);
        cell.setCellValue("产权面积");
        cell.setCellStyle(style);

        cell = row.createCell(5);
        cell.setCellValue("套内面积");
        cell.setCellStyle(style);

        cell = row.createCell(6);
        cell.setCellValue("户型");
        cell.setCellStyle(style);

        cell = row.createCell(7);
        cell.setCellValue("房产状态");
        cell.setCellStyle(style);
    }
    */
}
