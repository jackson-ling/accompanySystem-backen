package com.accompany.vo;

import lombok.Data;
import java.util.List;

/**
 * 服务项目列表VO（带分页）
 */
@Data
public class ServiceItemListVo {
    /** 服务项目列表 */
    private List<ServiceItemVo> list;

    /** 总数 */
    private Integer total;

    /** 当前页码 */
    private Integer page;

    /** 每页数量 */
    private Integer pageSize;
}