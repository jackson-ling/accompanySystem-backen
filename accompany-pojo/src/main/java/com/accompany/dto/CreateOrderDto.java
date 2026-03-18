package com.accompany.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * 创建订单DTO
 */
@Data
public class CreateOrderDto {
    /** 服务项目ID */
    @NotNull(message = "服务项目ID不能为空")
    private Long serviceId;

    /** 陪诊师ID（可选，不填则进入抢单大厅） */
    private Long companionId;

    /** 就诊人ID */
    @NotNull(message = "就诊人ID不能为空")
    private Long patientId;

    /** 医院名称 */
    @NotNull(message = "医院名称不能为空")
    private String hospital;

    /** 科室 */
    @NotNull(message = "科室不能为空")
    private String department;

    /** 预约时间 */
    @NotNull(message = "预约时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private LocalDateTime appointmentTime;

    /** 接人方式: 1-医院门口, 2-指定地点 */
    @NotNull(message = "接人方式不能为空")
    private Integer pickupOption;

    /** 接人地址(pickupOption=2时必填) */
    private String pickupAddress;

    /** 备注 */
    private String remarks;
}