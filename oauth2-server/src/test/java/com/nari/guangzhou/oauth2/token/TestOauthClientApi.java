package com.nari.guangzhou.oauth2.token;

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
        clientDetails.setClientId("nari_metabase");
        clientDetails.setClientSecret("nari_metabase_9527");
        clientDetails.setAccessTokenValiditySeconds(3600);
        clientDetails.setRefreshTokenValiditySeconds(-1);
        clientDetails.setScope(ImmutableSet.of("read", "write", "all"));
        clientDetails.setAuthorizedGrantTypes(ImmutableSet.of("refresh_token", "password", "authorization_code"));
        clientDetails.setRegisteredRedirectUri(ImmutableSet.of("http://www.baidu.com"));

        RestAssured.given().contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NzEwMzg2MDEsInVzZXJfbmFtZSI6IuW-kOaYpeWNjiIsImF1dGhvcml0aWVzIjpbIlRBQkxFXzEiLCJUQUJMRV8yIl0sImp0aSI6ImEyM2NlMjVlLTU2MWQtNDk5Yy1iMjFhLTc4MTgxODJiODk0NCIsImNsaWVudF9pZCI6Im1lcnJ5eW91Iiwic2NvcGUiOlsiYWxsIl19.eeSbyLUH3jK-fbuZVk9Pcq0Zkj5_NhdAftS0dEn0Q8I")
                .body(clientDetails).log().all()
                .post("/oauth/client")
                .then().log().all().statusCode(HttpStatus.CREATED.value());
    }

}
