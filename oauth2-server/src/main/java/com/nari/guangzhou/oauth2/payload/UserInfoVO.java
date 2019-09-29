package com.nari.guangzhou.oauth2.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nariit.pi6000.ua.po.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * 用户基本信息
 *
 * @author Zongwei
 * @date 2019/9/29 15:04
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
public class UserInfoVO {

    private String userId;

    private String userName;

    // 用户所属单位
    private String orgName;

    // 用户所属单位编码
    private String orgCode;

    // 用户所属部门（有可能是上级部门）
    private String dept;

    // 用户直属部门
    private String userDept;

    // 用户直属部门ID
    private String userDeptId;

    // 用户角色ID
    private List<String> userRoleIds;

    private Map<String, Object> userExt;

    public UserInfoVO(User user) {
        this.userId = user.getId();
        this.userName = user.getName();
    }

}
