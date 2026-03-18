package com.accompany.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.accompany.base.BasicEnum;
import com.accompany.dto.CreateRechargeDto;
import com.accompany.entity.RechargeRecord;
import com.accompany.exception.BaseException;
import com.accompany.mapper.RechargeMapper;
import com.accompany.service.RechargeService;
import com.accompany.util.UserThreadLocal;
import com.accompany.vo.RechargeConfigVo;
import com.accompany.vo.RechargeRecordVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 充值服务实现类
 */
@Service
public class RechargeServiceImpl implements RechargeService {

    @Autowired
    private RechargeMapper rechargeMapper;

    /**
     * 最小充值金额
     */
    private static final Integer MIN_AMOUNT = 10;

    /**
     * 最大充值金额
     */
    private static final Integer MAX_AMOUNT = 5000;

    /**
     * 快捷充值金额列表
     */
    private static final List<Integer> QUICK_AMOUNTS = Arrays.asList(10, 50, 100, 200, 500);

    @Override
    public RechargeConfigVo getRechargeConfig() {
        RechargeConfigVo configVo = new RechargeConfigVo();
        configVo.setMinAmount(MIN_AMOUNT);
        configVo.setMaxAmount(MAX_AMOUNT);
        configVo.setQuickAmounts(QUICK_AMOUNTS);
        return configVo;
    }

    @Override
    public String createRechargeOrder(CreateRechargeDto createRechargeDto) {
        // 从ThreadLocal中获取当前用户ID
        Long userId = UserThreadLocal.getCurrentId();
        if (ObjectUtils.isEmpty(userId)) {
            throw new BaseException(BasicEnum.USER_NOT_LOGIN);
        }

        // 验证充值金额范围
        if (createRechargeDto.getAmount().intValue() < MIN_AMOUNT) {
            throw new BaseException(BasicEnum.RECHARGE_AMOUNT_TOO_SMALL);
        }
        if (createRechargeDto.getAmount().intValue() > MAX_AMOUNT) {
            throw new BaseException(BasicEnum.RECHARGE_AMOUNT_TOO_LARGE);
        }

        // 验证支付方式
        if (!"wechat".equals(createRechargeDto.getMethod()) && !"alipay".equals(createRechargeDto.getMethod())) {
            throw new BaseException(BasicEnum.PAYMENT_METHOD_INVALID);
        }

        // 生成充值订单号 (R + 年月日 + 6位序号)
        String orderNo = generateOrderNo();

        // 创建充值记录
        RechargeRecord rechargeRecord = new RechargeRecord();
        rechargeRecord.setUserId(userId);
        rechargeRecord.setOrderNo(orderNo);
        rechargeRecord.setAmount(createRechargeDto.getAmount());
        rechargeRecord.setMethod(createRechargeDto.getMethod());
        rechargeRecord.setPayStatus(0); // 0-待支付

        // 插入数据库
        rechargeMapper.insert(rechargeRecord);

        // 返回订单号
        return orderNo;
    }

    @Override
    public List<RechargeRecordVo> getRechargeRecords() {
        // 从ThreadLocal中获取当前用户ID
        Long userId = UserThreadLocal.getCurrentId();
        if (ObjectUtils.isEmpty(userId)) {
            throw new BaseException(BasicEnum.USER_NOT_LOGIN);
        }

        // 查询充值记录
        List<RechargeRecord> rechargeRecords = rechargeMapper.findByUserId(userId);

        // 转换为VO
        return rechargeRecords.stream().map(record -> {
            RechargeRecordVo vo = new RechargeRecordVo();
            vo.setId(record.getId());
            vo.setOrderNo(record.getOrderNo());
            vo.setAmount(record.getAmount());
            vo.setMethod(record.getMethod());
            vo.setStatus(record.getPayStatus());
            vo.setTime(record.getCreateTime());
            return vo;
        }).collect(Collectors.toList());
    }

    /**
     * 生成充值订单号
     * 格式: R + 年月日 + 6位序号
     */
    private String generateOrderNo() {
        String dateStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String randomStr = String.valueOf(IdUtil.getSnowflakeNextId()).substring(10, 16);
        return "R" + dateStr + randomStr;
    }
}