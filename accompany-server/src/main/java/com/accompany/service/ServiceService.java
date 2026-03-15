package com.accompany.service;

import com.accompany.vo.ServiceCategoryVo;
import com.accompany.vo.ServiceItemListVo;
import com.accompany.vo.ServiceItemVo;

import java.util.List;

/**
 * 服务模块Service
 */
public interface ServiceService {

    /**
     * 获取所有服务分类列表
     * @return 服务分类列表
     */
    List<ServiceCategoryVo> getCategories();

    /**
     * 获取服务项目列表（支持分页、筛选、排序）
     * @param categoryId 分类ID（可选）
     * @param type 服务类型（companion/agency，可选）
     * @param keyword 搜索关键词（可选）
     * @param sort 排序方式（price_asc/price_desc/sales_desc，可选）
     * @param page 页码，默认1
     * @param pageSize 每页数量，默认10
     * @return 服务项目列表（带分页）
     */
    ServiceItemListVo getServiceItems(Long categoryId, String type, String keyword, String sort, Integer page, Integer pageSize);

    /**
     * 获取服务详情
     * @param id 服务ID
     * @return 服务详情
     */
    ServiceItemVo getServiceItemById(Long id);
}