package com.accompany.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 陪诊师评价VO
 */
@Data
public class CompanionCommentVo {
    /** 主键ID */
    private Long id;

    /** 用户ID */
    private Long userId;

    /** 用户名 */
    private String userName;

    /** 用户头像 */
    private String userAvatar;

    /** 评分 */
    private BigDecimal score;

    /** 评价内容 */
    private String content;

    /** 评价图片列表（JSON字符串格式） */
    private String images;

    /** 评价时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime time;
}