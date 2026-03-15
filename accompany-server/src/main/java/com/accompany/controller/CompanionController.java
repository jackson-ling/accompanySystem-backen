package com.accompany.controller;

import com.accompany.base.Result;
import com.accompany.service.CompanionService;
import com.accompany.vo.CompanionCommentListVo;
import com.accompany.vo.CompanionListVo;
import com.accompany.vo.CompanionVo;
import com.accompany.vo.TimeSlotVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 陪诊师模块Controller
 */
@RestController
@RequestMapping("/companions")
public class CompanionController {

    @Autowired
    private CompanionService companionService;

    /**
     * 获取陪诊师列表（支持筛选、排序、分页）
     * GET /companions
     *
     * @param gender 性别筛选（male/female，可选）
     * @param service 服务项目ID（可选）
     * @param keyword 搜索关键词（可选）
     * @param sort 排序方式（score_desc/orders_desc，可选）
     * @param page 页码，默认1
     * @param pageSize 每页数量，默认10
     */
    @GetMapping
    public Result<CompanionListVo> getCompanions(
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) Long service,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String sort,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize
    ) {
        CompanionListVo result = companionService.getCompanions(gender, service, keyword, sort, page, pageSize);
        return Result.success(result);
    }

    /**
     * 获取陪诊师详情
     * GET /companions/{id}
     *
     * @param id 陪诊师ID
     */
    @GetMapping("/{id}")
    public Result<CompanionVo> getCompanionById(@PathVariable Long id) {
        CompanionVo companionVo = companionService.getCompanionById(id);
        return Result.success(companionVo);
    }

    /**
     * 获取陪诊师评价列表（支持分页）
     * GET /companions/{id}/comments
     *
     * @param id 陪诊师ID
     * @param page 页码，默认1
     * @param pageSize 每页数量，默认10
     */
    @GetMapping("/{id}/comments")
    public Result<CompanionCommentListVo> getCompanionComments(
            @PathVariable Long id,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize
    ) {
        CompanionCommentListVo result = companionService.getCompanionComments(id, page, pageSize);
        return Result.success(result);
    }

    /**
     * 收藏/取消收藏陪诊师
     * POST /companions/{id}/favorite
     *
     * @param id 陪诊师ID
     * @return true-已收藏，false-已取消收藏
     */
    @PostMapping("/{id}/favorite")
    public Result<Boolean> toggleFavorite(@PathVariable Long id) {
        Boolean isFavorite = companionService.toggleFavorite(id);
        return Result.success(isFavorite);
    }

    /**
     * 获取陪诊师可用时间段
     * GET /companions/{id}/available-times
     *
     * @param id 陪诊师ID
     * @param date 日期，格式：2024-01-15（可选，默认今天）
     */
    @GetMapping("/{id}/available-times")
    public Result<List<TimeSlotVo>> getAvailableTimes(
            @PathVariable Long id,
            @RequestParam(required = false) String date
    ) {
        List<TimeSlotVo> timeSlots = companionService.getAvailableTimes(id, date);
        return Result.success(timeSlots);
    }
}