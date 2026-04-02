package com.accompany.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BasicEnum {

    SUCCEED(200, "操作成功"),
    NOT_EXIST(404, "用户不存在"),
    PASSWORD_ERROR(400, "密码错误"),
    SYSYTEM_FAIL(500, "系统运行异常"),
    SECURITY_ACCESSDENIED_FAIL(401, "权限不足!"),
    USER_EXIST(400, "用户已注册"),
    ALTER_PASSWROD_SAME(400, "新密码与旧密码一致"),
    ACCOUNT_NOT_ACCESS(400, "账号被禁用，请联系管理员"),
    OLD_PASSWORD_ERROR(400, "旧密码错误"),
    USER_NOT_LOGIN(401, "用户未登录"),
    UPDATE_AT_LEAST_ONE_FIELD(400, "至少需要更新一个字段"),
    AVATAR_URL_NOT_EMPTY(400, "头像URL不能为空"),
    NEW_PASSWORD_NOT_EMPTY(400, "新密码不能为空"),
    PATIENT_NAME_NOT_EMPTY(400, "就诊人姓名不能为空"),
    SERVICE_ID_NOT_EMPTY(400, "服务ID不能为空"),
    SERVICE_NOT_FOUND(404, "服务不存在"),
    SERVICE_NOT_EXIST(404, "服务项目不存在"),
    PATIENT_NOT_EXIST(404, "就诊人不存在"),
    COMPANION_NOT_EXIST(404, "陪诊师不存在"),
    PICKUP_ADDRESS_NOT_EMPTY(400, "接人地址不能为空"),
    ORDER_ID_NOT_EMPTY(400, "订单ID不能为空"),
    ORDER_NOT_EXIST(404, "订单不存在"),
    ORDER_STATUS_ERROR(400, "订单状态异常"),
    ONLY_PENDING_PAY_CAN_CANCEL(400, "只有待支付状态的订单可以取消"),
    ONLY_PENDING_PAY_CAN_PAY(400, "只有待支付状态的订单可以支付"),
    PAYMENT_METHOD_INVALID(400, "支付方式不正确"),
    ONLY_SERVICING_CAN_CONFIRM(400, "只有服务中状态的订单可以确认完成"),
    ONLY_PENDING_OR_SERVICING_CAN_REFUND(400, "只有待接单或服务中状态的订单可以申请退款"),
    ONLY_COMPLETED_CAN_COMMENT(400, "只有已完成状态的订单可以评价"),
    ORDER_NO_COMPANION(400, "订单未指定陪诊师，无法评价"),
    ORDER_ALREADY_REVIEWED(400, "订单已经评价过了"),
    COMPANION_PROFILE_NOT_EXIST(404, "陪诊师信息不存在"),
    ORDER_STATUS_INVALID(400, "订单状态不正确"),
    PARAM_ERROR(400, "参数错误"),
    ALREADY_FAVORITED(400, "已收藏"),
    NO_PERMISSION(403, "无权限操作"),
    CONVERSATION_NOT_EXIST(404, "会话不存在"),
    RECHARGE_AMOUNT_TOO_SMALL(400, "充值金额过小"),
    RECHARGE_AMOUNT_TOO_LARGE(400, "充值金额过大");

    /**
     * 编码
     */
    public final int code;
    /**
     * 信息
     */
    public final String msg;

}
