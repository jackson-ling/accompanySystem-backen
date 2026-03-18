package com.accompany.vo;

import lombok.Data;
import java.util.List;

/**
 * 充值配置返回对象
 */
@Data
public class RechargeConfigVo {
    /** 最小充值金额 */
    private Integer minAmount;

    /** 最大充值金额 */
    private Integer maxAmount;

    /** 快捷充值金额列表 */
    private List<Integer> quickAmounts;
}