package com.accompany.vo;

import lombok.Data;

/**
 * 时间段VO
 */
@Data
public class TimeSlotVo {
    /** 时间点（如：08:00） */
    private String time;

    /** 状态（available-可用，booked-已预约） */
    private String status;
}