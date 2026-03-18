package com.accompany.service;

import com.accompany.dto.CompleteOrderDto;
import com.accompany.dto.RejectOrderDto;
import com.accompany.dto.UpdateCompanionProfileDto;
import com.accompany.dto.UpdateOnlineStatusDto;
import com.accompany.entity.CompanionProfile;
import com.accompany.entity.Orders;
import com.accompany.vo.AvailableOrderListVo;
import com.accompany.vo.CompanionStatisticsVo;
import com.accompany.vo.CompanionVo;
import com.accompany.vo.IncomeRecordListVo;
import com.accompany.vo.OrderListVo;
import com.accompany.vo.OrderVo;

/**
 * 陪诊师工作台模块Service接口
 */
public interface CompanionWorkbenchService {

    /**
     * 获取统计数据
     * @return 统计数据
     */
    CompanionStatisticsVo getStatistics();

    /**
     * 获取个人信息
     * @return 个人信息
     */
    CompanionVo getProfile();

    /**
     * 更新个人信息
     * @param updateCompanionProfileDto 更新DTO
     * @return 更新后的个人信息
     */
    CompanionVo updateProfile(UpdateCompanionProfileDto updateCompanionProfileDto);

    /**
     * 获取可接订单列表（抢单大厅）
     * @param page 页码
     * @param pageSize 每页数量
     * @return 订单列表
     */
    AvailableOrderListVo getAvailableOrders(Integer page, Integer pageSize);

    /**
     * 接单
     * @param orderId 订单ID
     */
    void acceptOrder(Long orderId);

    /**
     * 拒单
     * @param orderId 订单ID
     * @param rejectOrderDto 拒单DTO
     */
    void rejectOrder(Long orderId, RejectOrderDto rejectOrderDto);

    /**
     * 开始服务
     * @param orderId 订单ID
     */
    void startService(Long orderId);

    /**
     * 完成服务
     * @param orderId 订单ID
     * @param completeOrderDto 完成服务DTO
     */
    void completeService(Long orderId, CompleteOrderDto completeOrderDto);

    /**
     * 更新订单状态
     * @param orderId 订单ID
     * @param status 订单状态
     */
    void updateOrderStatus(Long orderId, Integer status);

    /**
     * 获取订单列表
     * @param status 订单状态
     * @param page 页码
     * @param pageSize 每页数量
     * @return 订单列表
     */
    OrderListVo getOrders(Integer status, Integer page, Integer pageSize);

    /**
     * 获取订单详情
     * @param orderId 订单ID
     * @return 订单详情
     */
    OrderVo getOrderById(Long orderId);

    /**
     * 获取收入明细
     * @param page 页码
     * @param pageSize 每页数量
     * @return 收入记录列表
     */
    IncomeRecordListVo getIncome(Integer page, Integer pageSize);

    /**
     * 更新在线状态
     * @param updateOnlineStatusDto 更新DTO
     */
    void updateOnlineStatus(UpdateOnlineStatusDto updateOnlineStatusDto);
}