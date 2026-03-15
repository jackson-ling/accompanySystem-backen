package com.accompany.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

/**
 * 更新陪诊师个人信息DTO
 */
@Data
public class UpdateCompanionProfileDto {
    /** 昵称 */
    private String nickname;
    /** 头像 */
    private String avatar;
    /** 手机号 */
    private String phone;
    /** 年龄 */
    private Integer age;
    /** 工作经历 */
    private String experience;
    /** 个人简介 */
    private String introduction;
}