package com.nari.guangzhou.oauth2.security.oauth2;

import com.nari.guangzhou.oauth2.model.UserAuthority;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.*;
import java.util.stream.Collectors;

import static com.nari.guangzhou.oauth2.model.UserAuthority.AUTHORITY_TYPE_TABLE;
import static com.nari.guangzhou.oauth2.security.oauth2.ThreadContextHolder.USER_AUTHORITY_PREFIX;

/**
 * JWT Token增强类
 *
 * @author Zongwei
 * @date 2019/9/27 9:51
 */
public class JwtTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        Map<String, Object> info = new HashMap<>(5);

        String username = ((User) authentication.getPrincipal()).getUsername();
        Object localObject = ThreadContextHolder.get(USER_AUTHORITY_PREFIX + username);
        if (Objects.nonNull(localObject)) {
            List<UserAuthority> userAuthorities = (List<UserAuthority>) localObject;
            Set<String> tableAuthoritySet = userAuthorities.stream()
                    .filter(authority -> StringUtils.equalsIgnoreCase(AUTHORITY_TYPE_TABLE, authority.getAuthorityType()))
                    .map(UserAuthority::getAuthority)
                    .flatMap(Collection::stream).collect(Collectors.toSet());
            info.put("table_authority", tableAuthoritySet);
        }
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
        return accessToken;
    }

}
