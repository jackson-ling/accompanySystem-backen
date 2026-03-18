package com.accompany.mapper;

import com.accompany.entity.MessageConversation;
import com.accompany.vo.MessageConversationVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 消息会话模块Mapper
 */
@Mapper
public interface MessageMapper {

    /**
     * 获取用户消息会话列表
     * @param userId 用户ID
     * @return 消息会话列表
     */
    List<MessageConversationVo> selectConversationList(@Param("userId") Long userId);

    /**
     * 根据ID获取消息会话
     * @param id 会话ID
     * @param userId 用户ID
     * @return 消息会话
     */
    MessageConversation selectById(@Param("id") Long id, @Param("userId") Long userId);

    /**
     * 根据用户ID和类型获取会话
     * @param userId 用户ID
     * @param type 会话类型
     * @param targetId 目标ID
     * @return 消息会话
     */
    MessageConversation selectByUserIdAndType(
            @Param("userId") Long userId,
            @Param("type") String type,
            @Param("targetId") Long targetId
    );

    /**
     * 插入消息会话
     * @param conversation 消息会话
     */
    void insertConversation(@Param("conversation") MessageConversation conversation);

    /**
     * 更新会话最后消息
     * @param id 会话ID
     * @param lastMessage 最后消息
     * @param lastMessageTime 最后消息时间
     */
    void updateLastMessage(
            @Param("id") Long id,
            @Param("lastMessage") String lastMessage,
            @Param("lastMessageTime") java.time.LocalDateTime lastMessageTime
    );

    /**
     * 更新未读数量
     * @param id 会话ID
     */
    void incrementUnreadCount(@Param("id") Long id);

    /**
     * 标记已读
     * @param id 会话ID
     */
    void markAsRead(@Param("id") Long id);

    /**
     * 获取未读消息总数
     * @param userId 用户ID
     * @return 未读消息总数
     */
    Integer countUnread(@Param("userId") Long userId);

    /**
     * 删除会话
     * @param id 会话ID
     * @param userId 用户ID
     */
    void deleteConversation(@Param("id") Long id, @Param("userId") Long userId);
}
