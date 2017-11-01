package com.highplace.service.oauth.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.highplace.service.oauth.dao.RoleDao;
import com.highplace.service.oauth.dao.UserDao;
import com.highplace.service.oauth.domain.Role;
import com.highplace.service.oauth.domain.User;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
public class UserController {

    public static final Logger logger = LoggerFactory.getLogger(UserController.class);
    public static final String PREFIX_VERIFY_CODE_NAME_INSESSION = "PREFIX_VERIFY_CODE_NAME_INSESSION";
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    @Qualifier("captchaProducer")
    @Autowired
    private DefaultKaptcha defaultKaptcha;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    //resource server获取当前用户信息接口
    @RequestMapping(path = "/user", method = RequestMethod.GET)
    public Principal getCurrentUser(Principal user) {
        return user;
    }

    @PreAuthorize("#oauth2.hasScope('server')")
    @RequestMapping(path = "/user/{username}", method = RequestMethod.GET)
    public User getUser(@PathVariable String username) {
        return userDao.findByUsernameWithRoles(username);
    }

    @PreAuthorize("#oauth2.hasScope('server')")
    @RequestMapping(path = "/user", method = RequestMethod.POST)
    @Transactional
    public void createUser(@Valid @RequestBody User user) {
        User existing = userDao.findByUsername(user.getUsername());
        Assert.isNull(existing, "user already exists: " + user.getUsername());
        //加密密码
        String hash = encoder.encode(user.getPassword());
        user.setPassword(hash);
        userDao.insertUser(user);  //插入用户
        List<Role> roles = user.getRoles();
        for (Role role : roles) {
            roleDao.insertUserRole(user.getUserId(), role.getRoleId()); //插入用户角色关系
        }
    }

    @PreAuthorize("#oauth2.hasScope('server')")
    @RequestMapping(path = "/user", method = RequestMethod.PUT)
    @Transactional
    public void modifyUser(@Valid @RequestBody User user) {
        User existing = userDao.findByUsername(user.getUsername());
        Assert.notNull(existing, "user not exists: " + user.getUsername());

        if (StringUtils.isNotEmpty(user.getPassword())) {
            //加密密码
            String hash = encoder.encode(user.getPassword());
            existing.setPassword(hash);
        }
        if (StringUtils.isNotEmpty(user.getMobileNo())) {
            existing.setMobileNo(user.getMobileNo());
        }
        if (StringUtils.isNotEmpty(user.getEmail())) {
            existing.setEmail(user.getEmail());
        }
        if (user.getAccountLocked() != null) {
            existing.setAccountLocked(user.getAccountLocked());
        }
        userDao.updateCertainByUsername(existing); //更新特定用户信息

        if (user.getRoles() != null && user.getRoles().size() > 0) {
            roleDao.deleteAllUserRole(existing.getUserId()); //删除老的用户角色关系
            List<Role> roles = user.getRoles();
            for (Role role : roles) {
                roleDao.insertUserRole(user.getUserId(), role.getRoleId()); //插入新的用户角色关系
            }
        }
    }

    //前端获取用户信息接口
    @RequestMapping(path = "/userinfo", method = RequestMethod.GET)
    public Map<String, Object> getUserInfo(OAuth2Authentication authen) {

        //OAuth2Authentication a = (OAuth2Authentication) principal;
        User myUser = (User) authen.getPrincipal();

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("userId", myUser.getUserId());
        result.put("productInstId", myUser.getProductInstId());
        result.put("username", myUser.getUsername());
        result.put("mobileNo", myUser.getMobileNo());
        result.put("email", myUser.getEmail());
        result.put("wxOpenId", myUser.getWxOpenId());
        result.put("superUserFlag", myUser.getSuperUserFlag());
        result.put("roles", myUser.getRoles());
        result.put("modules", myUser.getModules());
        return result;

    }

    @RequestMapping("/captcha-image")
    public void defaultKaptcha(HttpServletRequest request, HttpServletResponse response) throws Exception {

        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");

        //生产验证码字符串并保存到redis中
        String createText = defaultKaptcha.createText();
        stringRedisTemplate.opsForValue().set(PREFIX_VERIFY_CODE_NAME_INSESSION + createText, createText, 60 * 1, TimeUnit.SECONDS);

        BufferedImage bi = defaultKaptcha.createImage(createText);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(bi, "jpg", out);
        try {
            out.flush();
        } finally {
            out.close();
        }
    }

    @RequestMapping("/captcha-image-check")
    public void checkVerifyCode(@RequestParam(value = "verifyCode", required = true) String verifyCode) throws Exception {

        String codeFromRedis = stringRedisTemplate.opsForValue().get(PREFIX_VERIFY_CODE_NAME_INSESSION + verifyCode);
        if (codeFromRedis == null || !codeFromRedis.equals(verifyCode)) {
            logger.debug("XXXXXXXXXXXXXXX codeFromRedis=" + codeFromRedis + "codeFromRequest=" + verifyCode);
            throw new Exception("验证码错误");
        }
    }

    /*
    @PreAuthorize("#oauth2.hasScope('server')")
    //@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    //(@PathVariable("id") int id, @RequestParam(value = "name", required = true) String name,
    //@RequestParam(value = "money", required = true) double money)
    //注册时一定传入实例ID regType为0,设置实例ID为空，表示是租户
    @Transactional
    @RequestMapping(path = "/reg", method = RequestMethod.POST)
    public UserView createUserOld(@Valid @RequestBody User user) throws Exception {

        String codeFromRedis = stringRedisTemplate.opsForValue().get(PREFIX_VERIFY_CODE_NAME_INSESSION + user.getVerifycode());
        if (codeFromRedis == null || user.getVerifycode() == null || !codeFromRedis.equals(user.getVerifycode())) {
            logger.debug("XXXXXXXXXXXXXXX codeFromRedis=" + codeFromRedis + "codeFromRequest=" + user.getVerifycode());
            throw new Exception("验证码错误");
        }
        //request token后再删除
        //stringRedisTemplate.delete(PREFIX_VERIFY_CODE_NAME_INSESSION + user.getVerifycode());

        User existing = userDao.findByUsername(user.getUsername());
        Assert.isNull(existing, "user already exists: " + user.getUsername());

        user.setProductInstId("550E8400-E29B-11D4-A716-446655440000");//hard code...

        //加密密码
        String hash = encoder.encode(user.getPassword());
        user.setPassword(hash);

        userDao.insertUser(user);
        logger.debug("XXXXXXXXXXXXX  userid:" + user.getUserId());

        roleDao.insertUserRole(user.getUserId(), 1L); //hard code...
        return new UserView(user.getProductInstId(), user.getUserId(), user.getUsername(), null, null);
    }
    */

}
