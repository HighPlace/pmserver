package com.highplace.service.oauth.service;

import com.highplace.service.oauth.dao.ActionDao;
import com.highplace.service.oauth.dao.UserDao;
import com.highplace.service.oauth.domain.Action;
import com.highplace.service.oauth.domain.MyGrantedAuthority;
import com.highplace.service.oauth.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductUserService implements UserDetailsService {

    public static final Logger logger = LoggerFactory.getLogger(ProductUserService.class);

    @Autowired
    UserDao userDao;

    @Autowired
    ActionDao actionDao;

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

        logger.info("XXXXXXXXXXXXX  username:" + username);
        User user = userDao.findByUsername(username);

        if (user != null) {

            logger.info("XXXXXXXXXXXXX  userid:" + user.getUserId());
            //logger.info("XXXXXXXXXXXXX  password:" + user.getPassword());
            logger.info("XXXXXXXXXXXXX  product_inst_id:" + user.getProductInstId());
            List<Action> actions = actionDao.findByUserId(user.getUserId());
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            for (Action action : actions) {
                if (action != null && action.getActionName() != null) {

                    GrantedAuthority grantedAuthority = new MyGrantedAuthority(action.getResourceUrl(), action.getHttpMethod());
                    grantedAuthorities.add(grantedAuthority);
                }
            }

            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
        } else {
            throw new UsernameNotFoundException("admin: " + username + " do not exist!");
        }
    }
}
