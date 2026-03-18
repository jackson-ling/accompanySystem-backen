package com.accompany.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PatientVo {
    /** 主键ID */
    private Long id;

    /** 姓名 */
    private String name;

    /** 电话 */
    private String phone;

    /** 身份证号 */
    private String idCard;

    /** 性别（0=未知，1=男，2=女） */
    private Integer gender;

    /** 年龄 */
    private Integer age;

    /** 地址 */
    private String address;

    /** 与用户关系 */
    private String relationship;

    /** 就诊卡号 */
    private String medicalCardNo;

    /** 是否默认（0=否，1=是） */
    private Integer isDefault;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}