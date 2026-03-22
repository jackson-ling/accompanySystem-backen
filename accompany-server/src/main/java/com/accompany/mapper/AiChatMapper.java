package com.accompany.mapper;

import com.accompany.entity.AiChatHistory;
import com.accompany.vo.ChatMessageVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * AI聊天模块Mapper
 */
@Mapper
public interface AiChatMapper {

    /**
     * 获取AI聊天记录列表
     * @param userId 用户ID
     * @return AI聊天记录列表
     */
    List<ChatMessageVo> selectChatHistory(@Param("userId") Long userId);

    /**
     * 插入AI聊天记录
     * @param history AI聊天记录
     */
    void insertChatHistory(@Param("history") AiChatHistory history);

    /**
     * 删除用户的所有AI聊天记录
     * @param userId 用户ID
     */
    void deleteChatHistory(@Param("userId") Long userId);
}