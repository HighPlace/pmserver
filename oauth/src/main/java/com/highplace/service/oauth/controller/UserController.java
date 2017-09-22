package com.highplace.service.oauth.controller;

import com.highplace.service.oauth.domain.User;
import com.highplace.service.oauth.domain.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;

@RestController
public class UserController {

    @Autowired
    private UserMapper userMapper;

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    //@RequestMapping(value = "/current", method = RequestMethod.GET)
    @RequestMapping(path = "/current", method = RequestMethod.GET)
    public Principal getUser(Principal principal) {
        return principal;
    }

    //@PreAuthorize("#oauth2.hasScope('server')")
    @RequestMapping(path = "/reg", method = RequestMethod.POST)
    public User createUser(@Valid @RequestBody User user) {

        User existing = userMapper.findByUsername(user.getUsername());
        Assert.isNull(existing, "user already exists: " + user.getUsername());

        String hash = encoder.encode(user.getPassword());
        user.setPassword(hash);
        userMapper.insert(user.getUsername(),user.getPassword(),user.getAge());

        return user;
    }

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping("/testauthor")
    public String author() {
        return "有权限访问";
    }
}
