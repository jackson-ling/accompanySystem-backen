package com.accompany.vo;

import lombok.Data;
import java.math.BigDecimal;

/**
 * 陪诊师统计数据VO
 */
@Data
public class CompanionStatisticsVo {
    /** 今日收入 */
    private BigDecimal todayIncome;
    /** 今日订单数 */
    private Integer todayOrders;
    /** 评分 */
    private BigDecimal rating;
    /** 粉丝数 */
    private Integer followers;
    /** 总订单数 */
    private Integer totalOrders;
    /** 工作天数 */
    private Integer workDays;
}