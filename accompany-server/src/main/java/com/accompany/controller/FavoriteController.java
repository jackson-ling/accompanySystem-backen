package com.accompany.controller;

import com.accompany.base.Result;
import com.accompany.entity.Favorite;
import com.accompany.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户收藏模块Controller
 */
@RestController
@RequestMapping("/user/favorites")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    /**
     * 获取收藏列表
     * GET /user/favorites
     *
     * @return 收藏列表（陪诊师）
     */
    @GetMapping
    public Result<List<Favorite>> getFavorites() {
        List<Favorite> favorites = favoriteService.getFavorites();
        return Result.success(favorites);
    }

    /**
     * 添加收藏
     * POST /user/favorites
     *
     * @param itemId 陪诊师ID
     * @return 收藏记录
     */
    @PostMapping
    public Result<Favorite> addFavorite(@RequestParam Long itemId) {
        Favorite favorite = favoriteService.addFavorite(itemId);
        return Result.success(favorite);
    }

    /**
     * 取消收藏
     * DELETE /user/favorites/{id}
     *
     * @param id 收藏ID
     * @return 操作结果
     */
    @DeleteMapping("/{id}")
    public Result<Void> removeFavorite(@PathVariable Long id) {
        favoriteService.removeFavorite(id);
        return Result.success();
    }
}
