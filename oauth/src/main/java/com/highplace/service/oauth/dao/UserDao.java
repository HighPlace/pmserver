package com.highplace.service.oauth.dao;

import com.highplace.service.oauth.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserDao {

    //@Select("SELECT * from t_user where username = #{username} and product_inst_id = #{productInstId}")
    //public User findByUserName(String username, String productInstId);

    @Select("SELECT * from t_user where username = #{username} limit 1")
    public User findByUsername(@Param("username") String username);

}
