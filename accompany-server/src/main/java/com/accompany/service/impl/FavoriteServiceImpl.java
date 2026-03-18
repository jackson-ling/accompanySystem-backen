package com.accompany.service.impl;

import com.accompany.base.BasicEnum;
import com.accompany.entity.Favorite;
import com.accompany.exception.BaseException;
import com.accompany.mapper.CompanionMapper;
import com.accompany.mapper.FavoriteMapper;
import com.accompany.service.FavoriteService;
import com.accompany.util.UserThreadLocal;
import com.accompany.vo.CompanionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 收藏模块Service实现
 */
@Service
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    private FavoriteMapper favoriteMapper;

    @Autowired
    private CompanionMapper companionMapper;

    @Override
    public List<Favorite> getFavorites() {
        Long userId = UserThreadLocal.getCurrentId();
        if (ObjectUtils.isEmpty(userId)) {
            throw new BaseException(BasicEnum.USER_NOT_LOGIN);
        }
        return favoriteMapper.selectByUserId(userId);
    }

    @Override
    @Transactional
    public Favorite addFavorite(Long itemId) {
        Long userId = UserThreadLocal.getCurrentId();
        if (ObjectUtils.isEmpty(userId)) {
            throw new BaseException(BasicEnum.USER_NOT_LOGIN);
        }

        // 检查参数
        if (ObjectUtils.isEmpty(itemId)) {
            throw new BaseException(BasicEnum.PARAM_ERROR);
        }

        // 检查是否已收藏
        Favorite existingFavorite = favoriteMapper.selectByUserIdAndItemId(userId, itemId);
        if (existingFavorite != null) {
            throw new BaseException(BasicEnum.ALREADY_FAVORITED);
        }

        // 获取陪诊师信息
        CompanionVo companionVo = companionMapper.selectCompanionById(itemId, userId);
        if (ObjectUtils.isEmpty(companionVo)) {
            throw new BaseException(BasicEnum.COMPANION_NOT_EXIST);
        }

        // 创建收藏记录
        Favorite favorite = new Favorite();
        favorite.setUserId(userId);
        favorite.setItemId(itemId);
        favorite.setName(companionVo.getName());
        favorite.setAvatar(companionVo.getAvatar());
        favorite.setDescription(companionVo.getIntro());
        favorite.setTime(LocalDateTime.now());

        favoriteMapper.insert(favorite);
        return favorite;
    }

    @Override
    @Transactional
    public void removeFavorite(Long id) {
        Long userId = UserThreadLocal.getCurrentId();
        if (ObjectUtils.isEmpty(userId)) {
            throw new BaseException(BasicEnum.USER_NOT_LOGIN);
        }

        // 检查收藏记录是否存在
        Favorite favorite = favoriteMapper.selectById(id);
        if (ObjectUtils.isEmpty(favorite)) {
            throw new BaseException(BasicEnum.NOT_EXIST);
        }

        // 检查是否是当前用户的收藏
        if (!favorite.getUserId().equals(userId)) {
            throw new BaseException(BasicEnum.NO_PERMISSION);
        }

        favoriteMapper.deleteById(id);
    }
}
