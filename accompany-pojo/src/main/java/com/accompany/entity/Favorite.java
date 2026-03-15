package com.accompany.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Favorite {
    /** 主键ID */
    private Long id;

    /** 用户ID */
    private Long userId;

    /** 收藏类型 */
    private String type;

    /** 收藏项目ID */
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