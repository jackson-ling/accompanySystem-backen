package com.accompany.service;

import com.accompany.entity.Favorite;

import java.util.List;

/**
 * 收藏模块Service接口
 */
public interface FavoriteService {

    /**
     * 获取收藏列表
     * @return 收藏列表（陪诊师）
     */
    List<Favorite> getFavorites();

    /**
     * 添加收藏
     * @param itemId 陪诊师ID
     * @return 收藏记录
     */
    Favorite addFavorite(Long itemId);

    /**
     * 取消收藏
     * @param id 收藏ID
     */
    void removeFavorite(Long id);
}
