package com.accompany.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class RechargeRecord {
    /** 主键ID */
    private Long id;

    /** 用户ID */
    private Long userId;

    /** 充值订单号 */
    private String orderNo;

    /** 金额 */
    private BigDecimal amount;

    /** 支付方式 */
    private String method;

    /** 支付状态 */
    private Integer payStatus;

    /** 支付完成时间 */
    private LocalDateTime completeTime;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;
}