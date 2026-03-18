package com.accompany.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 聊天消息VO
 */
@Data
public class ChatMessageVo {
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime time;
}
