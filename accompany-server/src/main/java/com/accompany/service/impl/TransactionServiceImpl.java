package com.accompany.service.impl;

import com.accompany.entity.TransactionRecord;
import com.accompany.mapper.TransactionMapper;
import com.accompany.service.TransactionService;
import com.accompany.util.UserThreadLocal;
import com.accompany.vo.TransactionRecordListVo;
import com.accompany.vo.TransactionRecordVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 交易记录服务实现类
 */
@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionMapper transactionMapper;

    @Override
    public TransactionRecordListVo getConsumptionRecords() {
        // 从ThreadLocal中获取当前用户ID
        Long userId = UserThreadLocal.getCurrentId();
        if (ObjectUtils.isEmpty(userId)) {
            throw new com.accompany.exception.BaseException(com.accompany.base.BasicEnum.USER_NOT_LOGIN);
        }

        // 查询消费记录（type = "consume"）
        List<TransactionRecord> records = transactionMapper.findByUserIdAndType(userId, "consume");

        // 转换为VO
        return convertToVoList(records);
    }

    @Override
    public TransactionRecordListVo getTransactionRecords() {
        // 从ThreadLocal中获取当前用户ID
        Long userId = UserThreadLocal.getCurrentId();
        if (ObjectUtils.isEmpty(userId)) {
            throw new com.accompany.exception.BaseException(com.accompany.base.BasicEnum.USER_NOT_LOGIN);
        }

        // 查询所有交易记录
        List<TransactionRecord> records = transactionMapper.findByUserId(userId);

        // 转换为VO
        return convertToVoList(records);
    }

    /**
     * 将实体列表转换为VO列表
     */
    private TransactionRecordListVo convertToVoList(List<TransactionRecord> records) {
        TransactionRecordListVo voList = new TransactionRecordListVo();
        List<TransactionRecordVo> vos = records.stream().map(record -> {
            TransactionRecordVo vo = new TransactionRecordVo();
            vo.setId(record.getId());
            vo.setTitle(record.getTitle());
            vo.setBalance(record.getBalance());
            vo.setTime(record.getTime());

            // 转换类型
            String type = record.getType();
            if ("recharge".equals(type)) {
                vo.setType(1); // 充值
                vo.setAmount(record.getAmount()); // 充值金额为正数
            } else if ("consume".equals(type)) {
                vo.setType(2); // 消费
                vo.setAmount(record.getAmount().negate()); // 消费金额为负数
            } else if ("refund".equals(type)) {
                vo.setType(3); // 退款
                vo.setAmount(record.getAmount().negate()); // 退款金额为负数
            }

            return vo;
        }).collect(Collectors.toList());

        voList.setList(vos);
        voList.setTotal(records.size());
        voList.setPage(1);
        voList.setPageSize(records.size());

        return voList;
    }
}