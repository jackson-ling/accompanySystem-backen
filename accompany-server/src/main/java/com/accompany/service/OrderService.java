package com.accompany.service;

import com.accompany.dto.CommentOrderDto;
import com.accompany.dto.CreateOrderDto;
import com.accompany.dto.PayOrderDto;
import com.accompany.dto.RefundOrderDto;
import com.accompany.vo.OrderListVo;
import com.accompany.vo.OrderVo;

/**
 * 订单模块Service
 */
public interface OrderService {

    /**
     * 创建订单
     * @param createOrderDto 创建订单DTO
     * @return 订单ID
     */
    Long createOrder(CreateOrderDto createOrderDto);

    /**
     * 获取用户订单列表（支持状态筛选、分页）
     * @param status 订单状态（可选）
     * @param page 页码，默认1
     * @param pageSize 每页数量，默认10
     * @return 订单列表（带分页）
     */
    OrderListVo getUserOrders(Integer status, Integer page, Integer pageSize);

    /**
     * 获取订单详情
     * @param id 订单ID
     * @return 订单详情
     */
    OrderVo getOrderById(Long id);

    /**
     * 取消订单
     * @param id 订单ID
     */
    void cancelOrder(Long id);

    /**
     * 支付订单
     * @param id 订单ID
     * @param payOrderDto 支付订单DTO
     */
    void payOrder(Long id, PayOrderDto payOrderDto);

    /**
     * 确认完成
     * @param id 订单ID
     */
    void confirmOrder(Long id);

    /**
     * 申请退款
     * @param id 订单ID
     * @param refundOrderDto 申请退款DTO
     */
    void refundOrder(Long id, RefundOrderDto refundOrderDto);

    /**
     * 评价订单
     * @param id 订单ID
     * @param commentOrderDto 评价订单DTO
     */
    void commentOrder(Long id, CommentOrderDto commentOrderDto);
}