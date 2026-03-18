package com.accompany.dto;

import lombok.Data;
import java.math.BigDecimal;

/**
 * 创建充值订单 DTO
 */
@Data
public class CreateRechargeDto {
    /** 充值金额 */
    private BigDecimal amount;

    /** 支付方式: wechat-微信, alipay-支付宝 */
    private String method;
}