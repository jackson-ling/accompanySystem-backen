package com.accompany.mapper;

import com.accompany.entity.Favorite;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 收藏模块Mapper
 */
@Mapper
public interface FavoriteMapper {

    /**
     * 根据用户ID查询收藏列表
     * @param userId 用户ID
     * @return 收藏列表
     */
    List<Favorite> selectByUserId(@Param("userId") Long userId);

    /**
     * 根据ID查询收藏记录
     * @param id 收藏ID
     * @return 收藏记录
     */
    Favorite selectById(@Param("id") Long id);

    /**
     * 检查是否已收藏
     * @param userId 用户ID
     * @param itemId 收藏项ID（陪诊师ID）
     * @return 收藏记录
     */
    Favorite selectByUserIdAndItemId(
            @Param("userId") Long userId,
            @Param("itemId") Long itemId
    );

    /**
     * 添加收藏
     * @param favorite 收藏记录
     * @return 插入的行数
     */
    int insert(Favorite favorite);

    /**
     * 删除收藏
     * @param id 收藏ID
     * @return 删除的行数
     */
    int deleteById(@Param("id") Long id);

    /**
     * 根据用户ID和项ID删除收藏
     * @param userId 用户ID
     * @param itemId 收藏项ID
     * @return 删除的行数
     */
    int deleteByUserIdAndItemId(
            @Param("userId") Long userId,
            @Param("itemId") Long itemId
    );
}
