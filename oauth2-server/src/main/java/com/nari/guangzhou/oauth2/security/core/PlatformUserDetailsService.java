package com.nari.guangzhou.oauth2.security.core;

import com.nariit.pi6000.ua.bizc.IUserBizc;
import com.nariit.pi6000.ua.po.User;
import org.springframework.beans.factory.annotation.Autowired;
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
        User user = userBizc.getUserByFullName(username);
        PlatformUserDetails platformUserDetails = new PlatformUserDetails();
        platformUserDetails.setUsername(user.getFullName());
        return platformUserDetails;
    }
}
