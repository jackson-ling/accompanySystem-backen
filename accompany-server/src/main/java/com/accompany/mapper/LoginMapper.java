package com.accompany.mapper;

import com.accompany.dto.ResetPasswordDto;
import com.accompany.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginMapper {

    SysUser selectUser(String phone);

    int registerUser(SysUser sysUser);

    void resetPassword(ResetPasswordDto dto);

}
