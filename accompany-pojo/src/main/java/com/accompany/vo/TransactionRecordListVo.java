package com.accompany.vo;

import lombok.Data;
import java.util.List;

/**
 * 交易记录列表返回对象
 */
@Data
public class TransactionRecordListVo {
    /** 交易记录列表 */
    private List<TransactionRecordVo> list;

    /** 总记录数 */
    private Integer total;

    /** 当前页码 */
    private Integer page;

    /** 每页数量 */
    private Integer pageSize;
}