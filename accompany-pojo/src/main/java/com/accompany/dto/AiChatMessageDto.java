package com.accompany.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * AI聊天消息DTO
 */
@Data
public class AiChatMessageDto {
    /** 用户消息 */
    @NotBlank(message = "消息内容不能为空")
    private String message;
}