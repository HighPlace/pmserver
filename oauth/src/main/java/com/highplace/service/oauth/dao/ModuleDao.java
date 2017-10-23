package com.highplace.service.oauth.dao;

import com.highplace.service.oauth.domain.Module;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ModuleDao {

    @Select("select distinct c.* from t_user_role a, t_role_action b, t_module c where a.user_id = #{userId} and a.role_id = b.role_id and b.module_id = c.module_id")
    @Results({
            @Result(id=true, column="module_id", property="moduleId"),
            @Result(column="module_name", property="moduleName"),
            @Result(column="release_time", property="releaseTime"),
            @Result(column="cancle_time", property="cancleTime"),
            @Result(column="version", property="version"),
            @Result(column="remark", property="remark"),
            @Result(column="create_time", property="createTime"),
            @Result(column="modify_time", property="modifyTime")
    })
    public List<Module> findByUserId(@Param("userId") long userId);

    //order status=5 表示支付成功发货成功
    @Select("select distinct c.* from t_order a, t_offer_module b, t_module c where a.product_inst_id= #{productInstId} and a.status=5 and a.offer_id = b.offer_id and b.module_id = c.module_id")
    @Results({
            @Result(id=true, column="module_id", property="moduleId"),
            @Result(column="module_name", property="moduleName"),
            @Result(column="release_time", property="releaseTime"),
            @Result(column="cancle_time", property="cancleTime"),
            @Result(column="version", property="version"),
            @Result(column="remark", property="remark"),
            @Result(column="create_time", property="createTime"),
            @Result(column="modify_time", property="modifyTime")
    })
    public List<Module> findByProductInstId(@Param("productInstId") String productInstId);

}
