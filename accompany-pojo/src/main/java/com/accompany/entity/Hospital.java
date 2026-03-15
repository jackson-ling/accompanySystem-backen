package com.accompany.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Hospital {
    /** 主键ID */
    private Long id;

    /** 医院名称 */
    private String name;

    /** 地址 */
    private String address;

    /** 级别 */
    private String level;

    /** 电话 */
    private String phone;

    /** 状态 */
    private Integer status;

    /** 创建时间 */
    private LocalDateTime createTime;
}