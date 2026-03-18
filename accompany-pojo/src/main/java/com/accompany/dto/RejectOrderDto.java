package com.accompany.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

/**
 * 拒单DTO
 */
@Data
public class RejectOrderDto {
    /** 拒单原因 */
    @NotBlank(message = "拒单原因不能为空")
    private String reason;
}