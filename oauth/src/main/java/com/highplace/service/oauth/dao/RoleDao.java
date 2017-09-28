package com.highplace.service.oauth.dao;

import com.highplace.service.oauth.domain.Action;
import com.highplace.service.oauth.domain.Role;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RoleDao {

    @Select("select b.* from t_user_role a, t_role b where a.user_id= #{userId} and a.role_id=b.role_id")
    @Results({
            @Result(id=true, column="role_id", property="roleId"),
            @Result(column="product_inst_id", property="productInstId"),
            @Result(column="role_name", property="roleName"),
            @Result(column="remark", property="remark"),
            @Result(column="create_time", property="createTime"),
            @Result(column="modify_time", property="modifyTime"),
            @Result(column="super_role_flag", property="superRoleFlag")
    })
    public List<Role> findByUserId(@Param("userId") long userId);
}
