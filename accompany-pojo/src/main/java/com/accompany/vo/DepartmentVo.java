package com.accompany.vo;

import lombok.Data;

/**
 * 科室返回对象
 */
@Data
public class DepartmentVo {
    /** ID */
    private Long id;

    /** 科室名称 */
    private String name;

    /** 所属医院ID */
    private Long hospitalId;
}