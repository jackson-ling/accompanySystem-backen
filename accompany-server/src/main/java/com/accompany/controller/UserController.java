package com.accompany.controller;

import com.accompany.base.Result;
import com.accompany.dto.CreateRechargeDto;
import com.accompany.dto.SubmitFeedbackDto;
import com.accompany.dto.UpdateAvatarDto;
import com.accompany.dto.UpdatePasswordDto;
import com.accompany.dto.UpdateUserDto;
import com.accompany.service.FeedbackService;
import com.accompany.service.RechargeService;
import com.accompany.service.TransactionService;
import com.accompany.service.UserService;
import com.accompany.vo.BalanceVo;
import com.accompany.vo.FeedbackVo;
import com.accompany.vo.RechargeConfigVo;
import com.accompany.vo.RechargeRecordVo;
import com.accompany.vo.TransactionRecordListVo;
import com.accompany.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RechargeService rechargeService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private FeedbackService feedbackService;

    /**
     * 获取用户信息
     */
    @GetMapping("/profile")
    public Result<UserVo> getUserProfile() {
        UserVo userVo = userService.getUserProfile();
        return Result.success(userVo);
    }

    /**
     * 获取用户余额
     */
    @GetMapping("/balance")
    public Result<BalanceVo> getUserBalance() {
        BalanceVo balanceVo = userService.getUserBalance();
        return Result.success(balanceVo);
    }

    /**
     * 更新用户基本信息（昵称、性别、生日）
     */
    @PutMapping("/profile")
    public Result<UserVo> updateUser(@RequestBody UpdateUserDto updateUserDto) {
        UserVo userVo = userService.updateUser(updateUserDto);
        return Result.success(userVo);
    }

    /**
     * 更新用户头像
     */
    @PutMapping("/avatar")
    public Result<UserVo> updateAvatar(@RequestBody UpdateAvatarDto updateAvatarDto) {
        UserVo userVo = userService.updateAvatar(updateAvatarDto);
        return Result.success(userVo);
    }

    /**
     * 修改密码
     */
    @PutMapping("/password")
    public Result updatePassword(@RequestBody UpdatePasswordDto updatePasswordDto) {
        userService.updatePassword(updatePasswordDto);
        return Result.success();
    }

    /**
     * 获取充值配置
     */
    @GetMapping("/recharge/config")
    public Result<RechargeConfigVo> getRechargeConfig() {
        RechargeConfigVo configVo = rechargeService.getRechargeConfig();
        return Result.success(configVo);
    }

    /**
     * 创建充值订单
     */
    @PostMapping("/recharge")
    public Result<String> createRechargeOrder(@RequestBody CreateRechargeDto createRechargeDto) {
        String orderNo = rechargeService.createRechargeOrder(createRechargeDto);
        return Result.success(orderNo);
    }

    /**
     * 获取充值记录
     */
    @GetMapping("/recharge/records")
    public Result<List<RechargeRecordVo>> getRechargeRecords() {
        List<RechargeRecordVo> records = rechargeService.getRechargeRecords();
        return Result.success(records);
    }

    /**
     * 获取消费记录
     */
    @GetMapping("/consumption")
    public Result<TransactionRecordListVo> getConsumptionRecords() {
        TransactionRecordListVo records = transactionService.getConsumptionRecords();
        return Result.success(records);
    }

    /**
     * 获取交易记录
     */
    @GetMapping("/transactions")
    public Result<TransactionRecordListVo> getTransactionRecords() {
        TransactionRecordListVo records = transactionService.getTransactionRecords();
        return Result.success(records);
    }

    /**
     * 提交反馈
     */
    @PostMapping("/feedback")
    public Result submitFeedback(@RequestBody SubmitFeedbackDto submitFeedbackDto) {
        feedbackService.submitFeedback(submitFeedbackDto);
        return Result.success();
    }

    /**
     * 获取反馈列表
     */
    @GetMapping("/feedback")
    public Result<List<FeedbackVo>> getFeedbackList() {
        List<FeedbackVo> feedbackList = feedbackService.getFeedbackList();
        return Result.success(feedbackList);
    }
}