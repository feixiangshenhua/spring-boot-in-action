package com.nari.guangzhou.oauth2.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * 用户相关API
 *
 * @author Zongwei
 * @date 2019/9/27 15:58
 */
@Slf4j
@RestController
public class UserProfileApi {

    /**
     * 获取用户的基本信息
     * @param user
     * @return
     */
    @GetMapping(value = "/user/profile")
    public Principal fetchUserProfile(Principal user){
        return user;
    }

}
