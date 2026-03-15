package com.accompany.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CompanionProfile {
    /** 主键ID */
    private Long id;

    /** 所属用户ID */
    private Long userId;

    /** 姓名 */
    private String name;

    /** 头像URL */
    private String avatar;

    /** 性别（0=未知，1=男，2=女） */
    private Integer gender;

    /** 年龄 */
    private Integer age;

    /** 评分 */
    private BigDecimal score;

    /** 完成订单数 */
    private Integer orders;

    /** 评论数 */
    private Integer comments;

    /** 是否认证（0=否，1=是） */
    private Integer certified;

    /** 是否有资质（0=否，1=是） */
    private Integer qualified;

    /** 工作经历 */
    private String experience;

    /** 标签（JSON或逗号分隔） */
    private String tags;

    /** 服务项目（JSON或逗号分隔） */
    private String services;

    /** 简介 */
    private String intro;

    /** 在线状态（0=离线，1=在线） */
    private Integer isOnline;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;
}