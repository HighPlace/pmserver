package com.highplace.biz.pm.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.highplace.biz.pm.config.MQConfig;
import com.highplace.biz.pm.config.QCloudConfig;
import com.highplace.biz.pm.dao.PropertyMapper;
import com.highplace.biz.pm.domain.Property;
import com.highplace.biz.pm.domain.PropertyExample;
import com.highplace.biz.pm.domain.ui.PropertySearchBean;
import com.highplace.biz.pm.service.util.CommonUtils;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.request.DelFileRequest;
import com.qcloud.cos.request.GetFileLocalRequest;
import com.qcloud.cos.sign.Credentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
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

    @Autowired
    private PropertyMapper propertyMapper;

    @Autowired
    QCloudConfig qCloudConfig;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private AmqpTemplate mqTemplate;

    //从redis中查询房产分区/楼号/单元信息
    public Map<String, Object> getAllZoneBuildingUnitId(String productInstId) {

        String redisKeyForZondId = PREFIX_PROPERTY_ZONEID_KEY + productInstId;
        Set<String> sZoneId = stringRedisTemplate.opsForSet().members(redisKeyForZondId);
        if(sZoneId == null) return null;

        List<String> lZondId = new ArrayList<>(sZoneId);
        Collections.sort(lZondId);

        List<Object> zoneList = new ArrayList<>();

        for(int i = 0 ; i < lZondId.size() ; i++) {

            Map<String, Object> zoneMap = new LinkedHashMap<>();

            String zoneId = lZondId.get(i);
            String redisKeyForBuildingId =  PREFIX_PROPERTY_BUILDINGID_KEY + productInstId + zoneId;
            Set<String> sBuildingId = stringRedisTemplate.opsForSet().members(redisKeyForBuildingId);
            //if(sBuildingId == null) continue;

            List<String> lBuildingId = new ArrayList<>(sBuildingId);
            Collections.sort(lBuildingId);

            List<Object> buildingList = new ArrayList<>();

            for(int j = 0 ; j < lBuildingId.size() ; j++) {

                String buildingId = lBuildingId.get(j);
                String redisKeyForUnitId =  PREFIX_PROPERTY_UNITID_KEY + productInstId + buildingId;
                Set<String> sUnitId = stringRedisTemplate.opsForSet().members(redisKeyForUnitId);
                //if(sUnitId == null) continue;

                List<String> lUnitId = new ArrayList<>(sUnitId);
                Collections.sort(lUnitId);

                Map<String, Object> buildingMap = new LinkedHashMap<>();
                buildingMap.put("name", buildingId);
                buildingMap.put("unitIds", lUnitId);

                buildingList.add(buildingMap);
            }

            zoneMap.put("name", zoneId);
            zoneMap.put("buildingIds", buildingList);

            zoneList.add(zoneMap);
        }

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("zoneIds", zoneList);
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

        //更新redis
        addRedisValue(property);

        return propertyMapper.insertSelective(property);
    }

    //修改房产信息
    public int update(String productInstId, Property property) {

        PropertyExample example = new PropertyExample();
        PropertyExample.Criteria criteria = example.createCriteria();
        criteria.andPropertyIdEqualTo(property.getPropertyId());

        //产品实例ID，必须填入
        criteria.andProductInstIdEqualTo(productInstId);

        //更新redis
        addRedisValue(property);
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

    //zoneId/buildingId/unitId以set数据结构缓存到redis中
    public void addRedisValue(Property property) {

        if(property.getProductInstId() == null ) return;

        String redisKeyForZoneId = PREFIX_PROPERTY_ZONEID_KEY + property.getProductInstId();
        String redisKeyForBuildIdPrefix = PREFIX_PROPERTY_BUILDINGID_KEY + property.getProductInstId();
        String redisKeyForUnitIdPrefix = PREFIX_PROPERTY_UNITID_KEY + property.getProductInstId();

        String zoneId = (property.getZoneId() == null)?"" : (property.getZoneId());
        String unitId = (property.getUnitId() == null)?"" : (property.getUnitId());

        stringRedisTemplate.opsForSet().add(redisKeyForZoneId, zoneId);

        if(property.getBuildingId() != null) {
            stringRedisTemplate.opsForSet().add(redisKeyForBuildIdPrefix + zoneId, property.getBuildingId());
            stringRedisTemplate.opsForSet().add(redisKeyForUnitIdPrefix + property.getBuildingId(), unitId);
        }

    }

    //将批量导入请求通过消息队列发出
    public String batchImportCall(String productInstId, String fileUrl, Integer vendor ) {

        String msgId = UUID.randomUUID().toString();
        Map<String, Object> msgMap = new HashMap<String, Object>();
        msgMap.put("msgId", msgId);
        msgMap.put("productInstId", productInstId);
        msgMap.put("fileUrl", fileUrl);
        msgMap.put("target", "property");
        msgMap.put("vendor", vendor);

        String msg = JSON.toJSONString(msgMap);
        logger.debug("Send MQ batchImport message: " + msg);

        mqTemplate.convertAndSend(MQConfig.BATCH_IMPORT_QUEUE_NAME, msg);

        return msgId;
    }

    //从消息队列接收消息后进行导入数据库操作
    //腾讯云操作,参考https://github.com/tencentyun/cos-java-sdk-v4/blob/master/src/main/java/com/qcloud/cos/demo/Demo.java
    public void batchImportHandler(String msg) {

        JSONObject jsonObject = JSON.parseObject(msg);
        if(null != jsonObject) {

            // 初始化秘钥信息
            Credentials cred = new Credentials(Long.parseLong(qCloudConfig.getAppId()), qCloudConfig.getSecretId(), qCloudConfig.getSecretKey());
            // 初始化客户端配置
            ClientConfig clientConfig = new ClientConfig();
            // 设置bucket所在的区域，比如华南园区：gz； 华北园区：tj；华东园区：sh ；
            clientConfig.setRegion("gz");
            // 初始化cosClient
            COSClient cosClient = new COSClient(clientConfig, cred);
            //获取文件在cos上的路径
            String cosFilePath = jsonObject.getString("fileUrl");
            //设置本地存储路径
            String localPathDown = "/tmp/" + cosFilePath;
            GetFileLocalRequest getFileLocalRequest =
                    new GetFileLocalRequest(qCloudConfig.getCosBucketName(), cosFilePath, localPathDown);
            getFileLocalRequest.setUseCDN(false);
            getFileLocalRequest.setReferer("*.myweb.cn");
            String getFileResult = cosClient.getFileLocal(getFileLocalRequest);
            logger.info("qcloud getFileResult: " + getFileResult);

            // 7. 删除文件
            DelFileRequest delFileRequest = new DelFileRequest(qCloudConfig.getCosBucketName(), cosFilePath);
            String delFileResult = cosClient.delFile(delFileRequest);
            logger.info("qcloud delFileResult: " + delFileResult);

            // 关闭释放资源
            cosClient.shutdown();
        }
    }

}
