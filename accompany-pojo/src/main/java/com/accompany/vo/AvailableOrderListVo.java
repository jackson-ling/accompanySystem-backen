package com.accompany.vo;

import lombok.Data;
import java.util.List;

/**
 * 可接订单列表VO
 */
@Data
public class AvailableOrderListVo {
    /** 订单列表 */
    private List<AvailableOrderVo> list;
    /** 总数 */
    private Integer total;
    /** 页码 */
    private Integer page;
    /** 每页数量 */
    private Integer pageSize;
}