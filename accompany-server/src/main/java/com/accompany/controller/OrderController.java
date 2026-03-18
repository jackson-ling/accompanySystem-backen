package com.accompany.controller;

import com.accompany.base.Result;
import com.accompany.dto.CommentOrderDto;
import com.accompany.dto.CreateOrderDto;
import com.accompany.dto.PayOrderDto;
import com.accompany.dto.RefundOrderDto;
import com.accompany.service.OrderService;
import com.accompany.vo.OrderListVo;
import com.accompany.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 订单模块Controller
 */
@RestController
@RequestMapping("/user/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 创建订单
     * POST /user/orders
     *
     * @param createOrderDto 创建订单DTO
     * @return 订单ID
     */
    @PostMapping
    public Result<Long> createOrder(@RequestBody CreateOrderDto createOrderDto) {
        Long orderId = orderService.createOrder(createOrderDto);
        return Result.success(orderId);
    }

    /**
     * 获取用户订单列表（支持状态筛选、分页）
     * GET /user/orders
     *
     * @param status 订单状态（可选）
     * @param page 页码，默认1
     * @param pageSize 每页数量，默认10
     * @return 订单列表（带分页）
     */
    @GetMapping
    public Result<OrderListVo> getUserOrders(
            @RequestParam(required = false) Integer status,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize
    ) {
        OrderListVo result = orderService.getUserOrders(status, page, pageSize);
        return Result.success(result);
    }

    /**
     * 获取订单详情
     * GET /user/orders/{id}
     *
     * @param id 订单ID
     * @return 订单详情
     */
    @GetMapping("/{id}")
    public Result<OrderVo> getOrderById(@PathVariable Long id) {
        OrderVo orderVo = orderService.getOrderById(id);
        return Result.success(orderVo);
    }

    /**
     * 取消订单
     * PUT /user/orders/{id}/cancel
     *
     * @param id 订单ID
     */
    @PutMapping("/{id}/cancel")
    public Result cancelOrder(@PathVariable Long id) {
        orderService.cancelOrder(id);
        return Result.success();
    }

    /**
     * 支付订单
     * POST /user/orders/{id}/pay
     *
     * @param id 订单ID
     * @param payOrderDto 支付订单DTO
     */
    @PostMapping("/{id}/pay")
    public Result payOrder(@PathVariable Long id, @RequestBody PayOrderDto payOrderDto) {
        orderService.payOrder(id, payOrderDto);
        return Result.success();
    }

    /**
     * 确认完成
     * PUT /user/orders/{id}/confirm
     *
     * @param id 订单ID
     */
    @PutMapping("/{id}/confirm")
    public Result confirmOrder(@PathVariable Long id) {
        orderService.confirmOrder(id);
        return Result.success();
    }

    /**
     * 申请退款
     * POST /user/orders/{id}/refund
     *
     * @param id 订单ID
     * @param refundOrderDto 申请退款DTO
     */
    @PostMapping("/{id}/refund")
    public Result refundOrder(@PathVariable Long id, @RequestBody RefundOrderDto refundOrderDto) {
        orderService.refundOrder(id, refundOrderDto);
        return Result.success();
    }

    /**
     * 评价订单
     * POST /user/orders/{id}/comment
     *
     * @param id 订单ID
     * @param commentOrderDto 评价订单DTO
     */
    @PostMapping("/{id}/comment")
    public Result commentOrder(@PathVariable Long id, @RequestBody CommentOrderDto commentOrderDto) {
        orderService.commentOrder(id, commentOrderDto);
        return Result.success();
    }
}