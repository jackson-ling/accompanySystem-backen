package com.accompany.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

/**
 * 支付订单DTO
 */
@Data
public class PayOrderDto {
    /** 支付方式: wechat-微信, alipay-支付宝, balance-余额 */
    @NotBlank(message = "支付方式不能为空")
    private String paymentMethod;
}