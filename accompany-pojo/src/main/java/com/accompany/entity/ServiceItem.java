package com.accompany.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ServiceItem {
    /** 主键ID */
    private Long id;

    /** 所属分类ID */
    private Long categoryId;

    /** 服务名称 */
    private String name;

    /** 服务描述 */
    private String description;

    /** 服务特点 */
    private String features;

    /** 服务价格 */
    private BigDecimal price;

    /** 服务类型 */
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
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;
}