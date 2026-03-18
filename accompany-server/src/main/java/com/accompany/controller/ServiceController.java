package com.accompany.controller;

import com.accompany.base.Result;
import com.accompany.service.ServiceService;
import com.accompany.vo.ServiceCategoryVo;
import com.accompany.vo.ServiceItemListVo;
import com.accompany.vo.ServiceItemVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 服务模块Controller
 */
@RestController
@RequestMapping("/services")
public class ServiceController {

    @Autowired
    private ServiceService serviceService;

    /**
     * 获取服务分类列表
     * GET /services/categories
     */
    @GetMapping("/categories")
    public Result<List<ServiceCategoryVo>> getCategories() {
        List<ServiceCategoryVo> categories = serviceService.getCategories();
        return Result.success(categories);
    }

    /**
     * 获取服务项目列表（支持分页、筛选、排序）
     * GET /services
     *
     * @param categoryId 分类ID（可选）
     * @param type 服务类型（companion/agency，可选）
     * @param keyword 搜索关键词（可选）
     * @param sort 排序方式（price_asc/price_desc/sales_desc，可选）
     * @param page 页码，默认1
     * @param pageSize 每页数量，默认10
     */
    @GetMapping
    public Result<ServiceItemListVo> getServiceItems(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String sort,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize
    ) {
        ServiceItemListVo result = serviceService.getServiceItems(categoryId, type, keyword, sort, page, pageSize);
        return Result.success(result);
    }

    /**
     * 获取服务详情
     * GET /services/{id}
     *
     * @param id 服务ID
     */
    @GetMapping("/{id}")
    public Result<ServiceItemVo> getServiceItemById(@PathVariable Long id) {
        ServiceItemVo serviceItemVo = serviceService.getServiceItemById(id);
        return Result.success(serviceItemVo);
    }
}