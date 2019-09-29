package com.nari.guangzhou.oauth2.api;

import com.nari.guangzhou.oauth2.payload.UserInfoVO;
import com.nariit.pi6000.framework.util.JsonUtil;
import com.nariit.pi6000.ua.bizc.IUserBizc;
import com.nariit.pi6000.ua.po.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

/**
 * 用户相关API
 *
 * @author Zongwei
 * @date 2019/9/27 15:58
 */
@Slf4j
@RestController
public class UserProfileApi {

    @Autowired
    private IUserBizc userBizc;

    /**
     * 获取用户的基本信息
     *
     * @param user
     * @return
     */
    @GetMapping(value = "/user/profile")
    public UserInfoVO fetchUserProfile(Principal user) {
        String username = user.getName();
        User pi6000User = userBizc.getUserByFullName(username);
        UserInfoVO userInfoVo = new UserInfoVO(pi6000User);
        if (StringUtils.isNotBlank(pi6000User.getExt())) {
            Map<String, Object> useExtMap = JsonUtil.parseMapType(pi6000User.getExt());
            userInfoVo.setUserExt(useExtMap);
        }
        return userInfoVo;
    }

}
