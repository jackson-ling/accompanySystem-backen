package com.accompany.dto;

import lombok.Data;

@Data
public class UpdatePatientDto {

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
}