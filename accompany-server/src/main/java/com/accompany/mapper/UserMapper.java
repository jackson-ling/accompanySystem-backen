package com.accompany.mapper;

import com.accompany.dto.UpdateAvatarDto;
import com.accompany.dto.UpdatePasswordDto;
import com.accompany.dto.UpdateUserDto;
import com.accompany.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    /**
     * 根据用户ID查询用户信息
     * @param userId 用户ID
     * @return 用户信息
     */
    SysUser selectUserById(Long userId);

    /**
     * 更新用户基本信息（昵称、性别、生日）
     * @param userId 用户ID
     * @param updateUserDto 更新用户信息的DTO
     */
    void updateUser(Long userId, UpdateUserDto updateUserDto);

    /**
     * 更新用户头像
     * @param userId 用户ID
     * @param updateAvatarDto 更新头像的DTO
     */
    void updateAvatar(Long userId, UpdateAvatarDto updateAvatarDto);

    /**
     * 修改密码
     * @param userId 用户ID
     * @param updatePasswordDto 修改密码的DTO
     */
    void updatePassword(Long userId, UpdatePasswordDto updatePasswordDto);
}