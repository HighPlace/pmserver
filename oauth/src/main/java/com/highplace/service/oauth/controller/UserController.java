package com.highplace.service.oauth.controller;

import com.highplace.service.oauth.JpaTemplate.JpaUser;
import com.highplace.service.oauth.JpaTemplate.UserRepository;
import com.highplace.service.oauth.domain.ReqType;
import com.highplace.service.oauth.domain.Role;
import com.highplace.service.oauth.domain.User;
import com.highplace.service.oauth.domain.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
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



    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static final String ROLE_TENANT_ADMIN = "TENANT_ADMIN";
    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_STAFF = "STAFF";
    public static final String ROLE_OWNER = "OWNER";
    public static final String ROLE_RENTER = "RENTER";
    public static final String ROLE_FOLLOWER = "FOLLOWER";

    //@RequestMapping(value = "/current", method = RequestMethod.GET)
    @RequestMapping(path = "/current", method = RequestMethod.GET)
    public Principal getUser(Principal principal) {
        return principal;
    }


    @Autowired
    private UserMapper userMapper;

    //@PreAuthorize("#oauth2.hasScope('server')")
    @Transactional
    @RequestMapping(path = "/reg", method = RequestMethod.POST)
    public User createUser(@Valid @RequestBody User user) {

        User existing = userMapper.findByUsername(user.getUsername());
        Assert.isNull(existing, "user already exists: " + user.getUsername());

        String hash = encoder.encode(user.getPassword());
        user.setPassword(hash);
        userMapper.insertUser(user);

        Role role = new Role();
        role.setInstance_id(user.getInstance_id());
        role.setTenant_id(user.getTenant_id());
        switch (user.getReq_type()) {
            case TENANT_ADMIN:
                role.setName(this.ROLE_TENANT_ADMIN);
                break;
            case ADMIN:
                role.setName(this.ROLE_ADMIN);
                break;
            case STAFF:
                role.setName(this.ROLE_STAFF);
                break;
            case OWNER:
                role.setName(this.ROLE_OWNER);
                break;
            case RENTER:
                role.setName(this.ROLE_RENTER);
                break;
            case FOLLOWER:
                role.setName(this.ROLE_FOLLOWER);
                break;
            default:
                break;
        }
        userMapper.insertRole(role);
        userMapper.insertUserRoles(user.getId(),role.getId());

        return user;
    }


    /*
    @Autowired
    private UserRepository repository;
    //@PreAuthorize("#oauth2.hasScope('server')")
    @RequestMapping(path = "/reg", method = RequestMethod.POST)
    public JpaUser createUser(@Valid @RequestBody JpaUser jpauser) {

        JpaUser existing = repository.findByUsername(jpauser.getUsername());
        Assert.isNull(existing, "user already exists: " + jpauser.getUsername());

        String hash = encoder.encode(jpauser.getPassword());
        jpauser.setPassword(hash);
        repository.save(jpauser);

        return jpauser;
    }
    */


    //@PreAuthorize("hasRole('ADMIN')")
    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping("/testauthor")
    public String author() {
        return "有权限访问";
    }
}
