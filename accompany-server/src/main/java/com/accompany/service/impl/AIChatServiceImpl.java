package com.accompany.service.impl;

import com.accompany.base.BasicEnum;
import com.accompany.dto.AiChatMessageDto;
import com.accompany.entity.AiChatHistory;
import com.accompany.exception.BaseException;
import com.accompany.mapper.AiChatMapper;
import com.accompany.service.AIChatService;
import com.accompany.util.UserThreadLocal;
import com.accompany.vo.AiChatResponseVo;
import com.accompany.vo.ChatMessageVo;
import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationParam;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * AI聊天模块Service实现
 */
@Service
public class AIChatServiceImpl implements AIChatService {

    private static final Logger log = LoggerFactory.getLogger(AIChatServiceImpl.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final AiChatMapper aiChatMapper;

    @Value("${accompany.aliyun.dashscope.api-key:}")
    private String apiKey;

    public AIChatServiceImpl(AiChatMapper aiChatMapper) {
        this.aiChatMapper = aiChatMapper;
    }

    @Override
    public List<ChatMessageVo> getChatHistory() {
        Long userId = UserThreadLocal.getCurrentId();
        log.info("获取AI聊天历史，userId: {}", userId);
        if (ObjectUtils.isEmpty(userId)) {
            log.info("用户未登录，返回空列表");
            return new ArrayList<>();
        }
        return aiChatMapper.selectChatHistory(userId);
    }

    @Override
    @Transactional
    public AiChatResponseVo chat(AiChatMessageDto aiChatMessageDto) {
        String userMessage = aiChatMessageDto.getMessage();
        log.info("用户消息: {}", userMessage);

        // 获取用户ID（可能为空，因为AI接口不需要登录）
        Long userId = UserThreadLocal.getCurrentId();
        log.info("当前用户ID: {}", userId);

        // 调用阿里云千问API获取AI回复
        String aiReply = callDashScopeAPI(userMessage);
        log.info("AI回复: {}", aiReply);

        // 生成建议问题
        List<String> suggestions = generateSuggestions(userMessage);

        // 如果用户已登录，保存聊天记录
        if (userId != null) {
            log.info("用户已登录，开始保存聊天记录");
            try {
                AiChatHistory history = new AiChatHistory();
                history.setUserId(userId);
                history.setMessage(userMessage);
                history.setReply(aiReply);
                // 将suggestions列表转换为JSON字符串
                history.setSuggestions(objectMapper.writeValueAsString(suggestions));
                history.setTime(LocalDateTime.now());
                aiChatMapper.insertChatHistory(history);
                log.info("聊天记录保存成功，historyId: {}", history.getId());
            } catch (Exception e) {
                log.error("保存聊天记录失败", e);
                throw new RuntimeException("保存聊天记录失败", e);
            }
        } else {
            log.info("用户未登录，不保存聊天记录");
        }

        // 返回响应
        AiChatResponseVo response = new AiChatResponseVo();
        response.setReply(aiReply);
        response.setSuggestions(suggestions);
        return response;
    }

    @Override
    @Transactional
    public void clearChatHistory() {
        Long userId = UserThreadLocal.getCurrentId();
        log.info("清空AI聊天记录，userId: {}", userId);
        if (ObjectUtils.isEmpty(userId)) {
            log.info("用户未登录，不执行任何操作");
            return;
        }
        aiChatMapper.deleteChatHistory(userId);
        log.info("聊天记录清空成功");
    }

    /**
     * 调用阿里云千问API
     * @param message 用户消息
     * @return AI回复
     */
    private String callDashScopeAPI(String message) {
        if (ObjectUtils.isEmpty(apiKey)) {
            return "AI服务配置错误，请联系管理员。";
        }

        try {
            Generation gen = new Generation();

            // 构建系统提示词
            String systemPrompt = "你是一个陪诊服务系统的智能客服助手。你的名字叫'小陪'。" +
                    "请根据用户的问题提供专业、友好、准确的回答。" +
                    "你的主要职责是回答关于陪诊服务的问题，包括服务内容、价格、预约流程等。" +
                    "回答时请用中文，保持简洁明了。";

            // 构建消息列表
            List<Message> messages = Arrays.asList(
                    Message.builder().role("system").content(systemPrompt).build(),
                    Message.builder().role("user").content(message).build()
            );

            // 构建请求参数
            GenerationParam param = GenerationParam.builder()
                    .apiKey(apiKey)
                    .model("qwen-plus")
                    .messages(messages)
                    .resultFormat(GenerationParam.ResultFormat.MESSAGE)
                    .build();

            // 调用API
            GenerationResult result = gen.call(param);
            return result.getOutput().getChoices().get(0).getMessage().getContent();

        } catch (NoApiKeyException e) {
            return "AI服务配置错误，请联系管理员。";
        } catch (ApiException e) {
            e.printStackTrace();
            return "抱歉，AI服务出现了异常，请稍后再试。";
        } catch (InputRequiredException e) {
            e.printStackTrace();
            return "抱歉，AI服务出现了异常，请稍后再试。";
        } catch (Exception e) {
            e.printStackTrace();
            return "抱歉，AI服务出现了异常，请稍后再试。";
        }
    }

    /**
     * 生成建议问题
     * @param message 用户消息
     * @return 建议问题列表
     */
    private List<String> generateSuggestions(String message) {
        List<String> suggestions = new ArrayList<>();

        // 根据用户消息关键词生成建议问题
        String lowerMessage = message.toLowerCase();

        if (lowerMessage.contains("价格") || lowerMessage.contains("多少钱") || lowerMessage.contains("收费")) {
            suggestions.add("全程陪诊多少钱");
            suggestions.add("代办陪诊价格");
            suggestions.add("有哪些套餐");
        } else if (lowerMessage.contains("预约") || lowerMessage.contains("下单") || lowerMessage.contains("如何")) {
            suggestions.add("如何预约陪诊师");
            suggestions.add("预约需要提供什么信息");
            suggestions.add("可以取消订单吗");
        } else if (lowerMessage.contains("服务") || lowerMessage.contains("项目")) {
            suggestions.add("你们提供哪些服务");
            suggestions.add("陪诊师都有哪些资质");
            suggestions.add("服务时长多久");
        } else if (lowerMessage.contains("退款") || lowerMessage.contains("取消")) {
            suggestions.add("如何退款");
            suggestions.add("退款需要多久");
            suggestions.add("什么情况下可以退款");
        } else {
            suggestions.add("全程陪诊多少钱");
            suggestions.add("如何预约陪诊师");
            suggestions.add("你们提供哪些服务");
        }

        return suggestions;
    }
}