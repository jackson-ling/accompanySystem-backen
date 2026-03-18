package com.accompany.mapper;

import com.accompany.entity.RechargeRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 充值记录 Mapper
 */
@Mapper
public interface RechargeMapper {

    /**
     * 插入充值记录
     */
    void insert(RechargeRecord rechargeRecord);

    /**
     * 根据用户ID查询充值记录列表
     */
    List<RechargeRecord> findByUserId(@Param("userId") Long userId);

    /**
     * 根据订单号查询充值记录
     */
    RechargeRecord findByOrderNo(@Param("orderNo") String orderNo);

    /**
     * 更新充值记录状态
     */
    void updateStatus(@Param("orderNo") String orderNo, @Param("payStatus") Integer payStatus);
}