package com.highplace.service.oauth.dao;

import com.highplace.service.oauth.domain.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserDao {

    //通过用户名、手机号、邮箱地址、微信openid 综合查询
    //该查询将返回用户对应的角色、模块和Actions信息
    @Select("SELECT * from t_user where username = #{name} or mobile_no = #{name} or email = #{name} or wx_open_id = #{name} limit 1")
    @Results({
            @Result(id=true, column="user_id", property="userId"),
            @Result(column="product_inst_id", property="productInstId"),
            @Result(column="username", property="username"),
            @Result(column="mobile_no", property="mobileNo"),
            @Result(column="email", property="email"),
            @Result(column="wx_open_id", property="wxOpenId"),
            @Result(column="password", property="password"),
            @Result(column="credential_expired", property="credentialExpired"),
            @Result(column="account_expired", property="accountExpired"),
            @Result(column="account_locked", property="accountLocked"),
            @Result(column="enabled", property="enabled"),
            @Result(column="super_user_flag", property="superUserFlag"),
            @Result(column="create_time", property="createTime"),
            @Result(column="modify_time", property="modifyTime"),
            @Result(column="user_id", property="roles", many=@Many(select="com.highplace.service.oauth.dao.RoleDao.findByUserId")),
            @Result(column="user_id", property="actions", many=@Many(select="com.highplace.service.oauth.dao.ActionDao.findByUserId")),
            @Result(column="user_id", property="modules", many=@Many(select="com.highplace.service.oauth.dao.ModuleDao.findByUserId"))
    })
    public User findByGeneralName(@Param("name") String name);

    //该查询主要是对用户名是否已经存在的判断,不返回用户对应的角色、模块和Actions信息
    @Select("SELECT * from t_user where username = #{username} limit 1")
    @Results({
            @Result(id=true, column="user_id", property="userId"),
            @Result(column="product_inst_id", property="productInstId"),
            @Result(column="username", property="username"),
            @Result(column="mobile_no", property="mobileNo"),
            @Result(column="email", property="email"),
            @Result(column="wx_open_id", property="wxOpenId"),
            @Result(column="password", property="password"),
            @Result(column="credential_expired", property="credentialExpired"),
            @Result(column="account_expired", property="accountExpired"),
            @Result(column="account_locked", property="accountLocked"),
            @Result(column="enabled", property="enabled"),
            @Result(column="super_user_flag", property="superUserFlag"),
            @Result(column="create_time", property="createTime"),
            @Result(column="modify_time", property="modifyTime")
    })
    public User findByUsername(@Param("username") String username);

    //该查询主要是对用户是否绑定微信的判断,不返回用户对应的角色、模块和Actions信息
    @Select("SELECT * from t_user where wx_open_id = #{wxOpenId} limit 1")
    @Results({
            @Result(id=true, column="user_id", property="userId"),
            @Result(column="product_inst_id", property="productInstId"),
            @Result(column="username", property="username"),
            @Result(column="mobile_no", property="mobileNo"),
            @Result(column="email", property="email"),
            @Result(column="wx_open_id", property="wxOpenId"),
            @Result(column="password", property="password"),
            @Result(column="credential_expired", property="credentialExpired"),
            @Result(column="account_expired", property="accountExpired"),
            @Result(column="account_locked", property="accountLocked"),
            @Result(column="enabled", property="enabled"),
            @Result(column="super_user_flag", property="superUserFlag"),
            @Result(column="create_time", property="createTime"),
            @Result(column="modify_time", property="modifyTime")
    })
    public User findByWxOpenId(@Param("wxOpenId") String wxOpenId);

    //用于微信帐号绑定
    @Update("Update t_user set wx_open_id = #{wxOpenId} where username = #{username} limit 1")
    int UpdateWxOpenIdByUsername(@Param("username") String username, @Param("wxOpenId") String wxOpenId);

    //用于用户注册
    @Insert("INSERT INTO t_user(product_inst_id, username, password, create_time) VALUES(#{productInstId}, #{username}, #{password}, now())")
    @Options(useGeneratedKeys = true, keyProperty = "userId", keyColumn = "user_id")
    int insertUser(User user);

    //用于用户注册
    @Insert("INSERT INTO t_user_role(user_id, role_id, create_time) VALUES(#{userId}, #{roleId}, now())")
    int insertUserRole(@Param("userId") Long userId, @Param("roleId") Long roleId);



}
