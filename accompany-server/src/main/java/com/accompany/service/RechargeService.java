package com.accompany.service;

import com.accompany.dto.CreateRechargeDto;
import com.accompany.vo.RechargeConfigVo;
import com.accompany.vo.RechargeRecordVo;

import java.util.List;

/**
 * 充值服务接口
 */
public interface RechargeService {

    /**
     * 获取充值配置
     * @return 充值配置
     */
    RechargeConfigVo getRechargeConfig();

    /**
     * 创建充值订单
     * @param createRechargeDto 创建充值订单的DTO
     * @return 充值订单号
     */
    String createRechargeOrder(CreateRechargeDto createRechargeDto);

    /**
     * 获取充值记录列表
     * @return 充值记录列表
     */
    List<RechargeRecordVo> getRechargeRecords();
}