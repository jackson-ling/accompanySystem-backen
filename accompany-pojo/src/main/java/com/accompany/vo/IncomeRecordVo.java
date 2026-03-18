package com.accompany.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 收入记录VO
 */
@Data
public class IncomeRecordVo {
    /** 记录ID */
    private Long id;
    /** 订单ID */
    private Long orderId;
    /** 服务名称 */
    private String serviceName;
    /** 收入金额 */
    private BigDecimal amount;
    /** 收入时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime time;
    /** 状态: 0-待结算, 1-已结算, 2-已取消 */
    private Integer status;
}