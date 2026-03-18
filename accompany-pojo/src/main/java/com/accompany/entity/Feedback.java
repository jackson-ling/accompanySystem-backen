package com.accompany.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Feedback {
    /** 主键ID */
    private Long id;

    /** 用户ID */
    private Long userId;

    /** 内容 */
    private String content;

    /** 图片（JSON或逗号分隔） */
    private String images;

    /** 联系方式 */
    private String contact;

    /** 状态 */
    private String status;

    /** 回复 */
    private String reply;

    /** 回复时间 */
    private LocalDateTime replyTime;

    /** 创建时间 */
    private LocalDateTime time;
}