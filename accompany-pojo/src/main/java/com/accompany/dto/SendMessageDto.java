package com.accompany.dto;

import lombok.Data;
import jakarta.validation.constraints.NotNull;

/**
 * 发送消息DTO
 */
@Data
public class SendMessageDto {
    /** 消息内容 */
    @NotNull(message = "消息内容不能为空")
    private String text;

    /** 消息类型 */
    private String type;
}
