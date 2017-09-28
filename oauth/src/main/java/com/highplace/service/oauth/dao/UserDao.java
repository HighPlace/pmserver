package com.highplace.service.oauth.dao;

import com.highplace.service.oauth.domain.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserDao {

    //@Select("SELECT * from t_user where username = #{username} and product_inst_id = #{productInstId}")
    //public User findByUserName(String username, String productInstId);

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

}