package com.accompany.mapper;

import com.accompany.entity.ChatMessage;
import com.accompany.vo.ChatMessageVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 聊天消息模块Mapper
 */
@Mapper
public interface ChatMessageMapper {

    /**
     * 获取聊天消息列表
     * @param conversationId 会话ID
     * @param offset 偏移量
     * @param pageSize 每页数量
     * @return 聊天消息列表
     */
    List<ChatMessageVo> selectMessageList(
            @Param("conversationId") Long conversationId,
            @Param("offset") Integer offset,
            @Param("pageSize") Integer pageSize
    );

    /**
     * 统计聊天消息总数
     * @param conversationId 会话ID
     * @return 总数
     */
    Integer countMessages(@Param("conversationId") Long conversationId);

    /**
     * 插入聊天消息
     * @param message 聊天消息
     */
    void insertMessage(@Param("message") ChatMessage message);

    /**
     * 删除聊天记录
     * @param conversationId 会话ID
     */
    void deleteMessages(@Param("conversationId") Long conversationId);
}
