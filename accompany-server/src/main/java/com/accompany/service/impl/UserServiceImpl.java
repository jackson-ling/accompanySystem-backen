package com.accompany.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.accompany.base.BasicEnum;
import com.accompany.dto.UpdateAvatarDto;
import com.accompany.dto.UpdatePasswordDto;
import com.accompany.dto.UpdateUserDto;
import com.accompany.entity.SysUser;
import com.accompany.exception.BaseException;
import com.accompany.mapper.UserMapper;
import com.accompany.service.UserService;
import com.accompany.util.UserThreadLocal;
import com.accompany.vo.BalanceVo;
import com.accompany.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserVo getUserProfile() {
        // 从ThreadLocal中获取当前用户ID
        Long userId = UserThreadLocal.getCurrentId();
        if (ObjectUtils.isEmpty(userId)) {
            throw new BaseException(BasicEnum.USER_NOT_LOGIN);
        }

        // 查询用户信息
        SysUser sysUser = userMapper.selectUserById(userId);
        if (ObjectUtils.isEmpty(sysUser)) {
            throw new BaseException(BasicEnum.NOT_EXIST);
        }

        // 转换为UserVo返回
        UserVo userVo = BeanUtil.toBean(sysUser, UserVo.class);
        return userVo;
    }

    @Override
    public BalanceVo getUserBalance() {
        // 从ThreadLocal中获取当前用户ID
        Long userId = UserThreadLocal.getCurrentId();
        if (ObjectUtils.isEmpty(userId)) {
            throw new BaseException(BasicEnum.USER_NOT_LOGIN);
        }

        // 查询用户信息
        SysUser sysUser = userMapper.selectUserById(userId);
        if (ObjectUtils.isEmpty(sysUser)) {
            throw new BaseException(BasicEnum.NOT_EXIST);
        }

        // 创建BalanceVo并设置余额
        BalanceVo balanceVo = new BalanceVo();
        balanceVo.setBalance(sysUser.getBalance());
        return balanceVo;
    }

    @Override
    public UserVo updateUser(UpdateUserDto updateUserDto) {
        // 从ThreadLocal中获取当前用户ID
        Long userId = UserThreadLocal.getCurrentId();
        if (ObjectUtils.isEmpty(userId)) {
            throw new BaseException(BasicEnum.USER_NOT_LOGIN);
        }

        // 查询用户信息
        SysUser sysUser = userMapper.selectUserById(userId);
        if (ObjectUtils.isEmpty(sysUser)) {
            throw new BaseException(BasicEnum.NOT_EXIST);
        }

        // 检查是否至少有一个字段被更新
        boolean hasUpdate = ObjectUtil.isNotEmpty(updateUserDto.getNickname())
                || ObjectUtil.isNotEmpty(updateUserDto.getGender())
                || ObjectUtil.isNotEmpty(updateUserDto.getBirthday());

        if (!hasUpdate) {
            throw new BaseException(BasicEnum.UPDATE_AT_LEAST_ONE_FIELD);
        }

        // 更新用户信息
        userMapper.updateUser(userId, updateUserDto);

        // 重新查询用户信息并返回
        SysUser updatedUser = userMapper.selectUserById(userId);
        UserVo userVo = BeanUtil.toBean(updatedUser, UserVo.class);
        return userVo;
    }

    @Override
    public UserVo updateAvatar(UpdateAvatarDto updateAvatarDto) {
        // 从ThreadLocal中获取当前用户ID
        Long userId = UserThreadLocal.getCurrentId();
        if (ObjectUtils.isEmpty(userId)) {
            throw new BaseException(BasicEnum.USER_NOT_LOGIN);
        }

        // 查询用户信息
        SysUser sysUser = userMapper.selectUserById(userId);
        if (ObjectUtils.isEmpty(sysUser)) {
            throw new BaseException(BasicEnum.NOT_EXIST);
        }

        // 检查头像URL是否为空
        if (ObjectUtil.isEmpty(updateAvatarDto.getAvatar())) {
            throw new BaseException(BasicEnum.AVATAR_URL_NOT_EMPTY);
        }

        // 更新头像
        userMapper.updateAvatar(userId, updateAvatarDto);

        // 重新查询用户信息并返回
        SysUser updatedUser = userMapper.selectUserById(userId);
        UserVo userVo = BeanUtil.toBean(updatedUser, UserVo.class);
        return userVo;
    }

    @Override
    public void updatePassword(UpdatePasswordDto updatePasswordDto) {
        // 从ThreadLocal中获取当前用户ID
        Long userId = UserThreadLocal.getCurrentId();
        if (ObjectUtils.isEmpty(userId)) {
            throw new BaseException(BasicEnum.USER_NOT_LOGIN);
        }

        // 查询用户信息
        SysUser sysUser = userMapper.selectUserById(userId);
        if (ObjectUtils.isEmpty(sysUser)) {
            throw new BaseException(BasicEnum.NOT_EXIST);
        }

        // 验证旧密码
        if (!sysUser.getPassword().equals(updatePasswordDto.getOldPassword())) {
            throw new BaseException(BasicEnum.OLD_PASSWORD_ERROR);
        }

        // 检查新旧密码是否相同
        if (sysUser.getPassword().equals(updatePasswordDto.getNewPassword())) {
            throw new BaseException(BasicEnum.ALTER_PASSWROD_SAME);
        }

        // 验证新密码是否为空
        if (ObjectUtil.isEmpty(updatePasswordDto.getNewPassword())) {
            throw new BaseException(BasicEnum.NEW_PASSWORD_NOT_EMPTY);
        }

        // 更新密码
        userMapper.updatePassword(userId, updatePasswordDto);
    }
}