package com.accompany.dto;

import jakarta.validation.constraints.NotNull;

/**
 * 更新订单状态DTO
 */
public class UpdateOrderStatusDto {

    @NotNull(message = "订单状态不能为空")
    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}