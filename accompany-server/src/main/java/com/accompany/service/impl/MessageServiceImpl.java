package com.accompany.service.impl;

import com.accompany.base.BasicEnum;
import com.accompany.dto.SendMessageDto;
import com.accompany.entity.ChatMessage;
import com.accompany.entity.MessageConversation;
import com.accompany.entity.SysUser;
import com.accompany.exception.BaseException;
import com.accompany.mapper.ChatMessageMapper;
import com.accompany.mapper.MessageMapper;
import com.accompany.mapper.UserMapper;
import com.accompany.service.MessageService;
import com.accompany.util.UserThreadLocal;
import com.accompany.vo.ChatMessageVo;
import com.accompany.vo.MessageConversationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 消息模块Service实现
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private ChatMessageMapper chatMessageMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<MessageConversationVo> getConversationList() {
        Long userId = UserThreadLocal.getCurrentId();
        if (ObjectUtils.isEmpty(userId)) {
            throw new BaseException(BasicEnum.USER_NOT_LOGIN);
        }
        return messageMapper.selectConversationList(userId);
    }

    @Override
    public Integer getUnreadCount() {
        Long userId = UserThreadLocal.getCurrentId();
        if (ObjectUtils.isEmpty(userId)) {
            throw new BaseException(BasicEnum.USER_NOT_LOGIN);
        }
        Integer count = messageMapper.countUnread(userId);
        return ObjectUtils.isEmpty(count) ? 0 : count;
    }

    @Override
    @Transactional
    public void markAsRead(Long id) {
        Long userId = UserThreadLocal.getCurrentId();
        if (ObjectUtils.isEmpty(userId)) {
            throw new BaseException(BasicEnum.USER_NOT_LOGIN);
        }
        MessageConversation conversation = messageMapper.selectById(id, userId);
        if (ObjectUtils.isEmpty(conversation)) {
            throw new BaseException(BasicEnum.CONVERSATION_NOT_EXIST);
        }
        // 标记已读，将未读数设置为0
        messageMapper.markAsRead(id);
    }

    @Override
    @Transactional
    public void deleteConversation(Long id) {
        Long userId = UserThreadLocal.getCurrentId();
        if (ObjectUtils.isEmpty(userId)) {
            throw new BaseException(BasicEnum.USER_NOT_LOGIN);
        }
        MessageConversation conversation = messageMapper.selectById(id, userId);
        if (ObjectUtils.isEmpty(conversation)) {
            throw new BaseException(BasicEnum.CONVERSATION_NOT_EXIST);
        }
        // 删除会话关联的聊天记录
        chatMessageMapper.deleteMessages(id);
        // 删除会话
        messageMapper.deleteConversation(id, userId);
    }

    @Override
    public List<ChatMessageVo> getMessageList(String type, Long targetId, Integer page, Integer pageSize) {
        Long userId = UserThreadLocal.getCurrentId();
        if (ObjectUtils.isEmpty(userId)) {
            throw new BaseException(BasicEnum.USER_NOT_LOGIN);
        }
        // 获取会话
        MessageConversation conversation = messageMapper.selectByUserIdAndType(userId, type, targetId);
        if (ObjectUtils.isEmpty(conversation)) {
            throw new BaseException(BasicEnum.CONVERSATION_NOT_EXIST);
        }
        // 计算偏移量
        Integer offset = (page - 1) * pageSize;
        return chatMessageMapper.selectMessageList(conversation.getId(), offset, pageSize);
    }

    @Override
    @Transactional
    public void sendMessage(String type, Long targetId, SendMessageDto sendMessageDto) {
        Long userId = UserThreadLocal.getCurrentId();
        if (ObjectUtils.isEmpty(userId)) {
            throw new BaseException(BasicEnum.USER_NOT_LOGIN);
        }
        // 获取或创建会话
        MessageConversation conversation = messageMapper.selectByUserIdAndType(userId, type, targetId);
        if (ObjectUtils.isEmpty(conversation)) {
            // 创建新会话
            conversation = createConversation(userId, type, targetId);
        }

        // 保存消息
        ChatMessage message = new ChatMessage();
        message.setConversationId(conversation.getId());
        message.setUserId(userId);
        message.setText(sendMessageDto.getText());
        message.setIsMe(1);
        message.setType(ObjectUtils.isEmpty(sendMessageDto.getType()) ? "text" : sendMessageDto.getType());
        message.setTime(LocalDateTime.now());
        chatMessageMapper.insertMessage(message);

        // 更新会话最后消息
        messageMapper.updateLastMessage(conversation.getId(), sendMessageDto.getText(), LocalDateTime.now());
    }

    @Override
    @Transactional
    public void deleteChatRecords(String type, Long targetId) {
        Long userId = UserThreadLocal.getCurrentId();
        if (ObjectUtils.isEmpty(userId)) {
            throw new BaseException(BasicEnum.USER_NOT_LOGIN);
        }
        MessageConversation conversation = messageMapper.selectByUserIdAndType(userId, type, targetId);
        if (ObjectUtils.isEmpty(conversation)) {
            throw new BaseException(BasicEnum.CONVERSATION_NOT_EXIST);
        }
        // 删除聊天记录
        chatMessageMapper.deleteMessages(conversation.getId());
        // 更新会话最后消息为提示语
        messageMapper.updateLastMessage(conversation.getId(), "聊天记录已清空", LocalDateTime.now());
    }

    /**
     * 创建会话
     */
    private MessageConversation createConversation(Long userId, String type, Long targetIdParam) {
        // 根据类型获取目标用户信息
        Long targetId = targetIdParam;
        String name = "";
        String avatar = "";

        if ("companion".equals(type)) {
            // 陪诊师消息
            if (targetId == null) {
                throw new BaseException(BasicEnum.PARAM_ERROR);
            }
            // 根据targetId查询陪诊师信息
            SysUser companion = userMapper.selectUserById(targetId);
            if (ObjectUtils.isEmpty(companion)) {
                throw new BaseException(BasicEnum.PARAM_ERROR);
            }
            name = ObjectUtils.isEmpty(companion.getNickname()) ? "陪诊师" : companion.getNickname();
            avatar = companion.getAvatar();
        } else if ("service".equals(type)) {
            // 客服消息
            name = "在线客服";
            targetId = 0L;
            avatar = "";
        } else {
            throw new BaseException(BasicEnum.PARAM_ERROR);
        }

        MessageConversation conversation = new MessageConversation();
        conversation.setUserId(userId);
        conversation.setType(type);
        conversation.setTargetId(targetId);
        conversation.setName(name);
        conversation.setAvatar(avatar);
        conversation.setLastMessage("");
        conversation.setLastMessageTime(LocalDateTime.now());
        conversation.setUnreadCount(0);
        conversation.setCreateTime(LocalDateTime.now());
        conversation.setUpdateTime(LocalDateTime.now());

        messageMapper.insertConversation(conversation);
        return conversation;
    }
}
