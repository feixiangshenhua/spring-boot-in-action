package com.nari.guangzhou.oauth2.security;

import com.nari.guangzhou.oauth2.security.core.PlatformUserDetails;
import com.nari.guangzhou.oauth2.security.core.PlatformUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Objects;

/**
 * 自定义spring security认证处理类
 *
 * @author Zongwei
 */
@Slf4j
@Component
public class PlatformAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private PlatformUserDetailsService userDetailService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 获取登录用户名/userId
        String userName = authentication.getName();
        // 获取登录用户密码
        String password = (String) authentication.getCredentials();

        log.info(userName);

        // 这里调用我们的自己写的获取用户的方法
        PlatformUserDetails userInfo = (PlatformUserDetails) userDetailService.loadUserByUsername(userName);

        // TODO 校验用户名密码是否匹配
        if (Objects.isNull(userInfo)) {
            throw new BadCredentialsException("用户名不存在");
        }

        Collection<? extends GrantedAuthority> authorities = userInfo.getAuthorities();
        // 构建返回的用户登录成功的token
        return new UsernamePasswordAuthenticationToken(userInfo, password, authorities);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        // 这里直接改成return true;表示是支持这个执行
        return true;
    }
}
