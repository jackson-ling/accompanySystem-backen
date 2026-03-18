package com.accompany.vo;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 反馈返回对象
 */
@Data
public class FeedbackVo {
    /** 主键ID */
    private Long id;

    /** 反馈内容 */
    private String content;

    /** 图片URL列表 */
    private List<String> images;

    /** 联系方式 */
    private String contact;

    /** 状态: 0-待处理, 1-已处理 */
    private Integer status;

    /** 回复内容 */
    private String reply;

    /** 回复时间 */
    private LocalDateTime replyTime;

    /** 提交时间 */
    private LocalDateTime time;
}