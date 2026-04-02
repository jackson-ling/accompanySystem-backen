package com.accompany.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Favorite {
    /** 主键ID */
    private Long id;

    /** 用户ID */
    private Long userId;

    /** 类型: companion-陪诊师, service-服务 */
    private String type;

    /** 收藏项目ID（陪诊师ID） */
    private Long itemId;

    /** 名称 */
    private String name;

    /** 头像 */
    private String avatar;

    /** 描述 */
    private String description;

    /** 收藏时间 */
    private LocalDateTime time;
}