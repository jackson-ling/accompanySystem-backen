package com.accompany.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CompanionStatistics {
    /** 主键ID */
    private Long id;

    /** 用户ID */
    private Long userId;

    /** 今日收入 */
    private BigDecimal todayIncome;

    /** 今日订单数 */
    private Integer todayOrders;

    /** 本月收入 */
    private BigDecimal monthIncome;

    /** 本月订单数 */
    private Integer monthOrders;

    /** 总收入 */
    private BigDecimal totalIncome;

    /** 总订单数 */
    private Integer totalOrders;

    /** 评分 */
    private BigDecimal score;

    /** 粉丝数 */
    private Integer followers;

    /** 工作天数 */
    private Integer workDays;

    /** 更新时间 */
    private LocalDateTime updateTime;
}