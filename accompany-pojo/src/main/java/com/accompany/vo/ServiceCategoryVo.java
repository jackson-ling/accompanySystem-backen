package com.accompany.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 服务分类VO
 */
@Data
public class ServiceCategoryVo {
    /** 主键ID */
    private Long id;

    /** 分类名称 */
    private String name;

    /** 图标URL */
    private String icon;

    /** 描述 */
    private String description;

    /** 排序 */
    private Integer sort;

    /** 状态（0=禁用，1=正常） */
    private Integer status;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}