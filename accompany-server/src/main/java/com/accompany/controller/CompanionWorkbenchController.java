package com.accompany.controller;

import com.accompany.base.Result;
import com.accompany.dto.CompleteOrderDto;
import com.accompany.dto.RejectOrderDto;
import com.accompany.dto.UpdateCompanionProfileDto;
import com.accompany.dto.UpdateOnlineStatusDto;
import com.accompany.dto.UpdateOrderStatusDto;
import com.accompany.service.CompanionWorkbenchService;
import com.accompany.vo.AvailableOrderListVo;
import com.accompany.vo.CompanionStatisticsVo;
import com.accompany.vo.CompanionVo;
import com.accompany.vo.IncomeRecordListVo;
import com.accompany.vo.OrderListVo;
import com.accompany.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 陪诊师工作台模块Controller
 */
@RestController
@RequestMapping("/companion")
public class CompanionWorkbenchController {

    @Autowired
    private CompanionWorkbenchService companionWorkbenchService;

    /**
     * 获取统计数据
     * GET /companion/statistics
     */
    @GetMapping("/statistics")
    public Result<CompanionStatisticsVo> getStatistics() {
        CompanionStatisticsVo statistics = companionWorkbenchService.getStatistics();
        return Result.success(statistics);
    }

    /**
     * 获取个人信息
     * GET /companion/profile
     */
    @GetMapping("/profile")
    public Result<CompanionVo> getProfile() {
        CompanionVo companionVo = companionWorkbenchService.getProfile();
        return Result.success(companionVo);
    }

    /**
     * 更新个人信息
     * PUT /companion/profile
     */
    @PutMapping("/profile")
    public Result<CompanionVo> updateProfile(@RequestBody UpdateCompanionProfileDto updateCompanionProfileDto) {
        CompanionVo companionVo = companionWorkbenchService.updateProfile(updateCompanionProfileDto);
        return Result.success(companionVo);
    }

    /**
     * 获取可接订单列表（抢单大厅）
     * GET /companion/available-orders
     */
    @GetMapping("/available-orders")
    public Result<AvailableOrderListVo> getAvailableOrders(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize
    ) {
        AvailableOrderListVo result = companionWorkbenchService.getAvailableOrders(page, pageSize);
        return Result.success(result);
    }

    /**
     * 接单
     * PUT /companion/orders/{orderId}/accept
     */
    @PutMapping("/orders/{orderId}/accept")
    public Result acceptOrder(@PathVariable Long orderId) {
        companionWorkbenchService.acceptOrder(orderId);
        return Result.success();
    }

    /**
     * 拒单
     * PUT /companion/orders/{orderId}/reject
     */
    @PutMapping("/orders/{orderId}/reject")
    public Result rejectOrder(@PathVariable Long orderId, @RequestBody RejectOrderDto rejectOrderDto) {
        companionWorkbenchService.rejectOrder(orderId, rejectOrderDto);
        return Result.success();
    }

    /**
     * 开始服务
     * PUT /companion/orders/{id}/start
     */
    @PutMapping("/orders/{id}/start")
    public Result startService(@PathVariable Long id) {
        companionWorkbenchService.startService(id);
        return Result.success();
    }

    /**
     * 完成服务
     * PUT /companion/orders/{id}/complete
     */
    @PutMapping("/orders/{id}/complete")
    public Result completeService(@PathVariable Long id, @RequestBody CompleteOrderDto completeOrderDto) {
        companionWorkbenchService.completeService(id, completeOrderDto);
        return Result.success();
    }

    /**
     * 更新订单状态
     * PUT /companion/orders/{id}/status
     */
    @PutMapping("/orders/{id}/status")
    public Result updateOrderStatus(@PathVariable Long id, @RequestBody UpdateOrderStatusDto updateOrderStatusDto) {
        companionWorkbenchService.updateOrderStatus(id, updateOrderStatusDto.getStatus());
        return Result.success();
    }

    /**
     * 获取订单列表
     * GET /companion/orders
     */
    @GetMapping("/orders")
    public Result<OrderListVo> getOrders(
            @RequestParam(required = false) Integer status,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize
    ) {
        OrderListVo result = companionWorkbenchService.getOrders(status, page, pageSize);
        return Result.success(result);
    }

    /**
     * 获取订单详情
     * GET /companion/orders/{id}
     */
    @GetMapping("/orders/{id}")
    public Result<OrderVo> getOrderById(@PathVariable Long id) {
        OrderVo orderVo = companionWorkbenchService.getOrderById(id);
        return Result.success(orderVo);
    }

    /**
     * 获取收入明细
     * GET /companion/income
     */
    @GetMapping("/income")
    public Result<IncomeRecordListVo> getIncome(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize
    ) {
        IncomeRecordListVo result = companionWorkbenchService.getIncome(page, pageSize);
        return Result.success(result);
    }

    /**
     * 更新在线状态
     * POST /companion/status
     */
    @PostMapping("/status")
    public Result updateOnlineStatus(@RequestBody UpdateOnlineStatusDto updateOnlineStatusDto) {
        companionWorkbenchService.updateOnlineStatus(updateOnlineStatusDto);
        return Result.success();
    }
}