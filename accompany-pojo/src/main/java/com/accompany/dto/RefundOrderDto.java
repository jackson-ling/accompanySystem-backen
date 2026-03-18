package com.accompany.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

/**
 * 申请退款DTO
 */
@Data
public class RefundOrderDto {
    /** 退款原因 */
    @NotBlank(message = "退款原因不能为空")
    private String reason;
}