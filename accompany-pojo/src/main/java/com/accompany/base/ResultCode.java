package com.accompany.base;

public class ResultCode {
    // 成功
    public static final Integer SUCCESS = 200;
    // 请求参数错误
    public static final Integer PARAM_ERROR = 400;
    // 未授权 / 登录过期
    public static final Integer UNAUTHORIZED = 401;
    // 禁止访问
    public static final Integer FORBIDDEN = 403;
    // 资源不存在
    public static final Integer NOT_FOUND = 404;
    // 服务器内部错误
    public static final Integer SERVER_ERROR = 500;

}