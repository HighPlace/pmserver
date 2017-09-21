package com.highplace.service.oauth.domain;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM user WHERE username = #{username}")
    User findByUsername(@Param("username") String username);

    @Insert("INSERT INTO user(username, password, age) VALUES(#{username}, #{password}, #{age})")
    int insert(@Param("name") String username, @Param("password") String password, @Param("age") Integer age);

}
