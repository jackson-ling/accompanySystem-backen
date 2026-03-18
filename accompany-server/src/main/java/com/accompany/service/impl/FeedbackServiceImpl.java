package com.accompany.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.accompany.dto.SubmitFeedbackDto;
import com.accompany.entity.Feedback;
import com.accompany.exception.BaseException;
import com.accompany.mapper.FeedbackMapper;
import com.accompany.service.FeedbackService;
import com.accompany.util.UserThreadLocal;
import com.accompany.vo.FeedbackVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 反馈服务实现类
 */
@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private FeedbackMapper feedbackMapper;

    @Override
    public void submitFeedback(SubmitFeedbackDto submitFeedbackDto) {
        // 从ThreadLocal中获取当前用户ID
        Long userId = UserThreadLocal.getCurrentId();
        if (ObjectUtils.isEmpty(userId)) {
            throw new BaseException(com.accompany.base.BasicEnum.USER_NOT_LOGIN);
        }

        // 验证反馈内容
        if (!StringUtils.hasText(submitFeedbackDto.getContent())) {
            throw new BaseException(com.accompany.base.BasicEnum.PARAM_ERROR);
        }

        // 创建反馈对象
        Feedback feedback = new Feedback();
        feedback.setUserId(userId);
        feedback.setContent(submitFeedbackDto.getContent());
        feedback.setContact(submitFeedbackDto.getContact());

        // 处理图片列表
        if (ObjectUtil.isNotEmpty(submitFeedbackDto.getImages()) && !submitFeedbackDto.getImages().isEmpty()) {
            // 将图片列表转换为 JSON 数组字符串
            String imagesJson = JSONUtil.toJsonStr(submitFeedbackDto.getImages());
            feedback.setImages(imagesJson);
        }

        // 插入数据库
        feedbackMapper.insert(feedback);
    }

    @Override
    public List<FeedbackVo> getFeedbackList() {
        // 从ThreadLocal中获取当前用户ID
        Long userId = UserThreadLocal.getCurrentId();
        if (ObjectUtils.isEmpty(userId)) {
            throw new BaseException(com.accompany.base.BasicEnum.USER_NOT_LOGIN);
        }

        // 查询反馈列表
        List<Feedback> feedbacks = feedbackMapper.findByUserId(userId);

        // 转换为VO
        return feedbacks.stream().map(feedback -> {
            FeedbackVo vo = new FeedbackVo();
            vo.setId(feedback.getId());
            vo.setContent(feedback.getContent());
            vo.setContact(feedback.getContact());
            vo.setReply(feedback.getReply());
            vo.setReplyTime(feedback.getReplyTime());
            vo.setTime(feedback.getTime());

            // 转换状态
            if ("pending".equals(feedback.getStatus())) {
                vo.setStatus(0); // 待处理
            } else if ("processed".equals(feedback.getStatus())) {
                vo.setStatus(1); // 已处理
            }

            // 处理图片列表
            if (StringUtils.hasText(feedback.getImages())) {
                // 将 JSON 数组字符串转换为列表
                List<String> images = JSONUtil.toList(feedback.getImages(), String.class);
                vo.setImages(images);
            }

            return vo;
        }).collect(Collectors.toList());
    }
}