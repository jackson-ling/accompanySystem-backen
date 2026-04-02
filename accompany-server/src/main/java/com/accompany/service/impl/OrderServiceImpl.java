package com.accompany.service.impl;

import com.accompany.base.BasicEnum;
import com.accompany.dto.CommentOrderDto;
import com.accompany.dto.CreateOrderDto;
import com.accompany.dto.PayOrderDto;
import com.accompany.dto.RefundOrderDto;
import com.accompany.entity.Orders;
import com.accompany.entity.Patient;
import com.accompany.exception.BaseException;
import com.accompany.mapper.CompanionMapper;
import com.accompany.mapper.OrderMapper;
import com.accompany.mapper.PatientMapper;
import com.accompany.mapper.ServiceMapper;
import com.accompany.service.OrderService;
import com.accompany.util.UserThreadLocal;
import com.accompany.vo.CompanionVo;
import com.accompany.vo.OrderListVo;
import com.accompany.vo.OrderVo;
import com.accompany.vo.ServiceItemVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 订单模块Service实现
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private ServiceMapper serviceMapper;

    @Autowired
    private PatientMapper patientMapper;

    @Autowired
    private CompanionMapper companionMapper;

    private static final DateTimeFormatter ORDER_NO_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    @Override
    @Transactional
    public Long createOrder(CreateOrderDto createOrderDto) {
        // 获取当前用户ID
        Long userId = UserThreadLocal.getCurrentId();
        if (ObjectUtils.isEmpty(userId)) {
            throw new BaseException(BasicEnum.USER_NOT_LOGIN);
        }

        // 验证服务项目是否存在
        ServiceItemVo serviceItem = serviceMapper.selectServiceItemById(createOrderDto.getServiceId());
        if (ObjectUtils.isEmpty(serviceItem)) {
            throw new BaseException(BasicEnum.SERVICE_NOT_EXIST);
        }

        // 验证就诊人是否存在且属于当前用户
        Patient patient = patientMapper.selectById(createOrderDto.getPatientId());
        if (ObjectUtils.isEmpty(patient) || !patient.getUserId().equals(userId)) {
            throw new BaseException(BasicEnum.PATIENT_NOT_EXIST);
        }

        // 如果指定了陪诊师，验证陪诊师是否存在
        if (!ObjectUtils.isEmpty(createOrderDto.getCompanionId())) {
            CompanionVo companion = companionMapper.selectCompanionById(
                    createOrderDto.getCompanionId(), userId);
            if (ObjectUtils.isEmpty(companion)) {
                throw new BaseException(BasicEnum.COMPANION_NOT_EXIST);
            }
        }

        // 验证接人方式
        if (createOrderDto.getPickupOption() == 2 && ObjectUtils.isEmpty(createOrderDto.getPickupAddress())) {
            throw new BaseException(BasicEnum.PICKUP_ADDRESS_NOT_EMPTY);
        }

        // 生成订单号
        String orderNo = "O" + LocalDateTime.now().format(ORDER_NO_FORMATTER);

        // 创建订单
        Orders orders = new Orders();
        orders.setOrderNo(orderNo);
        orders.setUserId(userId);
        orders.setCompanionId(createOrderDto.getCompanionId());
        orders.setPatientId(createOrderDto.getPatientId());
        orders.setServiceId(createOrderDto.getServiceId());
        orders.setServiceName(serviceItem.getName());
        orders.setServiceImage(serviceItem.getImage());
        orders.setHospital(createOrderDto.getHospital());
        orders.setDepartment(createOrderDto.getDepartment());
        orders.setAppointmentTime(createOrderDto.getAppointmentTime());
        orders.setPickupOption(createOrderDto.getPickupOption());
        orders.setPickupAddress(createOrderDto.getPickupAddress());
        orders.setRemarks(createOrderDto.getRemarks());
        orders.setAmount(serviceItem.getPrice());
        orders.setActualAmount(serviceItem.getPrice());
        orders.setStatus(0); // 待支付

        orderMapper.insertOrder(orders);

        return orders.getId();
    }

    @Override
    public OrderListVo getUserOrders(Integer status, Integer page, Integer pageSize) {
        // 获取当前用户ID
        Long userId = UserThreadLocal.getCurrentId();
        if (ObjectUtils.isEmpty(userId)) {
            throw new BaseException(BasicEnum.USER_NOT_LOGIN);
        }

        // 设置默认分页参数
        if (ObjectUtils.isEmpty(page) || page < 1) {
            page = 1;
        }
        if (ObjectUtils.isEmpty(pageSize) || pageSize < 1) {
            pageSize = 10;
        }

        // 计算偏移量
        Integer offset = (page - 1) * pageSize;

        // 查询订单列表
        java.util.List<OrderVo> list = orderMapper.selectUserOrders(userId, status, offset, pageSize);

        // 统计总数
        Integer total = orderMapper.countUserOrders(userId, status);

        // 封装返回结果
        OrderListVo result = new OrderListVo();
        result.setList(list);
        result.setTotal(total);
        result.setPage(page);
        result.setPageSize(pageSize);

        return result;
    }

    @Override
    public OrderVo getOrderById(Long id) {
        // 获取当前用户ID
        Long userId = UserThreadLocal.getCurrentId();
        if (ObjectUtils.isEmpty(userId)) {
            throw new BaseException(BasicEnum.USER_NOT_LOGIN);
        }

        if (ObjectUtils.isEmpty(id)) {
            throw new BaseException(BasicEnum.ORDER_ID_NOT_EMPTY);
        }

        OrderVo orderVo = orderMapper.selectOrderById(id, userId);
        if (ObjectUtils.isEmpty(orderVo)) {
            throw new BaseException(BasicEnum.ORDER_NOT_EXIST);
        }

        return orderVo;
    }

    @Override
    @Transactional
    public void cancelOrder(Long id) {
        // 获取当前用户ID
        Long userId = UserThreadLocal.getCurrentId();
        if (ObjectUtils.isEmpty(userId)) {
            throw new BaseException(BasicEnum.USER_NOT_LOGIN);
        }

        if (ObjectUtils.isEmpty(id)) {
            throw new BaseException(BasicEnum.ORDER_ID_NOT_EMPTY);
        }

        // 查询订单
        OrderVo orderVo = orderMapper.selectOrderById(id, userId);
        if (ObjectUtils.isEmpty(orderVo)) {
            throw new BaseException(BasicEnum.ORDER_NOT_EXIST);
        }

        // 只有待支付状态的订单可以取消
        if (orderVo.getStatus() != 0) {
            throw new BaseException(BasicEnum.ONLY_PENDING_PAY_CAN_CANCEL);
        }

        // 更新订单状态为已取消
        orderMapper.updateOrderStatus(id, 4);
    }

    @Override
    @Transactional
    public void payOrder(Long id, PayOrderDto payOrderDto) {
        // 获取当前用户ID
        Long userId = UserThreadLocal.getCurrentId();
        if (ObjectUtils.isEmpty(userId)) {
            throw new BaseException(BasicEnum.USER_NOT_LOGIN);
        }

        if (ObjectUtils.isEmpty(id)) {
            throw new BaseException(BasicEnum.ORDER_ID_NOT_EMPTY);
        }

        // 查询订单
        OrderVo orderVo = orderMapper.selectOrderById(id, userId);
        if (ObjectUtils.isEmpty(orderVo)) {
            throw new BaseException(BasicEnum.ORDER_NOT_EXIST);
        }

        // 只有待支付状态的订单可以支付
        if (orderVo.getStatus() != 0) {
            throw new BaseException(BasicEnum.ONLY_PENDING_PAY_CAN_PAY);
        }

        // 确定支付方式：1-微信, 2-支付宝, 3-余额
        Integer payMethod;
        if ("wechat".equals(payOrderDto.getPaymentMethod())) {
            payMethod = 1;
        } else if ("alipay".equals(payOrderDto.getPaymentMethod())) {
            payMethod = 2;
        } else if ("balance".equals(payOrderDto.getPaymentMethod())) {
            payMethod = 3;
        } else {
            throw new BaseException(BasicEnum.PAYMENT_METHOD_INVALID);
        }

        // 更新订单支付信息
        orderMapper.updateOrderPay(id, payMethod);

        // TODO: 调用支付接口（微信/支付宝统一下单）
        // TODO: 如果是余额支付，需要扣减用户余额
    }

    @Override
    @Transactional
    public void confirmOrder(Long id) {
        // 获取当前用户ID
        Long userId = UserThreadLocal.getCurrentId();
        if (ObjectUtils.isEmpty(userId)) {
            throw new BaseException(BasicEnum.USER_NOT_LOGIN);
        }

        if (ObjectUtils.isEmpty(id)) {
            throw new BaseException(BasicEnum.ORDER_ID_NOT_EMPTY);
        }

        // 查询订单
        OrderVo orderVo = orderMapper.selectOrderById(id, userId);
        if (ObjectUtils.isEmpty(orderVo)) {
            throw new BaseException(BasicEnum.ORDER_NOT_EXIST);
        }

        // 只有服务中状态的订单可以确认完成（状态 7=服务中）
        if (orderVo.getStatus() != 7) {
            throw new BaseException(BasicEnum.ONLY_SERVICING_CAN_CONFIRM);
        }

        // 更新订单状态为已完成
        orderMapper.updateOrderCompleteTime(id);
    }

    @Override
    @Transactional
    public void refundOrder(Long id, RefundOrderDto refundOrderDto) {
        // 获取当前用户ID
        Long userId = UserThreadLocal.getCurrentId();
        if (ObjectUtils.isEmpty(userId)) {
            throw new BaseException(BasicEnum.USER_NOT_LOGIN);
        }

        if (ObjectUtils.isEmpty(id)) {
            throw new BaseException(BasicEnum.ORDER_ID_NOT_EMPTY);
        }

        // 查询订单
        OrderVo orderVo = orderMapper.selectOrderById(id, userId);
        if (ObjectUtils.isEmpty(orderVo)) {
            throw new BaseException(BasicEnum.ORDER_NOT_EXIST);
        }

        // 只有待接单或服务中状态的订单可以申请退款（状态 1=待接单，状态 7=服务中）
        if (orderVo.getStatus() != 1 && orderVo.getStatus() != 7) {
            throw new BaseException(BasicEnum.ONLY_PENDING_OR_SERVICING_CAN_REFUND);
        }

        // 更新订单退款信息
        orderMapper.updateOrderRefund(id, refundOrderDto.getReason());

        // TODO: 调用退款接口
    }

    @Override
    @Transactional
    public void commentOrder(Long id, CommentOrderDto commentOrderDto) {
        // 获取当前用户ID
        Long userId = UserThreadLocal.getCurrentId();
        if (ObjectUtils.isEmpty(userId)) {
            throw new BaseException(BasicEnum.USER_NOT_LOGIN);
        }

        if (ObjectUtils.isEmpty(id)) {
            throw new BaseException(BasicEnum.ORDER_ID_NOT_EMPTY);
        }

        // 查询订单
        OrderVo orderVo = orderMapper.selectOrderById(id, userId);
        if (ObjectUtils.isEmpty(orderVo)) {
            throw new BaseException(BasicEnum.ORDER_NOT_EXIST);
        }

        // 只有已完成状态的订单可以评价
        if (orderVo.getStatus() != 3) {
            throw new BaseException(BasicEnum.ONLY_COMPLETED_CAN_COMMENT);
        }

        // 检查陪诊师是否存在
        if (ObjectUtils.isEmpty(orderVo.getCompanionId())) {
            throw new BaseException(BasicEnum.ORDER_NO_COMPANION);
        }

        // 检查订单是否已评价
        Integer reviewCount = orderMapper.checkOrderReviewed(id);
        if (reviewCount != null && reviewCount > 0) {
            throw new BaseException(BasicEnum.ORDER_ALREADY_REVIEWED);
        }

        // 获取陪诊师信息
        CompanionVo companionVo = companionMapper.selectCompanionById(orderVo.getCompanionId(), userId);
        if (ObjectUtils.isEmpty(companionVo)) {
            throw new BaseException(BasicEnum.COMPANION_NOT_EXIST);
        }

        // 创建评价记录
        orderMapper.insertOrderReview(
                id,
                userId,
                orderVo.getCompanionId(),
                companionVo.getName(),
                companionVo.getAvatar(),
                commentOrderDto.getScore(),
                commentOrderDto.getContent(),
                commentOrderDto.getImages()
        );

        // 更新陪诊师评分
        orderMapper.updateCompanionScore(orderVo.getCompanionId());
    }
}