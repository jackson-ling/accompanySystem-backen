package com.accompany.service;

import com.accompany.dto.SubmitFeedbackDto;
import com.accompany.vo.FeedbackVo;

import java.util.List;

/**
 * 反馈服务接口
 */
public interface FeedbackService {

    /**
     * 提交反馈
     * @param submitFeedbackDto 提交反馈DTO
     */
    void submitFeedback(SubmitFeedbackDto submitFeedbackDto);

    /**
     * 获取反馈列表
     * @return 反馈列表
     */
    List<FeedbackVo> getFeedbackList();
}