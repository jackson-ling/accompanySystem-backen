package com.accompany.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单VO
 */
@Data
public class OrderVo {
    /** 主键ID */
    private Long id;

    /** 订单号 */
    private String orderNo;

    /** 用户ID */
    private Long userId;

    /** 陪诊师ID */
    private Long companionId;

    /** 陪诊师姓名 */
    private String companionName;

    /** 陪诊师手机号 */
    private String companionPhone;

    /** 陪诊师头像 */
    private String companionAvatar;

    /** 服务ID */
    private Long serviceId;

    /** 服务名称 */
    private String serviceName;

    /** 服务图片 */
    private String serviceImage;

    /** 患者ID */
    private Long patientId;

    /** 患者姓名 */
    private String patientName;

    /** 医院 */
    private String hospital;

    /** 科室 */
    private String department;

    /** 预约时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime payTime;

    /** 状态: 0-待支付, 1-待接单, 2-服务中, 3-已完成, 4-已取消, 5-退款中, 6-已退款 */
    private Integer status;

    /** 退款原因 */
    private String refundReason;

    /** 退款时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime refundTime;

    /** 完成时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime completeTime;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    /** 是否已评价 */
    private Boolean reviewed;

    /** 评价评分 */
    private BigDecimal reviewScore;

    /** 评价内容 */
    private String reviewContent;
}