package com.nari.guangzhou.oauth2.security.core;

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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO 获取PI6000的用户
        PlatformUserDetails platformUserDetails = new PlatformUserDetails();
        platformUserDetails.setUsername(username);
        platformUserDetails.setPassword("123456");
        return platformUserDetails;
    }
}
