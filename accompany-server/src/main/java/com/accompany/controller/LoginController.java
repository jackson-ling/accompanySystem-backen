package com.accompany.controller;

import com.accompany.base.Result;
import com.accompany.dto.LoginDto;
import com.accompany.dto.RegisteryDto;
import com.accompany.dto.ResetPasswordDto;
import com.accompany.service.LoginService;
import com.accompany.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    private LoginService loginService;

    /**
     * 登录
     */
    @PostMapping("/login")
    public Result<LoginVo> login(@RequestBody LoginDto loginDto) {
        LoginVo loginVo = loginService.login(loginDto);
        return Result.success(loginVo);
    }

    /**
     *  注册
     */
    @PostMapping("/register")
    public Result<LoginVo> register(@RequestBody RegisteryDto registeryDto) {
        LoginVo loginVo = loginService.register(registeryDto);
        return Result.success(loginVo);
    }

    /**
     * 退出登录
     */
    @PostMapping("/logout")
    public Result logout() {
        loginService.logout();
        return Result.success();
    }

    /**
     * 重置密码
     */
    @PostMapping("/reset-password")
    public Result resetPassword(@RequestBody ResetPasswordDto dto) {
        loginService.resetPassword(dto);
        return Result.success();
    }

}
