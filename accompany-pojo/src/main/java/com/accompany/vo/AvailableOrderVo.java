package com.accompany.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 可接订单VO
 */
@Data
public class AvailableOrderVo {
    /** 订单ID */
    private Long id;
    /** 服务名称 */
    private String serviceName;
    /** 医院 */
    private String hospital;
    /** 科室 */
    private String department;
    /** 预约时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime appointmentTime;
    /** 接人方式 */
    private Integer pickupOption;
    /** 订单金额 */
    private BigDecimal amount;
    /** 距离 */
    private BigDecimal distance;
}