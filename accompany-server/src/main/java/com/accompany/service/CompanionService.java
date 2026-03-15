package com.accompany.service;

import com.accompany.vo.CompanionCommentListVo;
import com.accompany.vo.CompanionListVo;
import com.accompany.vo.CompanionVo;
import com.accompany.vo.TimeSlotVo;

import java.util.List;

/**
 * 陪诊师模块Service
 */
public interface CompanionService {

    /**
     * 获取陪诊师列表（支持筛选、排序、分页）
     * @param gender 性别筛选（male/female，可选）
     * @param service 服务项目ID（可选）
     * @param keyword 搜索关键词（可选）
     * @param sort 排序方式（score_desc/orders_desc，可选）
     * @param page 页码，默认1
     * @param pageSize 每页数量，默认10
     * @return 陪诊师列表（带分页）
     */
    CompanionListVo getCompanions(String gender, Long service, String keyword, String sort, Integer page, Integer pageSize);

    /**
     * 获取陪诊师详情
     * @param id 陪诊师ID
     * @return 陪诊师详情
     */
    CompanionVo getCompanionById(Long id);

    /**
     * 获取陪诊师评价列表（支持分页）
     * @param id 陪诊师ID
     * @param page 页码，默认1
     * @param pageSize 每页数量，默认10
     * @return 评价列表（带分页）
     */
    CompanionCommentListVo getCompanionComments(Long id, Integer page, Integer pageSize);

    /**
     * 收藏/取消收藏陪诊师
     * @param id 陪诊师ID
     * @return 操作结果
     */
    Boolean toggleFavorite(Long id);

    /**
     * 获取陪诊师可用时间段
     * @param id 陪诊师ID
     * @param date 日期，格式：2024-01-15（可选，默认今天）
     * @return 时间段列表
     */
    List<TimeSlotVo> getAvailableTimes(Long id, String date);
}