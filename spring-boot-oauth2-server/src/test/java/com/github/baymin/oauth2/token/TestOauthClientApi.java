package com.github.baymin.oauth2.token;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.test.context.junit4.SpringRunner;

import javax.ws.rs.core.MediaType;

/**
 * OauthClientApi单元测试
 *
 * @author Zongwei
 * @date 2019/10/14 14:45
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestOauthClientApi {

    @LocalServerPort
    private int port;

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://127.0.0.1";
        RestAssured.port = port;
        RestAssured.basePath = "/";
    }

    @Test
    public void testCreateClient() throws Exception {
        BaseClientDetails clientDetails = new BaseClientDetails();
        clientDetails.setClientId("test_client");
        clientDetails.setClientSecret("test_client_9527");
        clientDetails.setAccessTokenValiditySeconds(3600);
        clientDetails.setRefreshTokenValiditySeconds(-1);
        clientDetails.setScope(ImmutableSet.of("read", "write", "all"));
        clientDetails.setAuthorizedGrantTypes(ImmutableSet.of("refresh_token", "password", "authorization_code"));
        clientDetails.setRegisteredRedirectUri(ImmutableSet.of("http://www.baidu.com"));
        clientDetails.setAdditionalInformation(ImmutableMap.of("location", "dg"));

        RestAssured.given().contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer 92f929e6-3930-4f86-8108-2fab3cfc4f33")
                .body(clientDetails).log().all()
                .post("/oauth2/oauth/client")
                .then().log().all().statusCode(HttpStatus.CREATED.value());
    }

}
