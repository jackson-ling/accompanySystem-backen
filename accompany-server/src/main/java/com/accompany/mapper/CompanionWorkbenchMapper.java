package com.accompany.mapper;

import com.accompany.entity.CompanionIncome;
import com.accompany.entity.CompanionProfile;
import com.accompany.entity.CompanionStatistics;
import com.accompany.entity.Orders;
import com.accompany.vo.AvailableOrderVo;
import com.accompany.vo.CompanionStatisticsVo;
import com.accompany.vo.OrderVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 陪诊师工作台模块Mapper
 */
@Mapper
public interface CompanionWorkbenchMapper {

    /**
     * 获取陪诊师统计数据
     * @param companionId 陪诊师ID
     * @return 统计数据
     */
    CompanionStatisticsVo selectStatistics(@Param("companionId") Long companionId);

    /**
     * 获取陪诊师个人信息
     * @param userId 用户ID
     * @return 陪诊师信息
     */
    CompanionProfile selectProfile(@Param("userId") Long userId);

    /**
     * 更新陪诊师个人信息
     * @param profile 陪诊师信息
     */
    void updateProfile(@Param("profile") CompanionProfile profile);

    /**
     * 更新陪诊师在线状态
     * @param companionId 陪诊师ID
     * @param isOnline 是否在线
     */
    void updateOnlineStatus(@Param("companionId") Long companionId, @Param("isOnline") Integer isOnline);

    /**
     * 获取可接订单列表（抢单大厅）
     * @param offset 偏移量
     * @param pageSize 每页数量
     * @return 订单列表
     */
    List<AvailableOrderVo> selectAvailableOrders(@Param("offset") Integer offset, @Param("pageSize") Integer pageSize);

    /**
     * 统计可接订单总数
     * @return 总数
     */
    Integer countAvailableOrders();

    /**
     * 获取陪诊师订单列表
     * @param companionId 陪诊师ID
     * @param status 订单状态
     * @param offset 偏移量
     * @param pageSize 每页数量
     * @return 订单列表
     */
    List<Orders> selectCompanionOrders(@Param("companionId") Long companionId, @Param("status") Integer status, 
                                        @Param("offset") Integer offset, @Param("pageSize") Integer pageSize);

    /**
     * 统计陪诊师订单总数
     * @param companionId 陪诊师ID
     * @param status 订单状态
     * @return 总数
     */
    Integer countCompanionOrders(@Param("companionId") Long companionId, @Param("status") Integer status);

    /**
     * 更新陪诊师ID
     * @param orderId 订单ID
     * @param companionId 陪诊师ID
     */
    void updateOrderCompanionId(@Param("orderId") Long orderId, @Param("companionId") Long companionId);

    /**
     * 更新订单状态
     * @param orderId 订单ID
     * @param status 订单状态
     */
    void updateOrderStatus(@Param("orderId") Long orderId, @Param("status") Integer status);

    /**
     * 获取收入明细
     * @param companionId 陪诊师ID
     * @param offset 偏移量
     * @param pageSize 每页数量
     * @return 收入记录列表
     */
    List<CompanionIncome> selectIncomeRecords(@Param("companionId") Long companionId, 
                                               @Param("offset") Integer offset, @Param("pageSize") Integer pageSize);

    /**
     * 统计收入记录总数
     * @param companionId 陪诊师ID
     * @return 总数
     */
    Integer countIncomeRecords(@Param("companionId") Long companionId);

    /**
     * 根据订单ID获取订单详情（陪诊师视角）
     * @param orderId 订单ID
     * @return 订单详情
     */
    OrderVo selectCompanionOrderById(@Param("orderId") Long orderId);
}