package com.accompany.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Department {
    /** 主键ID */
    private Long id;

    /** 科室名称 */
    private String name;

    /** 所属医院ID */
    private Long hospitalId;

    /** 创建时间 */
    private LocalDateTime createTime;
}