package com.accompany.service.impl;

import com.accompany.base.BasicEnum;
import com.accompany.entity.Favorite;
import com.accompany.exception.BaseException;
import com.accompany.entity.Orders;
import com.accompany.mapper.CompanionMapper;
import com.accompany.mapper.OrderMapper;
import com.accompany.service.CompanionService;
import com.accompany.util.UserThreadLocal;
import com.accompany.vo.CompanionCommentListVo;
import com.accompany.vo.CompanionCommentVo;
import com.accompany.vo.CompanionListVo;
import com.accompany.vo.CompanionVo;
import com.accompany.vo.TimeSlotVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 陪诊师模块Service实现
 */
@Service
public class CompanionServiceImpl implements CompanionService {

    @Autowired
    private CompanionMapper companionMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public CompanionListVo getCompanions(String gender, Long service, String keyword, String sort, Integer page, Integer pageSize) {
        // 获取当前用户ID（用于判断是否收藏，未登录时为null）
        Long userId = null;
        try {
            userId = UserThreadLocal.getCurrentId();
        } catch (Exception e) {
            // 用户未登录，userId保持为null
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

        // 查询陪诊师列表
        List<CompanionVo> list = companionMapper.selectCompanions(
                gender, service, keyword, sort, offset, pageSize, userId
        );

        // 统计总数
        Integer total = companionMapper.countCompanions(gender, service, keyword);

        // 封装返回结果
        CompanionListVo result = new CompanionListVo();
        result.setList(list);
        result.setTotal(total);
        result.setPage(page);
        result.setPageSize(pageSize);

        return result;
    }

    @Override
    public CompanionVo getCompanionById(Long id) {
        if (ObjectUtils.isEmpty(id)) {
            throw new BaseException(BasicEnum.NOT_EXIST);
        }

        // 获取当前用户ID（用于判断是否收藏，未登录时为null）
        Long userId = null;
        try {
            userId = UserThreadLocal.getCurrentId();
        } catch (Exception e) {
            // 用户未登录，userId保持为null
        }

        CompanionVo companionVo = companionMapper.selectCompanionById(id, userId);
        if (ObjectUtils.isEmpty(companionVo)) {
            throw new BaseException(BasicEnum.NOT_EXIST);
        }

        return companionVo;
    }

    @Override
    public CompanionCommentListVo getCompanionComments(Long id, Integer page, Integer pageSize) {
        if (ObjectUtils.isEmpty(id)) {
            throw new BaseException(BasicEnum.NOT_EXIST);
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

        // 查询评价列表
        List<CompanionCommentVo> list = companionMapper.selectCompanionComments(id, offset, pageSize);

        // 统计总数
        Integer total = companionMapper.countCompanionComments(id);

        // 封装返回结果
        CompanionCommentListVo result = new CompanionCommentListVo();
        result.setList(list);
        result.setTotal(total);
        result.setPage(page);
        result.setPageSize(pageSize);

        return result;
    }

    @Override
    public Boolean toggleFavorite(Long id) {
        // 获取当前用户ID
        Long userId = UserThreadLocal.getCurrentId();
        if (ObjectUtils.isEmpty(userId)) {
            throw new BaseException(BasicEnum.USER_NOT_LOGIN);
        }

        if (ObjectUtils.isEmpty(id)) {
            throw new BaseException(BasicEnum.NOT_EXIST);
        }

        // 检查陪诊师是否存在
        CompanionVo companionVo = companionMapper.selectCompanionById(id, userId);
        if (ObjectUtils.isEmpty(companionVo)) {
            throw new BaseException(BasicEnum.NOT_EXIST);
        }

        // 检查是否已收藏
        Favorite favorite = companionMapper.selectFavorite(userId, id);

        if (favorite != null) {
            // 已收藏，取消收藏
            companionMapper.deleteFavorite(userId, id);
            return false;
        } else {
            // 未收藏，添加收藏
            favorite = new Favorite();
            favorite.setUserId(userId);
            favorite.setType("companion"); // 设置收藏类型为陪诊师
            favorite.setItemId(id);
            favorite.setName(companionVo.getName());
            favorite.setAvatar(companionVo.getAvatar());
            favorite.setDescription(companionVo.getIntro());
            favorite.setTime(LocalDateTime.now());
            companionMapper.insertFavorite(favorite);
            return true;
        }
    }

    @Override
    public List<TimeSlotVo> getAvailableTimes(Long id, String date) {
        if (ObjectUtils.isEmpty(id)) {
            throw new BaseException(BasicEnum.NOT_EXIST);
        }

        // 检查陪诊师是否存在
        Long userId = UserThreadLocal.getCurrentId();
        CompanionVo companionVo = companionMapper.selectCompanionById(id, userId);
        if (ObjectUtils.isEmpty(companionVo)) {
            throw new BaseException(BasicEnum.NOT_EXIST);
        }

        // 如果没有指定日期，使用今天
        if (ObjectUtils.isEmpty(date)) {
            date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } else {
            // 处理日期格式：如果传入的是 "M-dd" 或 "MM-dd" 格式，转换为 "yyyy-MM-dd"
            if (date.matches("^\\d{1,2}-\\d{1,2}$")) {
                String[] parts = date.split("-");
                int month = Integer.parseInt(parts[0]);
                int day = Integer.parseInt(parts[1]);
                int year = LocalDate.now().getYear();
                date = String.format("%04d-%02d-%02d", year, month, day);
            }
        }

        // 查询该陪诊师在指定日期的订单
        List<Orders> orders = orderMapper.selectCompanionOrdersByDate(id, date);

        // 提取已占用的小时数（订单预约时间的小时数）
        Set<Integer> bookedHours = new HashSet<>();
        for (Orders order : orders) {
            if (order.getAppointmentTime() != null) {
                int hour = order.getAppointmentTime().getHour();
                // 将订单的小时数和下一个小时都标记为已占用（假设每个订单占用1小时）
                bookedHours.add(hour);
            }
        }

        // 生成时间段列表（08:00 - 18:00，每小时一个时间段）
        List<TimeSlotVo> timeSlots = new ArrayList<>();

        for (int hour = 8; hour <= 18; hour++) {
            TimeSlotVo timeSlot = new TimeSlotVo();
            timeSlot.setTime(String.format("%02d:00", hour));

            // 如果该小时已被订单占用，状态为booked，否则为available
            String status = bookedHours.contains(hour) ? "booked" : "available";
            timeSlot.setStatus(status);

            timeSlots.add(timeSlot);
        }

        return timeSlots;
    }
}