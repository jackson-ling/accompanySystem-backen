package com.accompany.controller;

import com.accompany.base.Result;
import com.accompany.dto.UpdateAvatarDto;
import com.accompany.dto.UpdatePasswordDto;
import com.accompany.dto.UpdateUserDto;
import com.accompany.service.UserService;
import com.accompany.vo.BalanceVo;
import com.accompany.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 获取用户信息
     */
    @GetMapping("/profile")
    public Result<UserVo> getUserProfile() {
        UserVo userVo = userService.getUserProfile();
        return Result.success(userVo);
    }

    /**
     * 获取用户余额
     */
    @GetMapping("/balance")
    public Result<BalanceVo> getUserBalance() {
        BalanceVo balanceVo = userService.getUserBalance();
        return Result.success(balanceVo);
    }

    /**
     * 更新用户基本信息（昵称、性别、生日）
     */
    @PutMapping("/profile")
    public Result<UserVo> updateUser(@RequestBody UpdateUserDto updateUserDto) {
        UserVo userVo = userService.updateUser(updateUserDto);
        return Result.success(userVo);
    }

    /**
     * 更新用户头像
     */
    @PutMapping("/avatar")
    public Result<UserVo> updateAvatar(@RequestBody UpdateAvatarDto updateAvatarDto) {
        UserVo userVo = userService.updateAvatar(updateAvatarDto);
        return Result.success(userVo);
    }

    /**
     * 修改密码
     */
    @PutMapping("/password")
    public Result updatePassword(@RequestBody UpdatePasswordDto updatePasswordDto) {
        userService.updatePassword(updatePasswordDto);
        return Result.success();
    }
}