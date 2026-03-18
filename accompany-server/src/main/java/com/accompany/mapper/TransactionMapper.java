package com.accompany.mapper;

import com.accompany.entity.TransactionRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 交易记录 Mapper
 */
@Mapper
public interface TransactionMapper {

    /**
     * 根据用户ID查询交易记录列表
     */
    List<TransactionRecord> findByUserId(@Param("userId") Long userId);

    /**
     * 根据用户ID和类型查询交易记录列表
     */
    List<TransactionRecord> findByUserIdAndType(@Param("userId") Long userId, @Param("type") String type);
}