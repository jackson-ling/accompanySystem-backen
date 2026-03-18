package com.accompany.controller;

import com.accompany.base.Result;
import com.accompany.dto.SendMessageDto;
import com.accompany.service.MessageService;
import com.accompany.vo.ChatMessageVo;
import com.accompany.vo.MessageConversationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 消息模块Controller
 */
@RestController
@RequestMapping("/user")
public class MessageController {

    @Autowired
    private MessageService messageService;

    /**
     * 获取消息会话列表
     * GET /user/messages
     *
     * @return 消息会话列表
     */
    @GetMapping("/messages")
    public Result<List<MessageConversationVo>> getConversationList() {
        List<MessageConversationVo> list = messageService.getConversationList();
        return Result.success(list);
    }

    /**
     * 获取未读消息数量
     * GET /user/messages/unread-count
     *
     * @return 未读消息数量
     */
    @GetMapping("/messages/unread-count")
    public Result<Integer> getUnreadCount() {
        Integer count = messageService.getUnreadCount();
        return Result.success(count);
    }

    /**
     * 标记消息已读
     * PUT /user/messages/{id}/read
     *
     * @param id 会话ID
     */
    @PutMapping("/messages/{id}/read")
    public Result markAsRead(@PathVariable Long id) {
        messageService.markAsRead(id);
        return Result.success();
    }

    /**
     * 删除消息会话
     * DELETE /user/messages/{id}
     *
     * @param id 会话ID
     */
    @DeleteMapping("/messages/{id}")
    public Result deleteConversation(@PathVariable Long id) {
        messageService.deleteConversation(id);
        return Result.success();
    }

    /**
     * 获取聊天消息列表
     * GET /user/chats/{type}
     *
     * @param type 会话类型 (service:客服消息, companion:陪诊师消息)
     * @param targetId 目标ID（陪诊师ID，客服时可为空）
     * @param page 页码，默认1
     * @param pageSize 每页数量，默认20
     * @return 聊天消息列表
     */
    @GetMapping("/chats/{type}")
    public Result<List<ChatMessageVo>> getMessageList(
            @PathVariable String type,
            @RequestParam(required = false) Long targetId,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer pageSize
    ) {
        List<ChatMessageVo> list = messageService.getMessageList(type, targetId, page, pageSize);
        return Result.success(list);
    }

    /**
     * 发送消息
     * POST /user/chats/{type}/messages
     *
     * @param type 会话类型
     * @param sendMessageDto 发送消息DTO
     */
    @PostMapping("/chats/{type}/messages")
    public Result sendMessage(@PathVariable String type,
            @RequestParam(required = false) Long targetId,
            @RequestBody SendMessageDto sendMessageDto) {
        messageService.sendMessage(type, targetId, sendMessageDto);
        return Result.success();
    }

    /**
     * 删除聊天记录
     * DELETE /user/chats/{type}
     *
     * @param type 会话类型
     * @param targetId 目标ID（陪诊师ID，客服时可为空）
     */
    @DeleteMapping("/chats/{type}")
    public Result deleteChatRecords(@PathVariable String type, @RequestParam(required = false) Long targetId) {
        messageService.deleteChatRecords(type, targetId);
        return Result.success();
    }
}
