package com.accompany.service;

import com.accompany.vo.TransactionRecordListVo;

/**
 * 交易记录服务接口
 */
public interface TransactionService {

    /**
     * 获取消费记录列表
     * @return 消费记录列表
     */
    TransactionRecordListVo getConsumptionRecords();

    /**
     * 获取交易记录列表（包含充值、消费、退款）
     * @return 交易记录列表
     */
    TransactionRecordListVo getTransactionRecords();
}