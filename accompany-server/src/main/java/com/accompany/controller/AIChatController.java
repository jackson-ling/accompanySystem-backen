package com.accompany.controller;

import com.accompany.base.Result;
import com.accompany.dto.AiChatMessageDto;
import com.accompany.service.AIChatService;
import com.accompany.vo.AiChatResponseVo;
import com.accompany.vo.ChatMessageVo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * AI聊天模块Controller
 */
@RestController
@RequestMapping("/ai")
public class AIChatController {

    @Autowired
    private AIChatService aiChatService;

    /**
     * 获取AI聊天历史记录
     * @return AI聊天记录列表
     */
    @GetMapping("/chat")
    public Result<List<ChatMessageVo>> getChatHistory() {
        List<ChatMessageVo> chatHistory = aiChatService.getChatHistory();
        return Result.success(chatHistory);
    }

    /**
     * AI智能问答
     * @param aiChatMessageDto AI聊天消息DTO
     * @return AI响应
     */
    @PostMapping("/chat")
    public Result<AiChatResponseVo> chat(@Valid @RequestBody AiChatMessageDto aiChatMessageDto) {
        AiChatResponseVo response = aiChatService.chat(aiChatMessageDto);
        return Result.success(response);
    }

    /**
     * 清空AI聊天记录
     * @return 操作结果
     */
    @DeleteMapping("/chat")
    public Result<Void> clearChatHistory() {
        aiChatService.clearChatHistory();
        return Result.success();
    }
}
