package com.accompany.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AiChatHistory {
    /** 主键ID */
    private Long id;

    /** 用户ID */
    private Long userId;

    /** 用户消息 */
    private String message;

    /** AI回复 */
    private String reply;

    /** 建议（JSON或逗号分隔） */
    private String suggestions;

    /** 消息时间 */
    private LocalDateTime time;
}