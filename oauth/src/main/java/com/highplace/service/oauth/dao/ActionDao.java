package com.highplace.service.oauth.dao;

import com.highplace.service.oauth.domain.Action;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ActionDao {

    //@Select("Select a.* from t_action a, t_role_action b where b.role_id = #{roleId} and  a.action_id = b.action_id and a.module_id=b.module_id")
    //public List<Action> findByRoleId(@Param("roleId") long roleId);

    @Select("select c.* from t_user_role a, t_role_action b, t_action c where a.user_id = #{userId} and a.role_id = b.role_id and b.action_id = c.action_id and b.module_id=c.module_id")
    @Results({
            @Result(id=true, column="action_id", property="actionId"),
            @Result(column="module_id", property="moduleId"),
            @Result(column="action_name", property="actionName"),
            @Result(column="resource_url", property="resourceUrl"),
            @Result(column="http_method", property="httpMethod"),
            @Result(column="remark", property="remark"),
            @Result(column="create_time", property="createTime"),
            @Result(column="modify_time", property="modifyTime")
    })
    public List<Action> findByUserId(@Param("userId") long userId);
}
