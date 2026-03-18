package com.accompany.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CompanionIncome {
    /** 主键ID */
    private Long id;

    /** 用户ID */
    private Long userId;

    /** 订单ID */
    private Long orderId;

    /** 订单号 */
    private String orderNo;

    /** 服务名称 */
    private String serviceName;

    /** 金额 */
    private BigDecimal amount;

    /** 状态 */
    private String status;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 结算时间 */
    private LocalDateTime settleTime;
}