package com.highplace.biz.pm.service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.highplace.biz.pm.config.QCloudConfig;
import com.highplace.biz.pm.dao.base.PropertyMapper;
import com.highplace.biz.pm.dao.charge.*;
import com.highplace.biz.pm.dao.service.RequestMapper;
import com.highplace.biz.pm.domain.base.Property;
import com.highplace.biz.pm.domain.base.PropertyExample;
import com.highplace.biz.pm.domain.base.Relation;
import com.highplace.biz.pm.domain.charge.*;
import com.highplace.biz.pm.domain.ui.ChargeSearchBean;
import com.highplace.biz.pm.domain.ui.PageBean;
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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.orderbyhelper.OrderByHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class ChargeService {

    public static final Logger logger = LoggerFactory.getLogger(ChargeService.class);

    //写入redis的key前缀, 后面加上productInstId
    public static final String PREFIX_BILL_NAME_KEY = "CHARGE_BILL_TYPE_KEY_";
    public static final String MAP_BILL_ID = "billId";
    public static final String MAP_BILL_NAME = "billName";
    public static final String MAP_FEE_DATA_TYPE = "feeDataType";
    public static final String MAP_CHARGE_ID = "chargeId";
    public static final String MAP_FEE_DATA_TYPE_IS_IMPORT = "isImport";

    //设置流水导入成功的标识
    public static final String PREFIX_WATER_IMPORT_SUCCESS = "WATER_IMPORT_SUCCESS_";
    public static final String WATER_IMPORT_SUCCESS_VALUE = "SUCCESS";

    @Autowired
    private SubjectMapper subjectMapper;

    @Autowired
    private BillMapper billMapper;

    @Autowired
    private BillSubjectRelMapper billSubjectRelMapper;

    @Autowired
    private ChargeMapper chargeMapper;

    @Autowired
    private ChargeWaterMapper chargeWaterMapper;

    @Autowired
    private PropertyMapper propertyMapper;

    @Autowired
    private RequestMapper requestMapper;

    @Autowired
    private ChargeDetailMapper chargeDetailMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private TaskStatusService taskStatusService;

    @Autowired
    private QCloudConfig qCloudConfig;

    //账单类型 ID 和 name 以hash数据结构缓存到redis中
    public void addRedisValue(String productInstId, Bill bill) {

        if (bill.getBillId() != null && StringUtils.isNotEmpty(bill.getBillName())) {
            String redisKey = PREFIX_BILL_NAME_KEY + productInstId;
            redisTemplate.opsForHash().put(redisKey, bill.getBillId(), bill.getBillName());
        }
    }

    //从redis中查询所有账单类型id和名称
    public Map<String, Object> rapidSearchBillType(String productInstId) {

        String redisKey = PREFIX_BILL_NAME_KEY + productInstId;
        Map<Long, String> topLevelDepartmentMap = (Map<Long, String>) redisTemplate.opsForHash().entries(redisKey);

        List<Object> billTypeList = new ArrayList<>();
        Map<String, Object> billTypeMap;
        for (Map.Entry<Long, String> entry : topLevelDepartmentMap.entrySet()) {

            billTypeMap = new HashMap<>();
            billTypeMap.put(MAP_BILL_ID, entry.getKey());
            billTypeMap.put(MAP_BILL_NAME, entry.getValue());
            billTypeList.add(billTypeMap);
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("data", billTypeList);
        return result;
    }

    //查询收费科目列表
    public Map<String, Object> querySubject(String productInstId, PageBean pageBean, boolean noPageSortFlag) {

        SubjectExample example = new SubjectExample();
        SubjectExample.Criteria criteria = example.createCriteria();

        //产品实例ID，必须填入
        criteria.andProductInstIdEqualTo(productInstId);

        //如果noPageSortFlag 不为true
        if (!noPageSortFlag) {
            //设置分页参数
            if (pageBean.getPageNum() != null && pageBean.getPageSize() != null)
                PageHelper.startPage(pageBean.getPageNum(), pageBean.getPageSize());

            //设置排序字段,注意前端传入的是驼峰风格字段名,需要转换成数据库下划线风格字段名
            if (pageBean.getSortField() != null) {
                if (pageBean.getSortType() == null) {
                    OrderByHelper.orderBy(CommonUtils.underscoreString(pageBean.getSortField()) + " asc"); //默认升序
                } else {
                    OrderByHelper.orderBy(CommonUtils.underscoreString(pageBean.getSortField()) + " " + pageBean.getSortType());
                }
            }
        }

        //查询结果
        List<Subject> subjectList = subjectMapper.selectByExampleWithBLOBs(example);

        //总记录数
        long totalCount;

        //判断是否有分页
        if (!noPageSortFlag && pageBean.getPageNum() != null && pageBean.getPageSize() != null) {
            totalCount = ((Page) subjectList).getTotal();
        } else {
            totalCount = subjectList.size();
        }

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("totalCount", totalCount);
        result.put("data", subjectList);
        return result;
    }

    //插入收费科目
    public int insertSubject(String productInstId, Subject subject) {

        //设置产品实例ID
        subject.setProductInstId(productInstId);
        int num = subjectMapper.insertSelective(subject);
        return num;
    }

    //删除收费科目
    public int deleteSubject(String productInstId, Long subjectId) {

        SubjectExample example = new SubjectExample();
        SubjectExample.Criteria criteria = example.createCriteria();
        criteria.andProductInstIdEqualTo(productInstId);
        criteria.andSubjectIdEqualTo(subjectId);
        return subjectMapper.deleteByExample(example);
    }

    //修改收费科目
    public int updateSubject(String productInstId, Subject subject) {

        SubjectExample example = new SubjectExample();
        SubjectExample.Criteria criteria = example.createCriteria();
        //产品实例ID，必须填入
        criteria.andProductInstIdEqualTo(productInstId);
        criteria.andSubjectIdEqualTo(subject.getSubjectId());
        int num = subjectMapper.updateByExampleSelective(subject, example);
        return num;
    }

    //查询账单类型列表
    public Map<String, Object> queryBillType(String productInstId, PageBean pageBean, boolean noPageSortFlag) {

        BillExample example = new BillExample();
        BillExample.Criteria criteria = example.createCriteria();

        //产品实例ID，必须填入
        criteria.andProductInstIdEqualTo(productInstId);

        //如果noPageSortFlag 不为true
        if (!noPageSortFlag) {
            //设置分页参数
            if (pageBean.getPageNum() != null && pageBean.getPageSize() != null)
                PageHelper.startPage(pageBean.getPageNum(), pageBean.getPageSize());

            //设置排序字段,注意前端传入的是驼峰风格字段名,需要转换成数据库下划线风格字段名
            if (pageBean.getSortField() != null) {
                if (pageBean.getSortType() == null) {
                    OrderByHelper.orderBy(CommonUtils.underscoreString(pageBean.getSortField()) + " asc"); //默认升序
                } else {
                    OrderByHelper.orderBy(CommonUtils.underscoreString(pageBean.getSortField()) + " " + pageBean.getSortType());
                }
            }
        }

        //查询结果
        List<Bill> billList = billMapper.selectByExampleWithBLOBsWithRelation(example);

        //总记录数
        long totalCount;

        //判断是否有分页
        if (!noPageSortFlag && pageBean.getPageNum() != null && pageBean.getPageSize() != null) {
            totalCount = ((Page) billList).getTotal();
        } else {
            totalCount = billList.size();
        }

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("totalCount", totalCount);
        result.put("data", billList);
        return result;
    }

    //插入账单类型信息
    @Transactional
    public int insertBillType(String productInstId, Bill bill) {

        //设置产品实例ID
        bill.setProductInstId(productInstId);
        int num = billMapper.insertSelective(bill);
        if (num == 1) {
            //批量插入账单类型和收费科目关系信息
            List<BillSubjectRel> billSubjectRelList = bill.getBillSubjectRelList();
            if (billSubjectRelList != null) {
                for (BillSubjectRel billSubjectRel : billSubjectRelList) {

                    billSubjectRel.setProductInstId(productInstId);
                    billSubjectRel.setBillId(bill.getBillId());
                    billSubjectRelMapper.insertSelective(billSubjectRel);
                }
            }
            //更新cache
            addRedisValue(productInstId, bill);
        }
        return num;
    }

    //修改账单类型信息
    @Transactional
    public int updateBillType(String productInstId, Bill bill) {

        //先更新账单类型表
        BillExample example = new BillExample();
        BillExample.Criteria criteria = example.createCriteria();
        criteria.andBillIdEqualTo(bill.getBillId()); //账单类型ID
        criteria.andProductInstIdEqualTo(productInstId); //产品实例ID，必须填入
        int num = billMapper.updateByExampleSelective(bill, example);

        //如果更新成功,则继续更新账单和收费科目关系
        if (num == 1) {

            //获取提交的账单和收费科目关系列表
            List<BillSubjectRel> billSubjectRelList = bill.getBillSubjectRelList();

            //记录更新前的所有relationID列表
            List<Long> beforeRelationIdList = new ArrayList<>();
            List<Long> afterRelationIdList = new ArrayList<>();

            //遍历relatinoList，进行更新操作
            for (BillSubjectRel relation : billSubjectRelList) {

                //为null 表示新增relation信息
                if (relation.getRelationId() == null) {
                    //为null 表示新增relation信息
                    relation.setProductInstId(productInstId);
                    relation.setBillId(bill.getBillId());
                    billSubjectRelMapper.insertSelective(relation);

                    //将新增的relationId加入到beforeRelationIdList列表中
                    beforeRelationIdList.add(relation.getRelationId());

                } else {
                    //加入到beforeRelationIdList列表中
                    beforeRelationIdList.add(relation.getRelationId());
                }
            }

            //更新后的所有relatinoId列表
            List<BillSubjectRel> billSubjectRelList1 = billSubjectRelMapper.selectByBillIdWithSubjectName(bill.getBillId());
            for (BillSubjectRel relation : billSubjectRelList1) {
                afterRelationIdList.add(relation.getRelationId());
            }

            //求补集，删除afterCarIdList多余的部分
            afterRelationIdList.removeAll(beforeRelationIdList);
            if (afterRelationIdList.size() > 0) {
                BillSubjectRelExample example1 = new BillSubjectRelExample();
                BillSubjectRelExample.Criteria criteria1 = example1.createCriteria();
                criteria1.andProductInstIdEqualTo(productInstId);
                criteria1.andBillIdEqualTo(bill.getBillId());
                criteria1.andRelationIdIn(afterRelationIdList);
                billSubjectRelMapper.deleteByExample(example1);
            }

            //更新cache
            addRedisValue(productInstId, bill);
        }
        return num;
    }

    //删除账单类型信息
    @Transactional
    public int deleteBillType(String productInstId, Long billId) {

        //删除之前需要加入业务逻辑判断,不能随便删除
        //to-do

        //先删除账单类型和收费科目关系
        BillSubjectRelExample billSubjectRelExample = new BillSubjectRelExample();
        BillSubjectRelExample.Criteria criteria1 = billSubjectRelExample.createCriteria();
        criteria1.andProductInstIdEqualTo(productInstId);
        criteria1.andBillIdEqualTo(billId);
        billSubjectRelMapper.deleteByExample(billSubjectRelExample);

        //再删除账单类型
        BillExample billExample = new BillExample();
        BillExample.Criteria criteria = billExample.createCriteria();
        criteria.andProductInstIdEqualTo(productInstId);
        criteria.andBillIdEqualTo(billId);
        return billMapper.deleteByExample(billExample);
    }

    //查询已出账单
    public Map<String, Object> queryCharge(String productInstId, ChargeSearchBean chargeSearchBean, boolean noPageSortFlag) {

        ChargeExample example = new ChargeExample();
        ChargeExample.Criteria criteria = example.createCriteria();

        //chargeId
        if(chargeSearchBean.getChargeId() != null) {
            criteria.andChargeIdEqualTo(chargeSearchBean.getChargeId());
        }

        //产品实例ID，必须填入
        criteria.andProductInstIdEqualTo(productInstId);

        //BillId
        if(chargeSearchBean.getBillId() != null) {
            criteria.andBillIdEqualTo(chargeSearchBean.getBillId());
        }

        //BillPeriod
        if(StringUtils.isNotEmpty(chargeSearchBean.getBillPeriod())) {
            criteria.andBillPeriodEqualTo(chargeSearchBean.getBillPeriod());
        }

        //Status
        if(chargeSearchBean.getStatus() != null) {
            criteria.andStatusEqualTo(chargeSearchBean.getStatus());
        }

        //如果noPageSortFlag 不为true
        if (!noPageSortFlag) {
            //设置分页参数
            if (chargeSearchBean.getPageNum() != null && chargeSearchBean.getPageSize() != null)
                PageHelper.startPage(chargeSearchBean.getPageNum(), chargeSearchBean.getPageSize());

            //设置排序字段,注意前端传入的是驼峰风格字段名,需要转换成数据库下划线风格字段名
            if (chargeSearchBean.getSortField() != null) {
                if (chargeSearchBean.getSortType() == null) {
                    OrderByHelper.orderBy(CommonUtils.underscoreString(chargeSearchBean.getSortField()) + " asc"); //默认升序
                } else {
                    OrderByHelper.orderBy(CommonUtils.underscoreString(chargeSearchBean.getSortField()) + " " + chargeSearchBean.getSortType());
                }
            }
        }

        //查询结果
        List<Charge> chargeList = chargeMapper.selectByExampleWithBLOBs(example);

        //总记录数
        long totalCount;

        //判断是否有分页
        if (!noPageSortFlag && chargeSearchBean.getPageNum() != null && chargeSearchBean.getPageSize() != null) {
            totalCount = ((Page) chargeList).getTotal();
        } else {
            totalCount = chargeList.size();
        }

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("totalCount", totalCount);
        result.put("data", chargeList);
        return result;
    }

    //创建出账单
    public Charge insertCharge(String productInstId, Charge charge){

        //设置产品实例ID
        charge.setProductInstId(productInstId);
        chargeMapper.insertSelective(charge);

        //设置账单类别相关信息
        charge.setBill(billMapper.selectByPrimaryKey(charge.getBillId()));

        /*
        //设置收费科目相关信息
        List<Subject> subjectList = new ArrayList<>();
        Subject subject;
        List<BillSubjectRel> billSubjectRelList = billSubjectRelMapper.selectByBillId(charge.getBillId());
        for (BillSubjectRel billSubjectRel : billSubjectRelList) {
            subject = subjectMapper.selectByPrimaryKey(billSubjectRel.getSubjectId());
            subjectList.add(subject);
        }
        charge.setSubjectList(subjectList);
        */
        return charge;
    }

    //获取出账单对应的仪表数据导入信息
    public Map<String, Object> getChargeImportInfo(String productInstId, Long chargeId) throws Exception{

        //通过chargeId查找Charge信息
        ChargeExample example = new ChargeExample();
        ChargeExample.Criteria criteria = example.createCriteria();
        criteria.andChargeIdEqualTo(chargeId);
        criteria.andProductInstIdEqualTo(productInstId);
        List<Charge> chargeList = chargeMapper.selectByExample(example);
        if(chargeList == null || chargeList.size()==0) throw new Exception("chargeId is null");
        if(chargeList.get(0).getStatus() != 0) throw new Exception("出账中状态才能导入仪表数据");

        //查找对应收费科目信息,将需要仪表导入的记录到set中
        Set<Integer> feeDataTypeSet = new HashSet<>();
        Subject subject;
        List<BillSubjectRel> billSubjectRelList = billSubjectRelMapper.selectByBillId(chargeList.get(0).getBillId());
        for (BillSubjectRel billSubjectRel : billSubjectRelList) {
            subject = subjectMapper.selectByPrimaryKey(billSubjectRel.getSubjectId());
            if(subject.getFeeDataType() != null && subject.getFeeDataType() >=1 && subject.getFeeDataType() <=5 ) {
                feeDataTypeSet.add(subject.getFeeDataType());
            }
        }

        //查看feeDataType是否已经导入
        boolean isImport = false;
        String redisKey;
        List<Object> dataList = new LinkedList<>();
        Map<String, Object> feeDataTypeMap ;
        for(Integer feeDataType : feeDataTypeSet) {
            redisKey = PREFIX_WATER_IMPORT_SUCCESS + productInstId + "_" + chargeId + "_" + feeDataType;
            if(stringRedisTemplate.hasKey(redisKey)) {
                isImport = true;
            }
            feeDataTypeMap = new HashMap<>();
            feeDataTypeMap.put(MAP_FEE_DATA_TYPE, feeDataType);
            feeDataTypeMap.put(MAP_FEE_DATA_TYPE_IS_IMPORT, isImport);
            dataList.add(feeDataTypeMap);
        }

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("data", dataList);
        return result;
    }

    //修改出账单信息
    public int updateCharge(String productInstId, Charge charge) {

        //状态:0:出账中 1:仪表数据导入完成 2:出账完成 3:收费中 4:收费完成'
        if(charge.getStatus() != null){
            if(charge.getStatus() == 1) {  //如果修改状态为1,需要检查所有的仪表数据是否可以导入成功
                Long billId = charge.getBillId();
                //如果没有传入billId,先获取billId
                if (billId == null) {
                    Charge newCharge = chargeMapper.selectByPrimaryKey(charge.getChargeId());
                    if (newCharge == null) return -1;
                    billId = newCharge.getBillId();
                }

                //查找收费科目,并检查是否都导入仪表数据成功
                Subject subject;
                String redisKey;
                boolean checkFlag = true;
                List<BillSubjectRel> billSubjectRelList = billSubjectRelMapper.selectByBillId(billId);
                for (BillSubjectRel billSubjectRel : billSubjectRelList) {
                    subject = subjectMapper.selectByPrimaryKey(billSubjectRel.getSubjectId());
                    //用量关联数据标识,若null,则表示不关联, 0:产权面积 1:水表 2:电表 3:燃气表 4:暖气表 5:空调表 6:服务工单
                    if (subject.getFeeDataType() != null && subject.getFeeDataType() >= 1 && subject.getFeeDataType() <= 5) {
                        redisKey = PREFIX_WATER_IMPORT_SUCCESS + productInstId + "_" + charge.getChargeId() + "_" + subject.getFeeDataType();
                        if (!stringRedisTemplate.hasKey(redisKey)) {
                            checkFlag = false;
                            break;
                        }
                    }
                }
                if (!checkFlag) return -2;

            } else if(charge.getStatus() == 3) {   //如果修改状态为3,需要检查当前状态是否为2

                Charge newCharge = chargeMapper.selectByPrimaryKey(charge.getChargeId());
                if(newCharge.getStatus() != 2) return -3;
            } else {
                return -4;
            }
        }

        //更新charge信息
        ChargeExample example = new ChargeExample();
        ChargeExample.Criteria criteria = example.createCriteria();
        criteria.andChargeIdEqualTo(charge.getChargeId());
        criteria.andProductInstIdEqualTo(productInstId); //产品实例ID，必须填入
        return chargeMapper.updateByExampleSelective(charge, example);
    }

    //删除出账单
    @Transactional
    public int deleteCharge(String productInstId, Long chargeId) {

        //删除之前需要加入业务逻辑判断,不能随便删除
        ChargeExample chargeExample = new ChargeExample();
        ChargeExample.Criteria criteria = chargeExample.createCriteria();
        criteria.andProductInstIdEqualTo(productInstId);
        criteria.andChargeIdEqualTo(chargeId);
        List<Charge> chargeList = chargeMapper.selectByExample(chargeExample);
        if(chargeList == null || chargeList.size() != 1) {  //没有对应的chargeId
            return -1;
        } else if (chargeList.get(0).getStatus() != 0 || chargeList.get(0).getStatus() != 2) {    //非出账中和出账完成状态,不能删除
            return -2;
        }

        //先删除出账单对应的仪表用量信息表
        ChargeWaterExample chargeWaterExample = new ChargeWaterExample();
        ChargeWaterExample.Criteria criteria1 = chargeWaterExample.createCriteria();
        criteria1.andProductInstIdEqualTo(productInstId);
        criteria1.andChargeIdEqualTo(chargeId);
        chargeWaterMapper.deleteByExample(chargeWaterExample);

        //再删除出账单
        return chargeMapper.deleteByExample(chargeExample);
    }

    //批量导入仪表用量信息
    public void batchImport(JSONObject jsonObject) {

        //获取任务ID
        String taskId = jsonObject.getString(MQService.MSG_KEY_MSGID);

        //获取productInstID
        String productInstId = jsonObject.getString(MQService.MSG_KEY_PRODUCTINSTID);

        //获取文件在cos上的路径
        String cosFilePath = jsonObject.getString(MQService.MSG_KEY_FILEURL);

        //获取chargeId和feeDataType
        Long chargeId = jsonObject.getLong(ChargeService.MAP_CHARGE_ID);
        Integer feeDataType = jsonObject.getInteger(ChargeService.MAP_FEE_DATA_TYPE);

        //设置本地存储路径
        String localFilePath = "/tmp/" + cosFilePath;

        //设置任务状态为处理中
        taskStatusService.setTaskStatus(TaskStatusService.TaskTargetEnum.CHARGE,
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
            JSONObject jsonResult = readExcel(productInstId, chargeId, feeDataType, localFilePath);
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
        taskStatusService.setTaskStatus(TaskStatusService.TaskTargetEnum.CHARGE,
                TaskStatusService.TaskTypeEnum.IMPORT,
                productInstId,
                taskId,
                TaskStatusService.TaskStatusEnum.DONE,
                result);

        // 关闭释放资源
        qCloudCosHelper.releaseCosClient();

        //如果导入成功,在cache中写入标识,有效期1天
        if( (int)result.get(TaskStatusService.TASK_RESULT_CODE_KEY) == 0){
            String redisKey = PREFIX_WATER_IMPORT_SUCCESS + productInstId + "_" + chargeId + "_" + feeDataType;
            stringRedisTemplate.opsForValue().set(redisKey, WATER_IMPORT_SUCCESS_VALUE, 1, TimeUnit.DAYS);
        }
    }

    //读取Excel文件
    public JSONObject readExcel(String productInstID, Long chargeId, Integer feeDataType, String localFilePath) {

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
            return loadExcelValue(productInstID, chargeId, feeDataType, wb);

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
    protected JSONObject loadExcelValue(String productInstID, Long chargeId, Integer feeDataType, Workbook wb) {

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

        List<ChargeWater> chargeWaterList = new ArrayList<>();
        ChargeWater tempChargeWater;

        boolean errorFlag = false; //只要出现错误,都跳出循环

        //循环Excel行数,从第二行开始。标题不入库
        for (int r = 1; r < totalRows; r++) {

            Row row = sheet.getRow(r);
            if (row == null) {
                errorMsg += "第" + (r + 1) + "行数据有问题, 请仔细检查.";
                errorFlag = true;
                break;  //跳出循环
            }

            tempChargeWater = new ChargeWater();

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
                            tempChargeWater.setPropertyName(cellValue);
                            Property property = propertyMapper.selectByPropertyName(productInstID, cellValue);
                            if(property == null) {
                                errorMsg += "第" + (r + 1) + "行" + (c + 1) + "列房产记录不存在, 请先创建房产档案;";
                                errorFlag = true;
                            } else {
                                //设置房产ID
                                tempChargeWater.setPropertyId(property.getPropertyId());
                            }
                        } else {
                            errorFlag = true;
                            errorMsg += "第" + (r + 1) + "行" + (c + 1) + "列房产名不能为空, 请仔细检查;";
                        }
                        break;

                    case 1:  //开始用量(必填)
                        if (StringUtils.isNotEmpty(cellValue)) {
                            try {
                                tempChargeWater.setBeginUsage(Double.parseDouble(cellValue));
                            } catch (NumberFormatException e) {
                                errorMsg += "第" + (r + 1) + "行" + (c + 1) + "列数据有问题, 请仔细检查;";
                                errorFlag = true;
                                logger.error("read BeginUsage failed: cellvalue:" + cellValue + " err:" + e.getMessage());
                                e.printStackTrace();
                            }
                        } else {
                            errorMsg += "第" + (r + 1) + "行" + (c + 1) + "列数据有问题, 请仔细检查;";
                            errorFlag = true;
                        }
                        break;

                    case 2:  //结束用量(必填)
                        if (StringUtils.isNotEmpty(cellValue)) {
                            try {
                                tempChargeWater.setEndUsage(Double.parseDouble(cellValue));
                            } catch (NumberFormatException e) {
                                errorMsg += "第" + (r + 1) + "行" + (c + 1) + "列数据有问题, 请仔细检查;";
                                errorFlag = true;
                                logger.error("read EndUsage failed: cellvalue:" + cellValue + " err:" + e.getMessage());
                                e.printStackTrace();
                            }
                        } else {
                            errorMsg += "第" + (r + 1) + "行" + (c + 1) + "列数据有问题, 请仔细检查;";
                            errorFlag = true;
                        }
                        break;

                    case 3:  //开始时间
                        if (StringUtils.isNotEmpty(cellValue)) {
                            try {
                                tempChargeWater.setBeginDate(CommonUtils.getDate(cellValue, CommonUtils.FORMAT_DAY));
                            } catch (ParseException e) {
                                logger.error("SimpleDateFormat(yyyy-MM-dd) parse error, value:" + cellValue + ",error msg:" + e.getMessage());
                                e.printStackTrace();
                                errorMsg += "第" + (r + 1) + "行" + (c + 1) + "列日期格式应为yyyy-MM-dd,请仔细检查;";
                                errorFlag = true;
                            }
                        }
                        break;

                    case 4:  //结束时间
                        if (StringUtils.isNotEmpty(cellValue)) {
                            try {
                                tempChargeWater.setEndDate(CommonUtils.getDate(cellValue, CommonUtils.FORMAT_DAY));
                            } catch (ParseException e) {
                                logger.error("SimpleDateFormat(yyyy-MM-dd) parse error, value:" + cellValue + ",error msg:" + e.getMessage());
                                e.printStackTrace();
                                errorMsg += "第" + (r + 1) + "行" + (c + 1) + "列日期格式应为yyyy-MM-dd,请仔细检查;";
                                errorFlag = true;
                            }
                        }
                        break;

                    default:
                        break;
                }
            }
            if (errorFlag) {
                break;  //只要出现错误，跳出循环
            }
            chargeWaterList.add(tempChargeWater);
        }

        if (errorFlag) {
            result.put("code", 103);
            result.put("message", errorMsg);
            logger.error("loadExcelValue error:" + result.toJSONString() + " productInstID:" + productInstID);
        } else {
            int number = 0;
            ChargeWaterExample example;
            for (ChargeWater chargeWater : chargeWaterList) {

                //重要,先写入productInstId/chargeId/feeDataType
                chargeWater.setProductInstId(productInstID);
                chargeWater.setChargeId(chargeId);
                chargeWater.setFeeDataType(feeDataType);

                //查找记录是否存在
                ChargeWaterExample chargeWaterExample = new ChargeWaterExample();
                ChargeWaterExample.Criteria criteria = chargeWaterExample.createCriteria();
                criteria.andProductInstIdEqualTo(chargeWater.getProductInstId());
                criteria.andChargeIdEqualTo(chargeWater.getChargeId());
                criteria.andPropertyIdEqualTo(chargeWater.getPropertyId());
                criteria.andFeeDataTypeEqualTo(chargeWater.getFeeDataType());
                List<ChargeWater> find = chargeWaterMapper.selectByExample(chargeWaterExample);

                if (find.size() == 0) {  //不存在记录,直接insert
                    number += chargeWaterMapper.insertSelective(chargeWater);
                } else if (find.size() == 1) {  //存在记录,更新
                    number += chargeWaterMapper.updateByExample(chargeWater, chargeWaterExample);
                }
            }
            if (number < chargeWaterList.size()) {
                errorMsg = "导入成功，共" + number + "条数据, 重复" + (chargeWaterList.size() - number) + "条数据";
            } else {
                errorMsg = "导入成功，共" + number + "条数据";
            }
            result.put("code", 0);
            result.put("message", errorMsg);
            result.put("totalNum", chargeWaterList.size());
            result.put("insertNum", number);
            logger.debug("loadExcelValue success:" + result.toJSONString() + " productInstID:" + productInstID);
        }

        return result;
    }

    //出账计算
    public void chargeCalculate(Long chargeId) throws Exception {

        //判断chargeId是否存在，status是否为1，获取chargeId相关信息
        Charge charge = chargeMapper.selectByPrimaryKey(chargeId);
        if(charge == null) throw new Exception("chargeId is null");
        if(charge.getStatus() != 1) throw new Exception("charge status isn't 1");

        //获取账单对应的收费科目信息
        List<Subject> subjectList = new ArrayList<>();
        Subject tmpSubject;
        List<BillSubjectRel> billSubjectRelList = billSubjectRelMapper.selectByBillId(charge.getBillId());
        for (BillSubjectRel billSubjectRel : billSubjectRelList) {
            tmpSubject = subjectMapper.selectByPrimaryKey(billSubjectRel.getSubjectId());
            subjectList.add(tmpSubject);
        }
        charge.setSubjectList(subjectList);

        //遍历房产信息表,按房产进行费用计算
        PropertyExample example = new PropertyExample();
        PropertyExample.Criteria criteria = example.createCriteria();
        criteria.andProductInstIdEqualTo(charge.getProductInstId());
        List<Property> propertyList = propertyMapper.selectByExample(example);

        double totalAmount = 0;
        ChargeDetail chargeDetail;
        for(Property property : propertyList) {

            //按房产计算总金额
            double propertyAmount = 0;
            for(Subject subject : subjectList) {

                //用量关联数据标识,若null,则表示不关联, 0:产权面积 1:水表 2:电表 3:燃气表 4:暖气表 5:空调表 6:服务工单
                if(subject.getFeeDataType() == null) {   //不关联,如停车费,直接获取feeLevelOne金额

                    propertyAmount = CommonUtils.add(propertyAmount,subject.getFeeLevelOne());

                } else if(subject.getFeeDataType() == 6) {  //服务工单收费,从服务请求表中按周期计算总额

                    Double amount = requestMapper.sumDealFeeByPropertyAndMonth(property.getPropertyId().toString(), charge.getBillPeriod());
                    if(amount != null) propertyAmount = CommonUtils.add(propertyAmount,amount);

                } else if(subject.getFeeDataType() == 0) {  //按产权面积收费，如管理费，用feeLevelOne乘以产权面积

                    propertyAmount = CommonUtils.add(propertyAmount,CommonUtils.mul(subject.getFeeLevelOne(), property.getPropertyArea()));

                } else {   //其他的,从各类仪表数据中进行费用计算

                    //获取仪表数据
                    ChargeWater chargeWater = chargeWaterMapper.selectByPrimaryKey(charge.getProductInstId(), chargeId, property.getPropertyId(), subject.getFeeDataType());
                    //计算用量
                    double usage = CommonUtils.sub(chargeWater.getEndUsage(), chargeWater.getBeginUsage());
                    double amt = 0;
                    if( usage > 0 ) {
                        double amtLevelOne = 0;
                        double amtLevelTwo = 0;
                        double amtLevelThree = 0;

                        if (subject.getLevelOneToplimit() == null || usage <= subject.getLevelOneToplimit() ) {
                            amt = CommonUtils.mul(usage, subject.getFeeLevelOne());
                        } else {
                            amtLevelOne = CommonUtils.mul(subject.getLevelOneToplimit(), subject.getFeeLevelOne());
                            if(subject.getLevelTwoToplimit() == null || usage<=subject.getLevelTwoToplimit()) {
                                amtLevelTwo = CommonUtils.mul(CommonUtils.sub(usage, subject.getLevelOneToplimit()), subject.getFeeLevelTwo());
                            } else {
                                amtLevelTwo = CommonUtils.mul(CommonUtils.sub(subject.getLevelTwoToplimit(), subject.getLevelOneToplimit()), subject.getFeeLevelTwo());
                                amtLevelThree = CommonUtils.mul(CommonUtils.sub(usage, subject.getLevelTwoToplimit()), subject.getFeeLevelThree());
                            }
                        }
                        amt = CommonUtils.add(amt, amtLevelOne);
                        amt = CommonUtils.add(amt, amtLevelTwo);
                        amt = CommonUtils.add(amt, amtLevelThree);
                    }
                    propertyAmount = CommonUtils.add(propertyAmount,amt);
                }
            }

            //写入出账明细表(按房产)
            chargeDetail = new ChargeDetail();
            chargeDetail.setProductInstId(charge.getProductInstId());
            chargeDetail.setChargeId(charge.getChargeId());
            chargeDetail.setPropertyId(property.getPropertyId());
            chargeDetail.setAmount(propertyAmount);
            chargeDetail.setPayStatus(0); //状态:0:收费中 1:欠费 2:已缴费
            chargeDetail.setPayType(0);   //缴费方式:0:银行托收 1:微信缴费
            chargeDetailMapper.insertSelective(chargeDetail);

            //计算总账单费用
            totalAmount = CommonUtils.add(totalAmount, propertyAmount);
        }

        //更新出账信息总表,修改状态并设置总出账金额
        Charge newCharge = new Charge();
        newCharge.setChargeId(charge.getChargeId());
        newCharge.setStatus(2); //修改状态为2:出账完成
        newCharge.setTotalAmount(totalAmount); //设置总出账金额
        updateCharge(charge.getProductInstId(), newCharge);
    }


}
