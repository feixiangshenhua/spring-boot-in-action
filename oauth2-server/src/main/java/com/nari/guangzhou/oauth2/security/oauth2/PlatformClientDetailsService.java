package com.nari.guangzhou.oauth2.security.oauth2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * OAuth2 Client实现类
 *
 * @author Zongwei
 * @date 2019/9/27 14:25
 */
@Slf4j
public class PlatformClientDetailsService implements ClientDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        log.warn("loadClientByClientId");
        PlatformClientDetails clientDetails = new PlatformClientDetails();
        clientDetails.setAppId("merryyou");
        clientDetails.setSecret(passwordEncoder.encode("merryyou"));
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("read"));
        authorities.add(new SimpleGrantedAuthority("write"));
        authorities.add(new SimpleGrantedAuthority("all"));
        clientDetails.setAuthorities(authorities);
        // 授权类型
        Set<String> authorizedGrantTypes = new TreeSet<>();
        authorizedGrantTypes.add("password");
        authorizedGrantTypes.add("refresh_token");
        authorizedGrantTypes.add("authorization_code");
        clientDetails.setAuthorizedGrantTypes(authorizedGrantTypes);
        Set<String> scope = new TreeSet<>();
        scope.add("openid");
        scope.add("all");
        clientDetails.setScope(scope);
        return clientDetails;
    }

}
