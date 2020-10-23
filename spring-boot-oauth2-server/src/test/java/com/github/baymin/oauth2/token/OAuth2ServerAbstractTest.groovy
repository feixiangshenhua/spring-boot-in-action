package com.github.baymin.oauth2.token

import io.restassured.RestAssured
import org.junit.Before
import org.junit.BeforeClass
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner

/**
 * OAuth2 Server单元测试基类
 * @author Zongwei
 * @date   2020/5/14 17:13
 */
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
abstract class OAuth2ServerAbstractTest {

    @LocalServerPort
    int port;

    @BeforeClass
    static void beforeClass() throws Exception {
    }

    @Before
    void beforeMethod() {
        RestAssured.baseURI = "http://127.0.0.1";
        RestAssured.port = port;
        RestAssured.basePath = "/oauth2-server";
    }

}
