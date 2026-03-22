package com.accompany.service;

import com.accompany.dto.AiChatMessageDto;
import com.accompany.vo.AiChatResponseVo;
import com.accompany.vo.ChatMessageVo;

import java.util.List;

/**
 * AI聊天模块Service接口
 */
public interface AIChatService {

    /**
     * 获取AI聊天历史记录
     * @return AI聊天记录列表
     */
    List<ChatMessageVo> getChatHistory();

    /**
     * AI智能问答
     * @param aiChatMessageDto AI聊天消息DTO
     * @return AI响应
     */
    AiChatResponseVo chat(AiChatMessageDto aiChatMessageDto);

    /**
     * 清空AI聊天记录
     */
    void clearChatHistory();
}