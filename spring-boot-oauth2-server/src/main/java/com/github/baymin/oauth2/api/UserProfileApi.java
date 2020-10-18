package com.github.baymin.oauth2.api;

import com.github.baymin.oauth2.entity.UserProfile;
import com.github.baymin.oauth2.payload.UserInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;
import java.util.UUID;

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
     *
     * @param user
     * @return
     */
    @GetMapping(value = "/user/profile")
    public UserInfoVO fetchUserProfile(Principal user) {
        String username = user.getName();
        UserProfile userProfile = new UserProfile();
        userProfile.setUserName(username);
        userProfile.setUserId(UUID.randomUUID().toString());
        userProfile.setPassword(UUID.randomUUID().toString());
        UserInfoVO userInfoVo = new UserInfoVO(userProfile);
        return userInfoVo;
    }

}
