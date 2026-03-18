package com.accompany.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MessageConversation {
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
    private LocalDateTime lastMessageTime;

    /** 未读消息数 */
    private Integer unreadCount;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;
}