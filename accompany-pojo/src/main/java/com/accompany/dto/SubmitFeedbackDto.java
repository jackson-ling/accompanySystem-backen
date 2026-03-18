package com.accompany.dto;

import lombok.Data;
import java.util.List;

/**
 * 提交反馈 DTO
 */
@Data
public class SubmitFeedbackDto {
    /** 反馈内容 */
    private String content;

    /** 图片URL列表 */
    private List<String> images;

    /** 联系方式 */
    private String contact;
}