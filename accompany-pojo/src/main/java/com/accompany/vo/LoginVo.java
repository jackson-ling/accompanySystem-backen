package com.accompany.vo;

import lombok.Data;

@Data
public class LoginVo {
    /**
     * 登录凭证(Token/JWT)
     */
    private String token;

    /**
     * 用户唯一ID。
     */
    private Integer userId;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 头像URL
     */
    private String avatar;

    /**
     * 用户类型
     * 用于区分不同类型的用户，例如：1-普通用户, 2-管理员等。
     */
    private Integer userType;

    /**
     * 手机号码
     */
    private String phone;
}
