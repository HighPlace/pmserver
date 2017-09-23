package com.highplace.service.oauth.domain;


import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM t_user WHERE username = #{username}")
    User findByUsername(@Param("username") String username);

    @Select("SELECT * FROM t_role WHERE tenant_id = #{tenant_id} and instance_id = #{instance_id} and name = #{name}")
    Role findByRole(Role role);

    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "tenant_id", column = "tenant_id"),
            @Result(property = "instance_id", column = "instance_id")
    })
    @Select("SELECT * FROM t_role where id in (SELECT role_id from t_user_roles where user_id= #{user_id} )")
    List<Role> findAllByUserid(@Param("user_id") Long user_id);

    @Insert("INSERT INTO t_user(username, mobile_no, email, wx_openid, password, create_time) VALUES(#{username}, #{mobile_no}, #{email}, #{wx_openid}, #{password}, now())")
    int insertUser(User user);

    @Insert("INSERT INTO t_role(tenant_id, instance_id, name, create_time) VALUES(#{tenant_id}, #{instance_id}, #{name}, now())")
    int insertRole(Role role);

    @Insert("INSERT INTO t_user_roles(user_id, role_id, create_time) VALUES(#{user_id}, #{role_id},now())")
    int insertUserRoles(@Param("user_id") Long user_id, @Param("role_id") Long role_id);


}
