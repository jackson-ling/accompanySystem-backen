package com.accompany.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class UpdateUserDto {

    /** 昵称 */
    private String nickname;

    /** 性别（0=未知，1=男，2=女） */
    private Integer gender;

    /** 生日 */
    private LocalDate birthday;
}