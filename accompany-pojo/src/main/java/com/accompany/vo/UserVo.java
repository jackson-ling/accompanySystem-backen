package com.accompany.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class UserVo {
    /** 主键ID */
    private Integer id;

    /** 昵称 */
    private String nickname;

    /** 头像URL */
    private String avatar;

    /** 手机号 */
    private String phone;

    /** 用户类型（1=普通用户，2=陪护员等） */
    private Integer userType;

    /** 性别（0=未知，1=男，2=女） */
    private Integer gender;

    /** 生日 */
    private LocalDate birthday;

    /** 状态（0=禁用，1=正常） */
    private Integer status;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}