package com.highplace.service.oauth.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.highplace.service.oauth.dao.CountryDao;
import com.highplace.service.oauth.dao.UserDao;
import com.highplace.service.oauth.domain.Country;
import com.highplace.service.oauth.domain.User;
import com.highplace.service.oauth.domain.UserView;
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
import tk.mybatis.orderbyhelper.OrderByHelper;

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
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static final String PREFIX_VERIFY_CODE_NAME_INSESSION = "PREFIX_VERIFY_CODE_NAME_INSESSION";

    @Qualifier("captchaProducer")
    @Autowired
    private DefaultKaptcha defaultKaptcha;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private CountryDao countryDao;

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

    //resource server获取用户信息接口
    //@PreAuthorize("#oauth2.hasScope('server')")
    @RequestMapping(path = "/user", method = RequestMethod.GET)
    public Principal user(Principal user) {
        return user;
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

    //@PreAuthorize("#oauth2.hasScope('server')")
    //@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    //(@PathVariable("id") int id, @RequestParam(value = "name", required = true) String name,
    //@RequestParam(value = "money", required = true) double money)
    //注册时一定传入实例ID regType为0,设置实例ID为空，表示是租户
    @Transactional
    @RequestMapping(path = "/reg", method = RequestMethod.POST)
    public UserView createUser(@Valid @RequestBody User user,
                               HttpServletRequest request) throws Exception {

        //验证验证码 sessionid每次都不一样,改为直接存redis
        /*
        String codeFromSession = (String) request.getSession().getAttribute(PREFIX_VERIFY_CODE_NAME_INSESSION);
        logger.debug("XXXXXXXXXXXXXXX sessinoid:" + request.getSession().getId());
        logger.debug("XXXXXXXXXXXXXXX get session: " + PREFIX_VERIFY_CODE_NAME_INSESSION + "=" + codeFromSession);

        if (codeFromSession == null || user.getVerifycode() == null || !codeFromSession.equals(user.getVerifycode())) {
            logger.debug("XXXXXXXXXXXXXXX codeFromSession=" + codeFromSession + "codeFromRequest=" + user.getVerifycode());
            throw new Exception("验证码错误");
        }
        */

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

        userDao.insertUserRole(user.getUserId(), 1L); //hard code...
        return new UserView(user.getProductInstId(), user.getUserId(), user.getUsername(), null, null);

        /*
        Map<String, String> map = new LinkedHashMap<>();
        map.put("name", principal.getName());
        return map;
        */
    }



    @RequestMapping("/captcha-image")
    public void defaultKaptcha(HttpServletRequest request, HttpServletResponse response) throws Exception{

        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control","no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");

        //生产验证码字符串并保存到redis中
        String createText = defaultKaptcha.createText();
        stringRedisTemplate.opsForValue().set(PREFIX_VERIFY_CODE_NAME_INSESSION + createText, createText,60*1,TimeUnit.SECONDS);
        /* sessionid每次都不一样
        request.getSession().setAttribute(PREFIX_VERIFY_CODE_NAME_INSESSION, createText);
        logger.debug("XXXXXXXXXXXXXXX sessinoid:" + request.getSession().getId());
        logger.debug("XXXXXXXXXXXXXXX set session: " + PREFIX_VERIFY_CODE_NAME_INSESSION + "=" + createText);
        */
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


    //测试权限控制
    @PreAuthorize("hasAnyAuthority('/property;POST','ADMIN')")
    @RequestMapping("/testauthor")
    public String author() {
        return "有权限访问";
    }

    //测试分页功能
    @RequestMapping("/page")
    public List<Country> page() {

        PageHelper.startPage(18, 10);
        OrderByHelper.orderBy("countrycode desc");

        List<Country> countries = countryDao.findAll();
        logger.debug("Total: " + ((Page) countries).getTotal());
        for (Country country : countries) {
            logger.debug("Country Name: " + country.getCountryname());
        }

        return countries;
    }
}
