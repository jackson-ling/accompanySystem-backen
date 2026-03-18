package com.accompany.exception;

import cn.hutool.core.util.ObjectUtil;
import com.accompany.base.BasicEnum;
import com.accompany.base.Result;
import com.accompany.base.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理自定义异常BaseException。
     * 返回自定义异常中的错误代码和错误消息。
     *
     * @param exception 自定义异常
     * @return 响应数据，包含错误代码和错误消息
     */
    @ExceptionHandler(BaseException.class)
    public Result<Object> handleBaseException(BaseException exception) {
        exception.printStackTrace();
        if (ObjectUtil.isNotEmpty(exception.getBasicEnum())) {
            log.error("自定义异常处理:{}", exception.getBasicEnum().getMsg());
            return Result.error(exception.getBasicEnum());
        }

        return Result.error("系统内部错误");
    }

    /**
     * 处理其他未知异常。
     * 返回HTTP响应状态码500，包含错误代码和异常堆栈信息。
     *
     * @param exception 未知异常
     * @return 响应数据，包含错误代码和异常堆栈信息
     */
    @ExceptionHandler(Exception.class)
    public Result<Object> handleUnknownException(Exception exception) {
        exception.printStackTrace();
        if (ObjectUtil.isNotEmpty(exception.getCause())) {
            log.error("其他未知异常:{}", exception.getMessage());
        }
        return Result.error(ResultCode.SERVER_ERROR,exception.getMessage().toString());
    }

}
