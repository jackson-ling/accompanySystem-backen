package com.accompany.dto;

import lombok.Data;

/**
 * 更新在线状态DTO
 */
@Data
public class UpdateOnlineStatusDto {
    /** 是否在线 */
    private Boolean isOnline;
}