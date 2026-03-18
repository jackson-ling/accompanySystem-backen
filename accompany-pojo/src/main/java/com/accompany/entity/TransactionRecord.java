package com.accompany.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionRecord {
    /** 主键ID */
    private Long id;

    /** 用户ID */
    private Long userId;

    /** 交易类型 */
    private String type;

    /** 标题 */
    private String title;

    /** 金额 */
    private BigDecimal amount;

    /** 余额 */
    private BigDecimal balance;

    /** 订单号 */
    private String orderNo;

    /** 交易时间 */
    private LocalDateTime time;
}