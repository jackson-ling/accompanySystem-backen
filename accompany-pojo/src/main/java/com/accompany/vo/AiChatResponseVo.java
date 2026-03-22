package com.accompany.vo;

import lombok.Data;
import java.util.List;

/**
 * AI聊天响应VO
 */
@Data
public class AiChatResponseVo {
    /** AI回复内容 */
    private String reply;

    /** 建议问题列表 */
    private List<String> suggestions;
}