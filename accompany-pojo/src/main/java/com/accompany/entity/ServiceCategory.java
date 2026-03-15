package com.accompany.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ServiceCategory {
    /** 主键ID */
    private Long id;

    /** 分类名称 */
    private String name;

    /** 图标URL */
    private String icon;

    /** 排序 */
    private Integer sort;

    /** 状态（0=禁用，1=正常） */
    private Integer status;

    /** 创建时间 */
    private LocalDateTime createTime;
}