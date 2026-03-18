package com.accompany.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 陪诊师VO
 */
@Data
public class CompanionVo {
    /** 主键ID */
    private Long id;

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

    /** 评分（rating，与score一致） */
    private BigDecimal rating;

    /** 是否认证（0=否，1=是） */
    private Integer certified;

    /** 是否有资质（0=否，1=是） */
    private Integer qualified;

    /** 工作经历 */
    private String experience;

    /** 标签列表（JSON字符串格式） */
    private String tags;

    /** 服务类别ID列表（JSON字符串格式） */
    private String services;

    /** 简介 */
    private String intro;

    /** 在线状态（0=离线，1=在线） */
    private Integer isOnline;

    /** 是否收藏（动态计算） */
    private Boolean isFavorite;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}