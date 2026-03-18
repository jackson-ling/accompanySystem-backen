package com.accompany.mapper;

import com.accompany.entity.Feedback;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 反馈 Mapper
 */
@Mapper
public interface FeedbackMapper {

    /**
     * 插入反馈
     */
    void insert(Feedback feedback);

    /**
     * 根据用户ID查询反馈列表
     */
    List<Feedback> findByUserId(@Param("userId") Long userId);
}