package com.accompany.vo;

import lombok.Data;

/**
 * 服务类型返回对象
 */
@Data
public class ServiceTypeVo {
    /** ID */
    private Long id;

    /** 名称 */
    private String name;

    /** 值 */
    private String value;
}