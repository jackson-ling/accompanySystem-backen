package com.accompany.vo;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 交易记录返回对象
 */
@Data
public class TransactionRecordVo {
    /** 主键ID */
    private Long id;

    /** 交易类型: 1-充值, 2-消费, 3-退款 */
    private Integer type;

    /** 标题 */
    private String title;

    /** 金额（消费和退款显示为负数） */
    private BigDecimal amount;

    /** 余额 */
    private BigDecimal balance;

    /** 交易时间 */
    private LocalDateTime time;
}