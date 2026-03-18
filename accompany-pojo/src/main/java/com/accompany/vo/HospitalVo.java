package com.accompany.vo;

import lombok.Data;

/**
 * 医院返回对象
 */
@Data
public class HospitalVo {
    /** ID */
    private Long id;

    /** 医院名称 */
    private String name;

    /** 地址 */
    private String address;

    /** 医院等级 */
    private String level;
}