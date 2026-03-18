package com.accompany.vo;

import lombok.Data;
import java.util.List;

/**
 * 收入记录列表VO
 */
@Data
public class IncomeRecordListVo {
    /** 收入记录列表 */
    private List<IncomeRecordVo> list;
    /** 总数 */
    private Integer total;
    /** 页码 */
    private Integer page;
    /** 每页数量 */
    private Integer pageSize;
}