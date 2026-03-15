package com.accompany.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrderReview {
    /** 主键ID */
    private Long id;

    /** 订单ID */
    private Long orderId;

    /** 用户ID */
    private Long userId;

    /** 陪护员ID */
    private Long companionId;

    /** 用户姓名 */
    private String userName;

    /** 用户头像 */
    private String userAvatar;

    /** 评分 */
    private BigDecimal score;

    /** 评论内容 */
    private String content;

    /** 评论图片（JSON或逗号分隔） */
    private String images;

    /** 评论时间 */
    private LocalDateTime time;
}