package com.highplace.service.oauth.JdbcTemplate;

public interface UserService {

    /**
     * 增加一个用户
     * @param name
     */
    void create(String name);

    /**
     * 根据name删除一个用户
     * @param name
     */
    void deleteByName(String name);

    /**
     * 获取用户总量
     */
    Integer getAllUsers();

    /**
     * 删除所有用户
     */
    void deleteAllUsers();

}
