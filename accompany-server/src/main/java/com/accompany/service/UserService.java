package com.accompany.service;

import com.accompany.dto.UpdateAvatarDto;
import com.accompany.dto.UpdatePasswordDto;
import com.accompany.dto.UpdateUserDto;
import com.accompany.vo.BalanceVo;
import com.accompany.vo.UserVo;

public interface UserService {

    /**
     * 获取当前登录用户信息
     * @return 用户信息
     */
    UserVo getUserProfile();

    /**
     * 获取当前登录用户余额
     * @return 用户余额信息
     */
    BalanceVo getUserBalance();

    /**
     * 更新用户基本信息（昵称、性别、生日）
     * @param updateUserDto 更新用户信息的DTO
     * @return 更新后的用户信息
     */
    UserVo updateUser(UpdateUserDto updateUserDto);

    /**
     * 更新用户头像
     * @param updateAvatarDto 更新头像的DTO
     * @return 更新后的用户信息
     */
    UserVo updateAvatar(UpdateAvatarDto updateAvatarDto);

    /**
     * 修改密码
     * @param updatePasswordDto 修改密码的DTO
     */
    void updatePassword(UpdatePasswordDto updatePasswordDto);
}