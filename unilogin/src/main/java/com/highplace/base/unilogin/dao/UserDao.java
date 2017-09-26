package com.highplace.base.unilogin.dao;

import com.highplace.base.unilogin.domain.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserDao {

    @Select("SELECT * FROM user WHERE username = #{username}")
    User findByUsername(@Param("username") String username);

    @Select("SELECT * FROM user WHERE github_openid = #{github_openid}")
    User findByGithubOpenid(@Param("github_openid") String github_openid);

    @Insert("INSERT INTO user(username, password) VALUES(#{username}, #{password})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insertUser(User user);

    @Update("UPDATE user set github_openid= #{github_openid} where username = #{username}")
    int setGithubOpenid(User user);

    @Insert("INSERT INTO user(username, github_openid) VALUES(#{username}, #{github_openid})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insertUserWithGithubOpenid(User user);
}
