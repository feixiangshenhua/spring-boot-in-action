package com.nari.guangzhou.oauth2.security.core;

import com.nariit.pi6000.ua.bizc.IUserBizc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * 自定义的用户接口实现类
 *
 * @author Zongwei
 * @date 2019/9/27 9:53
 */
@Component
public class PlatformUserDetailsService implements UserDetailsService {

    @Autowired
    private IUserBizc userBizc;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.nariit.pi6000.ua.po.User pi6000User = userBizc.getUserByFullName(username);

        return User.builder().username(username).password(pi6000User.getPwd())
                .accountExpired(false).accountLocked(false).disabled(false).credentialsExpired(false)
                .authorities("TABLE_1", "TABLE_2")
//              .roles("USER", "VISITOR")
                .build();
    }
}
