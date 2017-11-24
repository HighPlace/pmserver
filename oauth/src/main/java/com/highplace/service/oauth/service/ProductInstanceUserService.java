package com.highplace.service.oauth.service;

import com.highplace.service.oauth.dao.ModuleDao;
import com.highplace.service.oauth.dao.UserDao;
import com.highplace.service.oauth.domain.Action;
import com.highplace.service.oauth.domain.Module;
import com.highplace.service.oauth.domain.MyGrantedAuthority;
import com.highplace.service.oauth.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.highplace.service.oauth.controller.UserController.PREFIX_VERIFY_CODE_NAME_INSESSION;

@Service
public class ProductInstanceUserService implements UserDetailsService {

    public static final Logger logger = LoggerFactory.getLogger(ProductInstanceUserService.class);

    public static final String ADMIN_ROLE = "ADMIN";

    @Autowired
    UserDao userDao;

    @Autowired
    ModuleDao moduleDao;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    //@Autowired
    //ActionDao actionDao;

    /**
     * Locates the user based on the username. In the actual implementation, the search
     * may possibly be case sensitive, or case insensitive depending on how the
     * implementation instance is configured. In this case, the <code>UserDetails</code>
     * object that comes back may have a username that is of a different case than what
     * was actually requested..
     *
     * @param username the username identifying the user whose data is required.
     * @return a fully populated user record (never <code>null</code>)
     * @throws UsernameNotFoundException if the user could not be found or the user has no
     *                                   GrantedAuthority
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //为增加验证码功能，用户名(用户名、邮箱、手机号)和验证以 name|code 的方式传入
        //如果是openid登录，则没有code
        logger.info("username | verifycode: {}" , username);

        String[] str = username.split("\\|");

        //User user = userDao.findByUsername(username);
        //通过用户名、手机号、邮箱、微信openid其中一个查询
        User user = userDao.findByGeneralName(str[0]);

        if (user != null) {

            logger.debug("userid:{}, productInstId:{}, superUserFlag:{}" ,user.getUserId(),user.getProductInstId(),user.getSuperUserFlag());

            //如果是超级管理员,要另外获取产品实例对应的所有模块
            if (user.getSuperUserFlag()) {

                List<Module> modules = moduleDao.findByProductInstId(user.getProductInstId());
                user.setModules(modules);
            }

            boolean checkVerifyCodeFlag = false;

            //用户名和邮箱登录，必须要图形验证码
            if (str[0].equals(user.getUsername()) || str[0].equals(user.getEmail())) {
                if (str.length == 1) {
                    throw new UsernameNotFoundException("Verify code do not exist!");
                } else {
                    checkVerifyCodeFlag = true;
                }
            }
            //手机号登录，有可能是通过手机验证码（作为密码传入），所以需要判断usernmae是否包含了图形验证码，如果包含了，则验证
            else if (str[0].equals(user.getMobileNo())) {
                if (str.length > 1) {
                    checkVerifyCodeFlag = true;
                }
            }

            if (checkVerifyCodeFlag) {
                String codeFromRedis = stringRedisTemplate.opsForValue().get(PREFIX_VERIFY_CODE_NAME_INSESSION + str[1]);
                if (codeFromRedis == null || !codeFromRedis.equals(str[1])) {
                    logger.debug("codeFromRedis={}, codeFromUsername+{}" , codeFromRedis , str[1]);
                    throw new UsernameNotFoundException("验证码错误");
                }
                //删除验证码
                stringRedisTemplate.delete(PREFIX_VERIFY_CODE_NAME_INSESSION + str[1]);
            }

            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

            //超级用户直接写ADMIN角色
            if (user.getSuperUserFlag()) {

                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(ADMIN_ROLE);
                grantedAuthorities.add(grantedAuthority);

            } else {

                List<Action> actions = user.getActions();
                for (Action action : actions) {
                    if (action != null && action.getActionName() != null) {

                        GrantedAuthority grantedAuthority = new MyGrantedAuthority(action.getResourceUrl(), action.getHttpMethod());
                        grantedAuthorities.add(grantedAuthority);
                    }
                }

                /*
                List<Role> roles = user.getRoles();
                for (Role role : roles) {
                    //如果角色是超级管理员,直接写入ADMIN
                    if (role != null && role.getRoleName() != null && role.getSuperRoleFlag()) {
                        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(ADMIN_ROLE);
                        grantedAuthorities.add(grantedAuthority);
                        break;
                    }
                }
                */
            }

            user.setAuthorities(grantedAuthorities);

            //return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
            //        user.isEnabled(), user.isAccountNonExpired(), user.isCredentialsNonExpired(),
            //        user.isAccountNonLocked(), grantedAuthorities);
            return user;

        } else {

            throw new UsernameNotFoundException("Username: " + username + " do not exist!");
        }
    }
}
