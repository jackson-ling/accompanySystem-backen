package com.accompany.vo;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class BalanceVo {
    /** 账户余额 */
    private BigDecimal balance;
}