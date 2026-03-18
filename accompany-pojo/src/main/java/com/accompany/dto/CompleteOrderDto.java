package com.accompany.dto;

import lombok.Data;

import java.util.List;

/**
 * 完成服务DTO
 */
@Data
public class CompleteOrderDto {
    /** 服务内容 */
    private String serviceContent;
    /** 图片列表 */
    private List<String> images;
}