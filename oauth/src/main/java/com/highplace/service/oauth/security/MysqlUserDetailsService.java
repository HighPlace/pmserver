package com.highplace.service.oauth.security;

import com.highplace.service.oauth.domain.OldRole;
import com.highplace.service.oauth.domain.OldUser;
import com.highplace.service.oauth.domain.OldUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MysqlUserDetailsService implements UserDetailsService {

    @Autowired
    private OldUserMapper oldUserMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        OldUser oldUser = oldUserMapper.findByUsername(username);

        if (oldUser == null) {
            throw new UsernameNotFoundException(username);
        }

        List<OldRole> oldRoles = oldUserMapper.findAllByUserid(oldUser.getId());
        oldUser.setOldRoles(oldRoles);

        return oldUser;
    }
}
