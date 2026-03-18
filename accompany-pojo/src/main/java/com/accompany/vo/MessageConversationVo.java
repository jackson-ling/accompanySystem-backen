package com.accompany.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 消息会话VO
 */
@Data
public class MessageConversationVo {
    /** 主键ID */
    private Long id;

    /** 用户ID */
    private Long userId;

    /** 会话类型 */
    private String type;

    /** 目标ID */
    private Long targetId;

    /** 名称 */
    private String name;

    /** 头像 */
    private String avatar;

    /** 最后一条消息 */
    private String lastMessage;

    /** 最后一条消息时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastMessageTime;

    /** 未读消息数 */
    private Integer unreadCount;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
