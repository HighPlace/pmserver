package com.highplace.service.oauth.controller;

import com.highplace.service.oauth.domain.OldRole;
import com.highplace.service.oauth.domain.OldUser;
import com.highplace.service.oauth.domain.OldUserMapper;
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

import static com.highplace.service.oauth.domain.RegType.*;

//@RestController
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
    private OldUserMapper oldUserMapper;

    //@PreAuthorize("#oauth2.hasScope('server')")
    //@Transactional
    @RequestMapping(path = "/reg", method = RequestMethod.POST)
    //@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    //(@PathVariable("id") int id, @RequestParam(value = "name", required = true) String name,
    //  @RequestParam(value = "money", required = true) double money)
    //注册时一定传入租户ID和实例ID regType为0,设置实例ID为空，表示是租户
    public OldUser createUser(@Valid @RequestBody OldUser oldUser) {

        OldUser existing = oldUserMapper.findByUsername(oldUser.getUsername());
        Assert.isNull(existing, "oldUser already exists: " + oldUser.getUsername());

        String hash = encoder.encode(oldUser.getPassword());
        oldUser.setPassword(hash);
        oldUserMapper.insertUser(oldUser);

        OldRole oldRole = new OldRole();
        oldRole.setInstance_id(oldUser.getInstance_id());
        oldRole.setTenant_id(oldUser.getTenant_id());
        switch (oldUser.getReg_type()) {
            case TENANT_ADMIN:
                oldRole.setName(this.ROLE_TENANT_ADMIN);
                break;
            case ADMIN:
                oldRole.setName(this.ROLE_ADMIN);
                break;
            case STAFF:
                oldRole.setName(this.ROLE_STAFF);
                break;
            case OWNER:
                oldRole.setName(this.ROLE_OWNER);
                break;
            case RENTER:
                oldRole.setName(this.ROLE_RENTER);
                break;
            case FOLLOWER:
                oldRole.setName(this.ROLE_FOLLOWER);
                break;
            default:
                break;
        }
        oldUserMapper.insertRole(oldRole);

        //写入user和role关系表
        oldRole = oldUserMapper.findByRole(oldRole);
        oldUser = oldUserMapper.findByUsername(oldUser.getUsername());
        oldUserMapper.insertUserRoles(oldUser.getId(), oldRole.getId());

        return oldUser;
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

String instanceid="111";
    //@PreAuthorize("hasRole('ADMIN')")
    @PreAuthorize("hasAuthority(instanceid)")
    @RequestMapping("/testauthor")
    public String author() {
        return "有权限访问";
    }
}
