package com.accompany.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ChatMessage {
    /** 主键ID */
    private Long id;

    /** 会话ID */
    private Long conversationId;

    /** 用户ID */
    private Long userId;

    /** 消息内容 */
    private String text;

    /** 是否本人发送（0=否，1=是） */
    private Integer isMe;

    /** 消息类型 */
    private String type;

    /** 消息时间 */
    private LocalDateTime time;
}