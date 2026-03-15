package com.accompany.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Orders {
    /** 主键ID */
    private Long id;

    /** 订单号 */
    private String orderNo;

    /** 用户ID */
    private Long userId;

    /** 陪护员ID */
    private Long companionId;

    /** 患者ID */
    private Long patientId;

    /** 服务ID */
    private Long serviceId;

    /** 服务名称 */
    private String serviceName;

    /** 服务图片 */
    private String serviceImage;

    /** 医院 */
    private String hospital;

    /** 科室 */
    private String department;

    /** 预约时间 */
    private LocalDateTime appointmentTime;

    /** 接送选项 */
    private Integer pickupOption;

    /** 接送地址 */
    private String pickupAddress;

    /** 备注 */
    private String remarks;

    /** 订单金额 */
    private BigDecimal amount;

    /** 实付金额 */
    private BigDecimal actualAmount;

    /** 支付方式 */
    private Integer payMethod;

    /** 支付时间 */
    private LocalDateTime payTime;

    /** 状态 */
    private Integer status;

    /** 退款原因 */
    private String refundReason;

    /** 退款时间 */
    private LocalDateTime refundTime;

    /** 完成时间 */
    private LocalDateTime completeTime;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;
}