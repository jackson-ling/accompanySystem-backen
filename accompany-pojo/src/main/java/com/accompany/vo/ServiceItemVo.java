package com.accompany.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 服务项目VO
 */
@Data
public class ServiceItemVo {
    /** 主键ID */
    private Long id;

    /** 分类ID */
    private Long categoryId;

    /** 分类名称 */
    private String categoryName;

    /** 服务名称 */
    private String name;

    /** 服务描述 */
    private String description;

    /** 服务特点 */
    private String features;

    /** 价格 */
    private BigDecimal price;

    /** 服务类型（companion-陪诊，agency-代办） */
    private String type;

    /** 销量 */
    private Integer sales;

    /** 图片URL */
    private String image;

    /** 封面图 */
    private String cover;

    /** 服务时长 */
    private String duration;

    /** 状态（0=禁用，1=正常） */
    private Integer status;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}