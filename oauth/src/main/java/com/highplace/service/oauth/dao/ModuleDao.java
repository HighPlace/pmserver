package com.highplace.service.oauth.dao;

import com.highplace.service.oauth.domain.Module;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ModuleDao {

    @Select("select c.* from t_user_role a, t_role_action b, t_module c where a.user_id = #{userId} and a.role_id = b.role_id and b.module_id = c.module_id")
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

}
