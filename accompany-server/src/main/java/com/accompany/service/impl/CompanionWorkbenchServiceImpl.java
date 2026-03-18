package com.accompany.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.accompany.base.BasicEnum;
import com.accompany.dto.CompleteOrderDto;
import com.accompany.dto.RejectOrderDto;
import com.accompany.dto.UpdateCompanionProfileDto;
import com.accompany.dto.UpdateOnlineStatusDto;
import com.accompany.entity.CompanionProfile;
import com.accompany.entity.CompanionStatistics;
import com.accompany.entity.Orders;
import com.accompany.entity.SysUser;
import com.accompany.exception.BaseException;
import com.accompany.mapper.CompanionMapper;
import com.accompany.mapper.CompanionWorkbenchMapper;
import com.accompany.mapper.OrderMapper;
import com.accompany.mapper.UserMapper;
import com.accompany.service.CompanionWorkbenchService;
import com.accompany.util.UserThreadLocal;
import com.accompany.vo.AvailableOrderListVo;
import com.accompany.vo.AvailableOrderVo;
import com.accompany.vo.CompanionStatisticsVo;
import com.accompany.vo.CompanionVo;
import com.accompany.vo.IncomeRecordListVo;
import com.accompany.vo.IncomeRecordVo;
import com.accompany.vo.OrderListVo;
import com.accompany.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 陪诊师工作台模块Service实现
 */
@Service
public class CompanionWorkbenchServiceImpl implements CompanionWorkbenchService {

    @Autowired
    private CompanionWorkbenchMapper companionWorkbenchMapper;

    @Autowired
    private CompanionMapper companionMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public CompanionStatisticsVo getStatistics() {
        // 获取当前陪诊师ID
        Long userId = UserThreadLocal.getCurrentId();
        if (ObjectUtils.isEmpty(userId)) {
            throw new BaseException(BasicEnum.USER_NOT_LOGIN);
        }

        // 查询陪诊师信息
        CompanionProfile companionProfile = companionWorkbenchMapper.selectProfile(userId);
        if (ObjectUtils.isEmpty(companionProfile)) {
            throw new BaseException(BasicEnum.COMPANION_PROFILE_NOT_EXIST);
        }

        // 查询统计数据
        CompanionStatisticsVo statistics = companionWorkbenchMapper.selectStatistics(companionProfile.getId());
        return statistics;
    }

    @Override
    public CompanionVo getProfile() {
        // 获取当前陪诊师ID
        Long userId = UserThreadLocal.getCurrentId();
        if (ObjectUtils.isEmpty(userId)) {
            throw new BaseException(BasicEnum.USER_NOT_LOGIN);
        }

        // 查询陪诊师信息
        CompanionProfile companionProfile = companionWorkbenchMapper.selectProfile(userId);
        if (ObjectUtils.isEmpty(companionProfile)) {
            throw new BaseException(BasicEnum.COMPANION_PROFILE_NOT_EXIST);
        }

        // 查询陪诊师详情
        CompanionVo companionVo = companionMapper.selectCompanionById(companionProfile.getId(), userId);
        if (ObjectUtils.isEmpty(companionVo)) {
            throw new BaseException(BasicEnum.COMPANION_PROFILE_NOT_EXIST);
        }

        return companionVo;
    }

    @Override
    @Transactional
    public CompanionVo updateProfile(UpdateCompanionProfileDto updateCompanionProfileDto) {
        // 获取当前陪诊师ID
        Long userId = UserThreadLocal.getCurrentId();
        if (ObjectUtils.isEmpty(userId)) {
            throw new BaseException(BasicEnum.USER_NOT_LOGIN);
        }

        // 查询陪诊师信息
        CompanionProfile companionProfile = companionWorkbenchMapper.selectProfile(userId);
        if (ObjectUtils.isEmpty(companionProfile)) {
            throw new BaseException(BasicEnum.COMPANION_PROFILE_NOT_EXIST);
        }

        // 更新陪诊师信息
        if (!ObjectUtils.isEmpty(updateCompanionProfileDto.getNickname())) {
            companionProfile.setName(updateCompanionProfileDto.getNickname());
        }
        if (!ObjectUtils.isEmpty(updateCompanionProfileDto.getAvatar())) {
            companionProfile.setAvatar(updateCompanionProfileDto.getAvatar());
        }
        if (!ObjectUtils.isEmpty(updateCompanionProfileDto.getAge())) {
            companionProfile.setAge(updateCompanionProfileDto.getAge());
        }
        if (!ObjectUtils.isEmpty(updateCompanionProfileDto.getExperience())) {
            companionProfile.setExperience(updateCompanionProfileDto.getExperience());
        }
        if (!ObjectUtils.isEmpty(updateCompanionProfileDto.getIntroduction())) {
            companionProfile.setIntro(updateCompanionProfileDto.getIntroduction());
        }

        // 更新用户信息（昵称、手机号）
        if (!ObjectUtils.isEmpty(updateCompanionProfileDto.getNickname()) || !ObjectUtils.isEmpty(updateCompanionProfileDto.getPhone())) {
            SysUser sysUser = userMapper.selectUserById(userId);
            if (!ObjectUtils.isEmpty(sysUser)) {
                if (!ObjectUtils.isEmpty(updateCompanionProfileDto.getNickname())) {
                    sysUser.setNickname(updateCompanionProfileDto.getNickname());
                }
                if (!ObjectUtils.isEmpty(updateCompanionProfileDto.getPhone())) {
                    sysUser.setPhone(updateCompanionProfileDto.getPhone());
                }
                // TODO: 更新用户信息
            }
        }

        companionWorkbenchMapper.updateProfile(companionProfile);

        // 返回更新后的信息
        return getProfile();
    }

    @Override
    public AvailableOrderListVo getAvailableOrders(Integer page, Integer pageSize) {
        // 获取当前陪诊师ID
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

        // 查询可接订单列表
        List<AvailableOrderVo> list = companionWorkbenchMapper.selectAvailableOrders(offset, pageSize);

        // 统计总数
        Integer total = companionWorkbenchMapper.countAvailableOrders();

        // 封装返回结果
        AvailableOrderListVo result = new AvailableOrderListVo();
        result.setList(list);
        result.setTotal(total);
        result.setPage(page);
        result.setPageSize(pageSize);

        return result;
    }

    @Override
    @Transactional
    public void acceptOrder(Long orderId) {
        // 获取当前陪诊师ID
        Long userId = UserThreadLocal.getCurrentId();
        if (ObjectUtils.isEmpty(userId)) {
            throw new BaseException(BasicEnum.USER_NOT_LOGIN);
        }

        if (ObjectUtils.isEmpty(orderId)) {
            throw new BaseException(BasicEnum.ORDER_ID_NOT_EMPTY);
        }

        // 查询陪诊师信息
        CompanionProfile companionProfile = companionWorkbenchMapper.selectProfile(userId);
        if (ObjectUtils.isEmpty(companionProfile)) {
            throw new BaseException(BasicEnum.COMPANION_PROFILE_NOT_EXIST);
        }

        // 查询订单信息
        Orders order = orderMapper.selectOrderEntityById(orderId);
        if (ObjectUtils.isEmpty(order)) {
            throw new BaseException(BasicEnum.ORDER_NOT_EXIST);
        }

        // 验证订单状态
        if (order.getStatus() != 1) {
            throw new BaseException(BasicEnum.ORDER_STATUS_ERROR);
        }

        // 接单
        companionWorkbenchMapper.updateOrderCompanionId(order.getId(), companionProfile.getId());
        orderMapper.updateOrderStatus(order.getId(), 2); // 接单
    }

    @Override
    @Transactional
    public void rejectOrder(Long orderId, RejectOrderDto rejectOrderDto) {
        // 获取当前陪诊师ID
        Long userId = UserThreadLocal.getCurrentId();
        if (ObjectUtils.isEmpty(userId)) {
            throw new BaseException(BasicEnum.USER_NOT_LOGIN);
        }

        if (ObjectUtils.isEmpty(orderId)) {
            throw new BaseException(BasicEnum.ORDER_ID_NOT_EMPTY);
        }

        // 查询订单信息
        Orders order = orderMapper.selectOrderEntityById(orderId);
        if (ObjectUtils.isEmpty(order)) {
            throw new BaseException(BasicEnum.ORDER_NOT_EXIST);
        }

        // 验证订单状态
        if (order.getStatus() != 1) {
            throw new BaseException(BasicEnum.ORDER_STATUS_ERROR);
        }

        // TODO: 记录拒单原因
        // 拒单后订单回到抢单大厅
        // 暂时不做任何处理
    }

    @Override
    @Transactional
    public void startService(Long orderId) {
        // 获取当前陪诊师ID
        Long userId = UserThreadLocal.getCurrentId();
        if (ObjectUtils.isEmpty(userId)) {
            throw new BaseException(BasicEnum.USER_NOT_LOGIN);
        }

        if (ObjectUtils.isEmpty(orderId)) {
            throw new BaseException(BasicEnum.ORDER_ID_NOT_EMPTY);
        }

        // 查询订单信息
        Orders order = orderMapper.selectOrderEntityById(orderId);
        if (ObjectUtils.isEmpty(order)) {
            throw new BaseException(BasicEnum.ORDER_NOT_EXIST);
        }

        // 验证订单状态（接单状态才能开始服务）
        if (order.getStatus() != 2) {
            throw new BaseException(BasicEnum.ORDER_STATUS_ERROR);
        }

        // 更新订单状态为开始服务
        orderMapper.updateOrderStatus(order.getId(), 7); // 开始服务
    }

    @Override
    @Transactional
    public void completeService(Long orderId, CompleteOrderDto completeOrderDto) {
        // 获取当前陪诊师ID
        Long userId = UserThreadLocal.getCurrentId();
        if (ObjectUtils.isEmpty(userId)) {
            throw new BaseException(BasicEnum.USER_NOT_LOGIN);
        }

        if (ObjectUtils.isEmpty(orderId)) {
            throw new BaseException(BasicEnum.ORDER_ID_NOT_EMPTY);
        }

        // 查询订单信息
        Orders order = orderMapper.selectOrderEntityById(orderId);
        if (ObjectUtils.isEmpty(order)) {
            throw new BaseException(BasicEnum.ORDER_NOT_EXIST);
        }

        // 验证订单状态（服务中才能完成服务）
        if (order.getStatus() != 7) {
            throw new BaseException(BasicEnum.ORDER_STATUS_ERROR);
        }

        // 更新订单状态为已完成
        orderMapper.updateOrderCompleteTime(order.getId());

        // TODO: 创建评价记录
        // TODO: 更新陪诊师统计信息
    }

    @Override
    @Transactional
    public void updateOrderStatus(Long orderId, Integer status) {
        // 获取当前陪诊师ID
        Long userId = UserThreadLocal.getCurrentId();
        if (ObjectUtils.isEmpty(userId)) {
            throw new BaseException(BasicEnum.USER_NOT_LOGIN);
        }

        if (ObjectUtils.isEmpty(orderId)) {
            throw new BaseException(BasicEnum.ORDER_ID_NOT_EMPTY);
        }

        // 验证订单状态（允许的状态：2已接单、7服务中、3已完成、4已取消）
        if (status != 2 && status != 7 && status != 3 && status != 4) {
            throw new BaseException(BasicEnum.ORDER_STATUS_INVALID);
        }

        // 更新订单状态
        companionWorkbenchMapper.updateOrderStatus(orderId, status);
    }

    @Override
    public OrderListVo getOrders(Integer status, Integer page, Integer pageSize) {
        // 获取当前陪诊师ID
        Long userId = UserThreadLocal.getCurrentId();
        if (ObjectUtils.isEmpty(userId)) {
            throw new BaseException(BasicEnum.USER_NOT_LOGIN);
        }

        // 查询陪诊师信息
        CompanionProfile companionProfile = companionWorkbenchMapper.selectProfile(userId);
        if (ObjectUtils.isEmpty(companionProfile)) {
            throw new BaseException(BasicEnum.COMPANION_PROFILE_NOT_EXIST);
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
        List<Orders> orders = companionWorkbenchMapper.selectCompanionOrders(companionProfile.getId(), status, offset, pageSize);

        // 转换为OrderVo
        List<OrderVo> orderVoList = new ArrayList<>();
        for (Orders order : orders) {
            OrderVo orderVo = BeanUtil.toBean(order, OrderVo.class);
            orderVoList.add(orderVo);
        }

        // 统计总数
        Integer total = companionWorkbenchMapper.countCompanionOrders(companionProfile.getId(), status);

        // 封装返回结果
        OrderListVo result = new OrderListVo();
        result.setList(orderVoList);
        result.setTotal(total);
        result.setPage(page);
        result.setPageSize(pageSize);

        return result;
    }

    @Override
    public OrderVo getOrderById(Long orderId) {
        // 获取当前陪诊师ID
        Long userId = UserThreadLocal.getCurrentId();
        if (ObjectUtils.isEmpty(userId)) {
            throw new BaseException(BasicEnum.USER_NOT_LOGIN);
        }

        if (ObjectUtils.isEmpty(orderId)) {
            throw new BaseException(BasicEnum.ORDER_ID_NOT_EMPTY);
        }

        // 查询订单详情（陪诊师视角）
        OrderVo orderVo = companionWorkbenchMapper.selectCompanionOrderById(orderId);
        if (ObjectUtils.isEmpty(orderVo)) {
            throw new BaseException(BasicEnum.ORDER_NOT_EXIST);
        }

        return orderVo;
    }

    @Override
    public IncomeRecordListVo getIncome(Integer page, Integer pageSize) {
        // 获取当前陪诊师ID
        Long userId = UserThreadLocal.getCurrentId();
        if (ObjectUtils.isEmpty(userId)) {
            throw new BaseException(BasicEnum.USER_NOT_LOGIN);
        }

        // 查询陪诊师信息
        CompanionProfile companionProfile = companionWorkbenchMapper.selectProfile(userId);
        if (ObjectUtils.isEmpty(companionProfile)) {
            throw new BaseException(BasicEnum.COMPANION_PROFILE_NOT_EXIST);
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

        // 查询收入记录
        List<com.accompany.entity.CompanionIncome> incomes = companionWorkbenchMapper.selectIncomeRecords(companionProfile.getId(), offset, pageSize);

        // 转换为IncomeRecordVo
        List<IncomeRecordVo> incomeVoList = new ArrayList<>();
        for (com.accompany.entity.CompanionIncome income : incomes) {
            IncomeRecordVo incomeVo = BeanUtil.toBean(income, IncomeRecordVo.class);
            incomeVoList.add(incomeVo);
        }

        // 统计总数
        Integer total = companionWorkbenchMapper.countIncomeRecords(companionProfile.getId());

        // 封装返回结果
        IncomeRecordListVo result = new IncomeRecordListVo();
        result.setList(incomeVoList);
        result.setTotal(total);
        result.setPage(page);
        result.setPageSize(pageSize);

        return result;
    }

    @Override
    @Transactional
    public void updateOnlineStatus(UpdateOnlineStatusDto updateOnlineStatusDto) {
        // 获取当前陪诊师ID
        Long userId = UserThreadLocal.getCurrentId();
        if (ObjectUtils.isEmpty(userId)) {
            throw new BaseException(BasicEnum.USER_NOT_LOGIN);
        }

        // 查询陪诊师信息
        CompanionProfile companionProfile = companionWorkbenchMapper.selectProfile(userId);
        if (ObjectUtils.isEmpty(companionProfile)) {
            throw new BaseException(BasicEnum.COMPANION_PROFILE_NOT_EXIST);
        }

        // 更新在线状态
        Integer isOnline = updateOnlineStatusDto.getIsOnline() ? 1 : 0;
        companionWorkbenchMapper.updateOnlineStatus(companionProfile.getId(), isOnline);
    }
}