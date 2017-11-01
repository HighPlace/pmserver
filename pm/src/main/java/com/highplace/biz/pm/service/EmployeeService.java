package com.highplace.biz.pm.service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.highplace.biz.pm.config.QCloudConfig;
import com.highplace.biz.pm.dao.org.DepartmentMapper;
import com.highplace.biz.pm.dao.org.EmployeeMapper;
import com.highplace.biz.pm.domain.org.Department;
import com.highplace.biz.pm.domain.org.DepartmentExample;
import com.highplace.biz.pm.domain.org.Employee;
import com.highplace.biz.pm.domain.org.EmployeeExample;
import com.highplace.biz.pm.domain.system.Account;
import com.highplace.biz.pm.domain.ui.EmployeeSearchBean;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

@Service
public class EmployeeService {

    public static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    //写入redis的key前缀, 后面加上productInstId
    public static final String PREFIX_EMPLOYEE_POSITION_KEY = "EMPLOYEE_POSITION_KEY_";
    public static final String PREFIX_EMPLOYEE_NAME_KEY = "EMPLOYEE_NAME_KEY_";
    public static final String PREFIX_EMPLOYEE_PHONE_KEY = "EMPLOYEE_PHONE_KEY_";

    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private DepartmentMapper departmentMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private TaskStatusService taskStatusService;
    @Autowired
    private QCloudConfig qCloudConfig;
    @Autowired
    private AccountService accountService;

    //position/employeeName/phone以set数据结构缓存到redis中
    public void addRedisValue(String productInstId, Employee employee) {
        if (productInstId == null) return;

        if (StringUtils.isNotEmpty(employee.getPosition())) {
            stringRedisTemplate.opsForSet().add(PREFIX_EMPLOYEE_POSITION_KEY + productInstId, employee.getPosition());

            //如果部门id不为空，还加上部门ID对应的职位
            if (employee.getDeptId() != null)
                stringRedisTemplate.opsForSet().add(PREFIX_EMPLOYEE_POSITION_KEY + productInstId + "_" + employee.getDeptId(), employee.getPosition());
        }

        if (StringUtils.isNotEmpty(employee.getEmployeeName()))
            stringRedisTemplate.opsForSet().add(PREFIX_EMPLOYEE_NAME_KEY + productInstId, employee.getEmployeeName());

        if (StringUtils.isNotEmpty(employee.getPhone()))
            stringRedisTemplate.opsForSet().add(PREFIX_EMPLOYEE_PHONE_KEY + productInstId, employee.getPhone());
    }

    //从redis中查询position/employeeName/phone列表，用于前端在检索时快速提示(模糊查询)
    public Map<String, Object> rapidSearch(String productInstId, String entity, String searchValue, Long deptId) {

        String redisKey;
        //对于position,如果传入了deptId，则查对应deptId下的position
        if (entity.equals("position")) {

            redisKey = PREFIX_EMPLOYEE_POSITION_KEY + productInstId;
            if (deptId != null)
                redisKey = PREFIX_EMPLOYEE_POSITION_KEY + productInstId + "_" + deptId;

        } else if (entity.equals("name")) {

            redisKey = PREFIX_EMPLOYEE_NAME_KEY + productInstId;

        } else if (entity.equals("phone")) {

            redisKey = PREFIX_EMPLOYEE_PHONE_KEY + productInstId;

        } else {
            return null;
        }

        Map<String, Object> result = new HashMap<String, Object>();

        Set<String> sEntity = stringRedisTemplate.opsForSet().members(redisKey);
        if (sEntity == null) {

            result.put("data", null);
        } else {

            //对于position,如果传入了deptId，则返回该deptId下的全量position列表
            if (entity.equals("position") && (deptId != null)) {
                result.put("data", sEntity);

            } else {
                List<String> dataList = new ArrayList();
                Pattern pattern = Pattern.compile(searchValue, Pattern.CASE_INSENSITIVE); //大小写不敏感
                int i = 0;
                for (String entityValue : sEntity) {
                    i++;
                    if (pattern.matcher(entityValue).find()) dataList.add(entityValue);  //find()模糊匹配  matches()精确匹配
                    if (i >= 10) break; //匹配到超过10条记录，退出
                }
                result.put("data", dataList);
            }
        }
        return result;
    }

    //查询员工信息列表
    public Map<String, Object> query(String productInstId, EmployeeSearchBean searchBean, boolean noPageSortFlag, boolean onlySysUserFlag) {

        EmployeeExample example = new EmployeeExample();
        EmployeeExample.Criteria criteria = example.createCriteria();

        //产品实例ID，必须填入
        criteria.andProductInstIdEqualTo(productInstId);

        //查询条件判断
        if (searchBean.getDeptId() != null)
            criteria.andDeptIdEqualTo(searchBean.getDeptId());

        if (StringUtils.isNotEmpty(searchBean.getPosition()))
            criteria.andPositionLike("%" + searchBean.getPosition() + "%"); //模糊查询

        if (StringUtils.isNotEmpty(searchBean.getEmployeeName()))
            criteria.andEmployeeNameLike("%" + searchBean.getEmployeeName() + "%"); //模糊查询

        if (StringUtils.isNotEmpty(searchBean.getPhone()))
            criteria.andPhoneLike("%" + searchBean.getPhone() + "%"); //模糊查询

        if (searchBean.getStatus() != null)
            criteria.andStatusEqualTo(searchBean.getStatus());

        if (StringUtils.isNotEmpty(searchBean.getSysUsername())) {
            criteria.andSysUsernameLike("%" + searchBean.getSysUsername() + "%"); //模糊查询
        }else {
            if (onlySysUserFlag) criteria.andSysUsernameIsNotNull();  //没有输入账号查询的话,从员工表中搜索非null的记录
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
        List<Employee> employeeList = employeeMapper.selectByExampleWithBLOBsWithDeptName(example);

        //总记录数
        long totalCount;

        //判断是否有分页
        if (!noPageSortFlag && searchBean.getPageNum() != null && searchBean.getPageSize() != null) {
            totalCount = ((Page) employeeList).getTotal();
        } else {
            totalCount = employeeList.size();
        }

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("totalCount", totalCount);
        result.put("data", employeeList);
        return result;
    }

    //插入员工信息
    public int insert(String productInstId, Employee employee) {

        //设置产品实例ID
        employee.setProductInstId(productInstId);

        //必须要有部门id
        if (employee.getDeptId() == 0) return 0;

        //查询部门ID是否存在
        DepartmentExample departmentExample = new DepartmentExample();
        DepartmentExample.Criteria dCriteria = departmentExample.createCriteria();
        dCriteria.andProductInstIdEqualTo(productInstId);
        dCriteria.andDeptIdEqualTo(employee.getDeptId());
        List<Department> departmentList = departmentMapper.selectByExample(departmentExample);

        //没有deptId部门存在
        if (departmentList.size() == 0) return -1;

        //有deptId部门存在,插入员工记录
        int num = employeeMapper.insertSelective(employee);
        if (num == 1) {
            //更新redis
            addRedisValue(productInstId, employee);
        }
        return num;
    }

    //删除员工信息
    public int delete(String productInstId, Long employeeId) {

        Employee employee = employeeMapper.selectByPrimaryKey(employeeId);
        //员工状态: 0:在岗 1:不在岗 2:离职, 只有离职的员工才能删除
        if ((employee != null) && (employee.getStatus() != 2)) return -1;

        //删除员工
        EmployeeExample employeeExample = new EmployeeExample();
        EmployeeExample.Criteria criteria = employeeExample.createCriteria();
        criteria.andProductInstIdEqualTo(productInstId);
        criteria.andEmployeeIdEqualTo(employeeId);
        return employeeMapper.deleteByExample(employeeExample);
    }

    //修改员工信息
    public int update(String productInstId, Employee employee) {

        //如果有传入deptId,要查看deptid是否存在
        if (employee.getDeptId() != null) {

            DepartmentExample departmentExample = new DepartmentExample();
            DepartmentExample.Criteria dCriteria = departmentExample.createCriteria();
            dCriteria.andProductInstIdEqualTo(productInstId);
            dCriteria.andDeptIdEqualTo(employee.getDeptId());
            List<Department> departmentList = departmentMapper.selectByExample(departmentExample);
            if (departmentList.size() == 0) {
                //没有deptId部门存在
                return -1;
            }
        }

        EmployeeExample example = new EmployeeExample();
        EmployeeExample.Criteria criteria = example.createCriteria();
        //产品实例ID，必须填入
        criteria.andProductInstIdEqualTo(productInstId);
        criteria.andEmployeeIdEqualTo(employee.getEmployeeId());
        int num = employeeMapper.updateByExampleSelective(employee, example);
        if (num == 1) {
            //更新redis
            addRedisValue(productInstId, employee);

            //将有改动的电话和邮箱同步到系统账号信息中
            if(StringUtils.isNotEmpty(employee.getSysUsername())){
                if(StringUtils.isNotEmpty(employee.getEmail()) ||
                        StringUtils.isNotEmpty(employee.getPhone())) {
                    Account account = new Account();
                    account.setUsername(employee.getSysUsername());;
                    account.setMobileNo(employee.getPhone());
                    account.setEmail(employee.getEmail());
                    accountService.update(productInstId, account);
                }
            }
        }
        return num;
    }

    //批量导入员工信息
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
        taskStatusService.setTaskStatus(TaskStatusService.TaskTargetEnum.EMPLOYEE,
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
        taskStatusService.setTaskStatus(TaskStatusService.TaskTargetEnum.EMPLOYEE,
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

        List<Employee> employeeList = new ArrayList<>();
        Employee tempEmployee;

        boolean errorFlag = false; //只要出现错误,都跳出循环

        //循环Excel行数,从第二行开始。标题不入库
        for (int r = 1; r < totalRows; r++) {

            Row row = sheet.getRow(r);
            if (row == null) {
                errorMsg += "第" + (r + 1) + "行数据有问题, 请仔细检查.";
                errorFlag = true;
                break;  //跳出循环
            }

            tempEmployee = new Employee();

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
                    case 0:  //title="员工姓名",order=1 (必填)
                        if (StringUtils.isNotEmpty(cellValue)) {
                            tempEmployee.setEmployeeName(cellValue);
                        } else {
                            errorFlag = true;
                            errorMsg += "第" + (r + 1) + "行" + (c + 1) + "列员工姓名不能为空, 请仔细检查;";
                        }
                        break;

                    case 1:  //title="部门",order=2  (必填)
                        if (StringUtils.isNotEmpty(cellValue)) {
                            tempEmployee.setDeptName(cellValue);

                            //根据部门名查询部门ID
                            Long deptId = departmentMapper.selectDeptIdByDeptName(productInstID, cellValue);
                            if(deptId == null ) {
                                errorFlag = true;
                                errorMsg += "第" + (r + 1) + "行" + (c + 1) + "列部门名不存在, 请仔细检查;";
                            } else {
                                tempEmployee.setDeptId(deptId);
                            }

                        } else {
                            errorMsg += "第" + (r + 1) + "行" + (c + 1) + "列部门不能为空,请仔细检查;";
                            errorFlag = true;
                        }
                        break;

                    case 2:  //title="岗位",order=3)
                        if (StringUtils.isNotEmpty(cellValue)) {
                            tempEmployee.setPosition(cellValue);
                        }
                        break;

                    case 3:  //title="联系电话",order=4 (必填)
                        if (StringUtils.isNotEmpty(cellValue)) {
                            tempEmployee.setPhone(cellValue);
                        } else {
                            errorMsg += "第" + (r + 1) + "行" + (c + 1) + "列联系电话不能为空,请仔细检查;";
                            errorFlag = true;
                        }
                        break;

                    case 4:  //title="备用电话",order=5)
                        if (StringUtils.isNotEmpty(cellValue)) {
                            tempEmployee.setBackupPhone1(cellValue);
                        }
                        break;

                    case 5:  //title="工号",order=6)
                        if (StringUtils.isNotEmpty(cellValue)) {
                            tempEmployee.setEmployeeCode(cellValue);
                        } else {
                            tempEmployee.setEmployeeCode(""); //工号是联合主键
                        }
                        break;

                    case 6:  //title="证件类型",order=7
                        if (StringUtils.isNotEmpty(cellValue)) {
                            tempEmployee.setIdentityType(Employee.transferDescToIdentityType(cellValue));
                        }
                        break;

                    case 7:  //title="证件号码",order=8
                        if (StringUtils.isNotEmpty(cellValue)) {
                            tempEmployee.setIdentityNo(cellValue);
                        }
                        break;

                    case 8:  //title="性别",order=9
                        if (StringUtils.isNotEmpty(cellValue)) {
                            tempEmployee.setGender(cellValue);
                        }
                        break;

                    case 9:  //title="入职日期",order=10
                        if (StringUtils.isNotEmpty(cellValue)) {
                            try {
                                tempEmployee.setEntryDate(CommonUtils.getDate(cellValue, CommonUtils.FORMAT_DAY));
                            } catch (ParseException e) {
                                logger.error("SimpleDateFormat(yyyy-MM-dd) parse error, value:" + cellValue + ",error msg:" + e.getMessage());
                                e.printStackTrace();
                                errorMsg += "第" + (r + 1) + "行" + (c + 1) + "列日期格式应为yyyy-MM-dd,请仔细检查;";
                                errorFlag = true;
                            }
                        }
                        break;

                    case 10:  //title="状态",order=11
                        if (StringUtils.isNotEmpty(cellValue)) {
                            tempEmployee.setStatus(Employee.transferDescToStatus(cellValue));
                        }
                        break;

                    case 11:  //title="电子邮箱",order=12)
                        if (StringUtils.isNotEmpty(cellValue)) {
                            tempEmployee.setEmail(cellValue);
                        }
                        break;

                    default:
                        break;
                }
            }
            if (errorFlag) {
                break;  //只要出现错误，跳出循环
            }
            employeeList.add(tempEmployee);
        }

        if (errorFlag) {
            result.put("code", 103);
            result.put("message", errorMsg);
            logger.error("loadExcelValue error:" + result.toJSONString() + " productInstID:" + productInstID);
        } else {
            int number = 0;

            EmployeeExample example;
            for (Employee employee : employeeList) {

                //重要,先写入productInstId
                employee.setProductInstId(productInstID);

                //根据组合主键查询是否存在记录
                example = new EmployeeExample();
                EmployeeExample.Criteria criteria = example.createCriteria();
                criteria.andProductInstIdEqualTo(employee.getProductInstId());
                criteria.andDeptIdEqualTo(employee.getDeptId());
                criteria.andEmployeeNameEqualTo(employee.getEmployeeName());
                //criteria.andEmployeeCodeEqualTo(employee.getEmployeeCode());

                List<Employee> find = employeeMapper.selectByExample(example);
                if (find.size() == 0) {  //不存在记录,直接insert
                    number += insert(productInstID, employee);

                } else { //存在记录,进行update
                    number += update(productInstID, employee);
                }
            }
            if (number < employeeList.size()) {
                errorMsg = "导入成功，共" + number + "条数据, 重复" + (employeeList.size() - number) + "条数据";
            } else {
                errorMsg = "导入成功，共" + number + "条数据";
            }
            result.put("code", 0);
            result.put("message", errorMsg);
            result.put("totalNum", employeeList.size());
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
        taskStatusService.setTaskStatus(TaskStatusService.TaskTargetEnum.EMPLOYEE,
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
        taskStatusService.setTaskStatus(TaskStatusService.TaskTargetEnum.EMPLOYEE,
                TaskStatusService.TaskTypeEnum.EXPORT,
                productInstId,
                taskId,
                TaskStatusService.TaskStatusEnum.DONE,
                result);
    }

    //读取房产资料并上传到cos,基于注解方式
    private JSONObject writeExcelAndUploadCosNew(String productInstId) {

        String targetFilename = "employee_" + productInstId + "-" + new SimpleDateFormat("ddHHmmssS").format(new Date()) + ".xls";
        String cosFolder = "/" + new SimpleDateFormat("yyyyMM").format(new Date()) + "/";
        String cosFilePath = cosFolder + targetFilename;
        String localFilePath = "/tmp/" + targetFilename;

        //获取数据
        EmployeeExample employeeExample = new EmployeeExample();
        EmployeeExample.Criteria criteria = employeeExample.createCriteria();
        criteria.andProductInstIdEqualTo(productInstId);
        //OrderByHelper.orderBy(" dept_id asc");
        List<Employee> employeeList = employeeMapper.selectByExampleWithBLOBsWithDeptName(employeeExample);

        //不按模板导出excel, 基于注解
        ExcelUtils.getInstance().exportObj2Excel(localFilePath, employeeList, Employee.class);

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
