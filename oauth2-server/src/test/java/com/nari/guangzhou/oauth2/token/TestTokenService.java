package com.nari.guangzhou.oauth2.token;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Token测试类
 *
 * @author Zongwei
 * @date 2019/9/27 15:10
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestTokenService {

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private ConsumerTokenServices tokenServices;

    @Test
    public void testGetAllTokens() {
        List<String> tokenValues = new ArrayList<>();
        Collection<OAuth2AccessToken> tokens = tokenStore.findTokensByClientId("merryyou");
        if (tokens != null) {
            for (OAuth2AccessToken token : tokens) {
                tokenValues.add(token.getValue());
            }
        }
        for (OAuth2AccessToken token : tokens) {
            System.out.println(token);
        }
    }

    @Test
    public void testRevokeToken() {
//        OAuth2AccessToken accessToken = tokenStore.readAccessToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1Njk1NzI4NzksImJsb2ciOiJodHRwczovL2xvbmdmZWl6aGVuZy5naXRodWIuaW8vIiwidXNlcl9uYW1lIjoiYWRtaW4zIiwianRpIjoiYjEzMDEzYmItY2Y1Zi00MTVlLTk4ZTAtMzc1Y2VmNWYwNDQzIiwiY2xpZW50X2lkIjoibWVycnl5b3UiLCJzY29wZSI6WyJhbGwiXX0.WF-9BzWVbj8Eoqqk7KNWqTluUsKUsnIEpNgR2U4ixtE");
//        System.out.println(accessToken.getValue());
//        System.out.println(accessToken.getRefreshToken().getValue());
        tokenServices.revokeToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1Njk1NzYzMjgsImJsb2ciOiJodHRwczovL2xvbmdmZWl6aGVuZy5naXRodWIuaW8vIiwidXNlcl9uYW1lIjoiYWRtaW4wMDAxIiwianRpIjoiM2E3NmFlMTktNTkyOC00MmYxLWE1MmEtOGNmMmFkNTJhNTUwIiwiY2xpZW50X2lkIjoibWVycnl5b3UiLCJzY29wZSI6WyJhbGwiXX0.I40D0RnhR7wLeYLvxu0PP61aroj4Q-KY5bAvtYSc7yE");
    }

    @Test
    public void testRevokeRefreshToken() {
        ((RedisTokenStore) tokenStore).removeRefreshToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJhZG1pbjMiLCJzY29wZSI6WyJhbGwiXSwiYXRpIjoiYjEzMDEzYmItY2Y1Zi00MTVlLTk4ZTAtMzc1Y2VmNWYwNDQzIiwiZXhwIjoxNTY5OTI5Mjc5LCJibG9nIjoiaHR0cHM6Ly9sb25nZmVpemhlbmcuZ2l0aHViLmlvLyIsImp0aSI6IjI3NDIwNmI2LTNiOTMtNGI1YS1iNjUwLWE4MzI4M2RiMzhmYiIsImNsaWVudF9pZCI6Im1lcnJ5eW91In0.8GlOZmDkPecrUyd57b_5Q0X1rS0x2VG9BofrdaAQypk");
        OAuth2RefreshToken refreshToken = tokenStore.readRefreshToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJhZG1pbjMiLCJzY29wZSI6WyJhbGwiXSwiYXRpIjoiYjEzMDEzYmItY2Y1Zi00MTVlLTk4ZTAtMzc1Y2VmNWYwNDQzIiwiZXhwIjoxNTY5OTI5Mjc5LCJibG9nIjoiaHR0cHM6Ly9sb25nZmVpemhlbmcuZ2l0aHViLmlvLyIsImp0aSI6IjI3NDIwNmI2LTNiOTMtNGI1YS1iNjUwLWE4MzI4M2RiMzhmYiIsImNsaWVudF9pZCI6Im1lcnJ5eW91In0.8GlOZmDkPecrUyd57b_5Q0X1rS0x2VG9BofrdaAQypk");
    }

}
