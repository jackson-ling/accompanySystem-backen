package com.accompany.dto;

import lombok.Data;

@Data
public class ResetPasswordDto {

    private String phone;

    private String password;

    // 忘记密码
    private String newPassword;
}
