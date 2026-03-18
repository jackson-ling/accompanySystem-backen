package com.accompany.vo;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 充值记录返回对象
 */
@Data
public class RechargeRecordVo {
    /** 主键ID */
    private Long id;

    /** 充值订单号 */
    private String orderNo;

    /** 充值金额 */
    private BigDecimal amount;

    /** 支付方式 */
    private String method;

    /** 支付状态: 0-待支付, 1-支付成功, 2-支付失败 */
    private Integer status;

    /** 创建时间 */
    private LocalDateTime time;
}