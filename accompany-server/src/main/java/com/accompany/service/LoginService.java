package com.accompany.service;

import com.accompany.base.Result;
import com.accompany.dto.LoginDto;
import com.accompany.dto.RegisteryDto;
import com.accompany.dto.ResetPasswordDto;
import com.accompany.vo.LoginVo;

public interface LoginService {

    LoginVo login(LoginDto loginDto);

    LoginVo register(RegisteryDto registeryDto);

    void logout();

    void resetPassword(ResetPasswordDto dto);
}
