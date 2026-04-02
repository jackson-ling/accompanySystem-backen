package com.accompany.mapper;

import com.accompany.entity.Orders;
import com.accompany.vo.OrderVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单模块Mapper
 */
@Mapper
public interface OrderMapper {

    /**
     * 创建订单
     * @param orders 订单实体
     */
    void insertOrder(@Param("orders") Orders orders);

    /**
     * 获取用户订单列表（支持状态筛选、分页）
     * @param userId 用户ID
     * @param status 订单状态（可选）
     * @param offset 偏移量
     * @param pageSize 每页数量
     * @return 订单列表
     */
    List<OrderVo> selectUserOrders(
            @Param("userId") Long userId,
            @Param("status") Integer status,
            @Param("offset") Integer offset,
            @Param("pageSize") Integer pageSize
    );

    /**
     * 统计用户订单总数
     * @param userId 用户ID
     * @param status 订单状态（可选）
     * @return 总数
     */
    Integer countUserOrders(
            @Param("userId") Long userId,
            @Param("status") Integer status
    );

    /**
     * 根据ID获取订单详情
     * @param id 订单ID
     * @param userId 用户ID
     * @return 订单详情
     */
    OrderVo selectOrderById(@Param("id") Long id, @Param("userId") Long userId);

    /**
     * 更新订单状态
     * @param id 订单ID
     * @param status 订单状态
     */
    void updateOrderStatus(@Param("id") Long id, @Param("status") Integer status);

    /**
     * 更新订单支付信息
     * @param id 订单ID
     * @param payMethod 支付方式
     */
    void updateOrderPay(@Param("id") Long id, @Param("payMethod") Integer payMethod);

    /**
     * 更新订单退款信息
     * @param id 订单ID
     * @param refundReason 退款原因
     */
    void updateOrderRefund(@Param("id") Long id, @Param("refundReason") String refundReason);

    /**
     * 根据订单号获取订单
     * @param orderNo 订单号
     * @return 订单
     */
    Orders selectOrderByOrderNo(@Param("orderNo") String orderNo);

    /**
     * 根据订单ID获取订单实体
     * @param id 订单ID
     * @return 订单实体
     */
    Orders selectOrderEntityById(@Param("id") Long id);

    /**
     * 更新陪诊师ID
     * @param id 订单ID
     * @param companionId 陪诊师ID
     */
    void updateOrderCompanionId(@Param("id") Long id, @Param("companionId") Long companionId);

    /**
     * 更新订单完成时间
     * @param id 订单ID
     */
    void updateOrderCompleteTime(@Param("id") Long id);

    /**
     * 查询陪诊师在指定日期的订单
     * @param companionId 陪诊师ID
     * @param date 日期（yyyy-MM-dd格式）
     * @return 订单列表
     */
    List<Orders> selectCompanionOrdersByDate(@Param("companionId") Long companionId, @Param("date") String date);

    /**
     * 插入订单评价
     * @param orderId 订单ID
     * @param userId 用户ID
     * @param companionId 陪诊师ID
     * @param userName 用户姓名
     * @param userAvatar 用户头像
     * @param score 评分
     * @param content 评价内容
     * @param images 评价图片
     */
    void insertOrderReview(
            @Param("orderId") Long orderId,
            @Param("userId") Long userId,
            @Param("companionId") Long companionId,
            @Param("userName") String userName,
            @Param("userAvatar") String userAvatar,
            @Param("score") java.math.BigDecimal score,
            @Param("content") String content,
            @Param("images") String images
    );

    /**
     * 更新陪诊师评分和评论数
     * @param companionId 陪诊师ID
     */
    void updateCompanionScore(@Param("companionId") Long companionId);

    /**
     * 检查订单是否已评价
     * @param orderId 订单ID
     * @return 评价记录数
     */
    Integer checkOrderReviewed(@Param("orderId") Long orderId);
}