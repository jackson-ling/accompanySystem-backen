package com.accompany.mapper;

import com.accompany.entity.ServiceCategory;
import com.accompany.entity.ServiceItem;
import com.accompany.vo.ServiceCategoryVo;
import com.accompany.vo.ServiceItemVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 服务模块Mapper
 */
@Mapper
public interface ServiceMapper {

    /**
     * 获取所有服务分类列表
     * @return 服务分类列表
     */
    List<ServiceCategoryVo> selectAllCategories();

    /**
     * 根据ID获取服务分类
     * @param id 分类ID
     * @return 服务分类
     */
    ServiceCategory selectCategoryById(@Param("id") Long id);

    /**
     * 获取服务项目列表（支持分页、筛选、排序）
     * @param categoryId 分类ID（可选）
     * @param type 服务类型（companion/agency，可选）
     * @param keyword 搜索关键词（可选）
     * @param sort 排序方式（price_asc/price_desc/sales_desc，可选）
     * @param offset 偏移量
     * @param pageSize 每页数量
     * @return 服务项目列表
     */
    List<ServiceItemVo> selectServiceItems(
            @Param("categoryId") Long categoryId,
            @Param("type") String type,
            @Param("keyword") String keyword,
            @Param("sort") String sort,
            @Param("offset") Integer offset,
            @Param("pageSize") Integer pageSize
    );

    /**
     * 统计服务项目总数（支持筛选）
     * @param categoryId 分类ID（可选）
     * @param type 服务类型（可选）
     * @param keyword 搜索关键词（可选）
     * @return 总数
     */
    Integer countServiceItems(
            @Param("categoryId") Long categoryId,
            @Param("type") String type,
            @Param("keyword") String keyword
    );

    /**
     * 根据ID获取服务详情
     * @param id 服务ID
     * @return 服务详情
     */
    ServiceItemVo selectServiceItemById(@Param("id") Long id);
}