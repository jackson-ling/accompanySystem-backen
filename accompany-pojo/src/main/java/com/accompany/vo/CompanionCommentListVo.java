package com.accompany.vo;

import lombok.Data;
import java.util.List;

/**
 * 陪诊师评价列表VO（带分页）
 */
@Data
public class CompanionCommentListVo {
    /** 评价列表 */
    private List<CompanionCommentVo> list;

    /** 总数 */
    private Integer total;

    /** 当前页码 */
    private Integer page;

    /** 每页数量 */
    private Integer pageSize;
}