package com.accompany.mapper;

import com.accompany.entity.CompanionProfile;
import com.accompany.entity.Favorite;
import com.accompany.entity.OrderReview;
import com.accompany.vo.CompanionCommentVo;
import com.accompany.vo.CompanionVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 陪诊师模块Mapper
 */
@Mapper
public interface CompanionMapper {

    /**
     * 获取陪诊师列表（支持筛选、排序、分页）
     * @param gender 性别筛选（male/female，可选）
     * @param service 服务项目ID（可选）
     * @param keyword 搜索关键词（可选）
     * @param sort 排序方式（score_desc/orders_desc，可选）
     * @param offset 偏移量
     * @param pageSize 每页数量
     * @param userId 当前用户ID（用于判断是否收藏）
     * @return 陪诊师列表
     */
    List<CompanionVo> selectCompanions(
            @Param("gender") String gender,
            @Param("service") Long service,
            @Param("keyword") String keyword,
            @Param("sort") String sort,
            @Param("offset") Integer offset,
            @Param("pageSize") Integer pageSize,
            @Param("userId") Long userId
    );

    /**
     * 统计陪诊师总数（支持筛选）
     * @param gender 性别筛选（可选）
     * @param service 服务项目ID（可选）
     * @param keyword 搜索关键词（可选）
     * @return 总数
     */
    Integer countCompanions(
            @Param("gender") String gender,
            @Param("service") Long service,
            @Param("keyword") String keyword
    );

    /**
     * 根据ID获取陪诊师详情
     * @param id 陪诊师ID
     * @param userId 当前用户ID（用于判断是否收藏）
     * @return 陪诊师详情
     */
    CompanionVo selectCompanionById(@Param("id") Long id, @Param("userId") Long userId);

    /**
     * 获取陪诊师评价列表（支持分页）
     * @param companionId 陪诊师ID
     * @param offset 偏移量
     * @param pageSize 每页数量
     * @return 评价列表
     */
    List<CompanionCommentVo> selectCompanionComments(
            @Param("companionId") Long companionId,
            @Param("offset") Integer offset,
            @Param("pageSize") Integer pageSize
    );

    /**
     * 统计陪诊师评价总数
     * @param companionId 陪诊师ID
     * @return 总数
     */
    Integer countCompanionComments(@Param("companionId") Long companionId);

    /**
     * 检查是否已收藏
     * @param userId 用户ID
     * @param itemId 陪诊师ID
     * @return 收藏记录
     */
    Favorite selectFavorite(@Param("userId") Long userId, @Param("itemId") Long itemId);

    /**
     * 添加收藏
     * @param favorite 收藏记录
     */
    void insertFavorite(@Param("favorite") Favorite favorite);

    /**
     * 删除收藏
     * @param userId 用户ID
     * @param itemId 陪诊师ID
     */
    void deleteFavorite(@Param("userId") Long userId, @Param("itemId") Long itemId);
}