package com.highplace.service.oauth.dao;

import com.highplace.service.oauth.domain.Action;
import com.highplace.service.oauth.domain.Role;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RoleDao {

    //查询用户角色信息
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

    //查询产品实例下的所有角色信息
    @Select("select role_id, role_name from t_role where product_inst_id= #{productInstId}")
    @Results({
            @Result(id=true, column="role_id", property="roleId"),
            @Result(column="role_name", property="roleName")
    })
    public List<Role> findByProductInstId(@Param("productInstId") String productInstId);

    //插入用户角色关系
    @Insert("INSERT INTO t_user_role(user_id, role_id, create_time) VALUES(#{userId}, #{roleId}, now())")
    int insertUserRole(@Param("userId") Long userId, @Param("roleId") Long roleId);

    //删除所有用户角色关系
    @Delete("delete from t_user_role where user_id = #{userId}")
    int deleteAllUserRole(@Param("userId") Long userId);
}
