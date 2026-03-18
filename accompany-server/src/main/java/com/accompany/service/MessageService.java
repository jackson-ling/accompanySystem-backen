package com.accompany.service;

import com.accompany.dto.SendMessageDto;
import com.accompany.vo.ChatMessageVo;
import com.accompany.vo.MessageConversationVo;

import java.util.List;

/**
 * 消息模块Service接口
 */
public interface MessageService {

    /**
     * 获取消息会话列表
     * @return 消息会话列表
     */
    List<MessageConversationVo> getConversationList();

    /**
     * 获取未读消息数量
     * @return 未读消息数量
     */
    Integer getUnreadCount();

    /**
     * 标记消息已读
     * @param id 会话ID
     */
    void markAsRead(Long id);

    /**
     * 删除消息会话
     * @param id 会话ID
     */
    void deleteConversation(Long id);

    /**
     * 获取聊天消息列表
     * @param type 会话类型
     * @param targetId 目标ID(陪诊师ID)
     * @param page 页码
     * @param pageSize 每页数量
     * @return 聊天消息列表
     */
    List<ChatMessageVo> getMessageList(String type, Long targetId, Integer page, Integer pageSize);

    /**
     * 发送消息
     * @param type 会话类型
     * @param targetId 目标ID(陪诊师ID)
     * @param sendMessageDto 发送消息DTO
     */
    void sendMessage(String type, Long targetId, SendMessageDto sendMessageDto);

    /**
     * 删除聊天记录
     * @param type 会话类型
     * @param targetId 目标ID(陪诊师ID)
     */
    void deleteChatRecords(String type, Long targetId);
}
