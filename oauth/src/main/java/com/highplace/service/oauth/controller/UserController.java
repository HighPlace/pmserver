package com.highplace.service.oauth.controller;

import com.highplace.service.oauth.dao.UserDao;
import com.highplace.service.oauth.domain.User;
import com.highplace.service.oauth.domain.UserView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;

@RestController
public class UserController {

    public static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    /*
    public static final String ROLE_TENANT_ADMIN = "TENANT_ADMIN";
    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_STAFF = "STAFF";
    public static final String ROLE_OWNER = "OWNER";
    public static final String ROLE_RENTER = "RENTER";
    public static final String ROLE_FOLLOWER = "FOLLOWER";
    */

    @Autowired
    private UserDao userDao;

    //@PreAuthorize("#oauth2.hasScope('server')")
    @RequestMapping(path = "/user", method = RequestMethod.GET)
    public Principal user(Principal user) {
        return user;
    }

    //@PreAuthorize("#oauth2.hasScope('server')")
    //@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    //(@PathVariable("id") int id, @RequestParam(value = "name", required = true) String name,
    //@RequestParam(value = "money", required = true) double money)
    //注册时一定传入实例ID regType为0,设置实例ID为空，表示是租户
    @Transactional
    @RequestMapping(path = "/reg", method = RequestMethod.POST)
    public UserView createUser(@Valid @RequestBody User user) {

        User existing = userDao.findByUsername(user.getUsername());
        Assert.isNull(existing, "user already exists: " + user.getUsername());

        user.setProductInstId("550E8400-E29B-11D4-A716-446655440000");//hard code...

        //加密密码
        String hash = encoder.encode(user.getPassword());
        user.setPassword(hash);

        userDao.insertUser(user);
        logger.debug("XXXXXXXXXXXXX  userid:" + user.getUserId());

        userDao.insertUserRole(user.getUserId(), 1L); //hard code...
        return new UserView(user.getProductInstId(), user.getUserId(), user.getUsername());

        /*
        Map<String, String> map = new LinkedHashMap<>();
        map.put("name", principal.getName());
        return map;
        */
    }

    //测试权限控制
    @PreAuthorize("hasAnyAuthority('/property;POST','ADMIN')")
    @RequestMapping("/testauthor")
    public String author() {
        return "有权限访问";
    }
}
