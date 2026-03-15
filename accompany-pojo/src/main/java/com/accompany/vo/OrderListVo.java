package com.accompany.vo;

import lombok.Data;
import java.util.List;

/**
 * 订单列表VO（带分页）
 */
@Data
public class OrderListVo {
    /** 订单列表 */
    private List<OrderVo> list;

    /** 总数 */
    private Integer total;

    /** 当前页码 */
    private Integer page;

    /** 每页数量 */
    private Integer pageSize;
}