package com.accompany.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.accompany.base.BasicEnum;
import com.accompany.dto.LoginDto;
import com.accompany.dto.RegisteryDto;
import com.accompany.dto.ResetPasswordDto;
import com.accompany.entity.SysUser;
import com.accompany.exception.BaseException;
import com.accompany.mapper.LoginMapper;
import com.accompany.properties.JwtProperties;
import com.accompany.service.LoginService;
import com.accompany.utill.JwtUtil;
import com.accompany.utill.UserThreadLocal;
import com.accompany.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private LoginMapper loginMapper;


    @Override
    public LoginVo login(LoginDto loginDto) {
        // 查询用户是否存在
        SysUser sysUser = loginMapper.selectUser(loginDto.getPhone());
        if (ObjectUtils.isEmpty(sysUser)){
            throw new BaseException(BasicEnum.NOT_EXIST);
        }

        // 查看账户是否被禁用
        if (sysUser.getStatus() == 0) {
            throw new BaseException(BasicEnum.ACCOUNT_NOT_ACCESS);
        }
        // 比对密码
        if (!sysUser.getPassword().equals(loginDto.getPassword())){
            throw new BaseException(BasicEnum.PASSWORD_ERROR);
        }

        // 登录成功，生成 token 返回
        Map<String,Object> claims = new HashMap<>();
        // 必须存入 userId，拦截器需要用到
        claims.put("userId",sysUser.getId());
        claims.put("phone",sysUser.getPhone());
        claims.put("nickname",sysUser.getNickname());
        String token = JwtUtil.createJWT(jwtProperties.getBase64EncodedSecretKey(), jwtProperties.getTtl(), claims);

        LoginVo loginVo = BeanUtil.toBean(sysUser, LoginVo.class);
        loginVo.setUserId(sysUser.getId());
        loginVo.setToken(token);
        return loginVo;
    }

    @Override
    public LoginVo register(RegisteryDto registeryDto) {
        // 查询用户是否存在
        SysUser sysUser = loginMapper.selectUser(registeryDto.getPhone());
        if (ObjectUtil.isNotEmpty(sysUser)) {
            throw new BaseException(BasicEnum.USER_EXIST);
        }
        // 新增用户
        // 生成随机的三位数
        String randomNumber = String.valueOf((int) ((Math.random() * 900) + 100));
        String nickname = "新用户" + randomNumber;
        SysUser newUser = SysUser.builder()
                .phone(registeryDto.getPhone())
                .password(registeryDto.getPassword())
                .nickname(nickname)
                // 1-普通用户，2-陪诊师，3-管理员
                .userType(1)
                .build();

        loginMapper.registerUser(newUser);

        // 注册成功，生成 token 返回
        Map<String,Object> claims = new HashMap<>();
        claims.put("userId", newUser.getId());
        claims.put("phone", newUser.getPhone());
        claims.put("nickname", newUser.getNickname());
        String token = JwtUtil.createJWT(jwtProperties.getBase64EncodedSecretKey(), jwtProperties.getTtl(), claims);

        LoginVo loginVo = BeanUtil.toBean(newUser, LoginVo.class);
        loginVo.setUserId(newUser.getId());
        loginVo.setToken(token);
        return loginVo;
    }

    @Override
    public void logout() {
        UserThreadLocal.removeCurrentId();
    }

    @Override
    public void resetPassword(ResetPasswordDto dto) {
        // 查询旧密码。防止修改密码与新密码一致
        SysUser sysUser = loginMapper.selectUser(dto.getPhone());

        // 是否匹配旧密码
        if (!sysUser.getPassword().equals(dto.getPassword())) {
            throw new BaseException(BasicEnum.OLD_PASSWORD_ERROR);
        }

        // 校验新旧密码是否相同
        if (sysUser.getPassword().equals(dto.getNewPassword())){
            throw new BaseException(BasicEnum.ALTER_PASSWROD_SAME);
        }

        loginMapper.resetPassword(dto);
    }
}
