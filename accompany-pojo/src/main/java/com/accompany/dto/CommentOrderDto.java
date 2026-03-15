package com.accompany.dto;

import lombok.Data;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.DecimalMax;
import java.math.BigDecimal;

/**
 * 评价订单DTO
 */
@Data
public class CommentOrderDto {
    /** 评分(1-5) */
    @NotNull(message = "评分不能为空")
    @DecimalMin(value = "1.0", message = "评分最小为1.0")
    @DecimalMax(value = "5.0", message = "评分最大为5.0")
    private BigDecimal score;

    /** 评价内容 */
    private String content;
}