package com.accompany.base;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result<T> implements Serializable {

    private Integer code; //编码：1成功，0和其它数字为失败
    private String msg; //错误信息
    private T data; //数据

    public static <T> Result<T> success() {
        Result<T> result = new Result<T>();
        result.code = ResultCode.SUCCESS;
        result.msg = "success";
        return result;
    }

    public static <T> Result<T> success(T object) {
        Result<T> result = new Result<T>();
        result.data = object;
        result.code = ResultCode.SUCCESS;
        result.msg = "success";
        return result;
    }

    public static <T> Result<T> error(String msg) {
        Result<T> result = new Result<T>();
        result.msg = msg;
        result.code = ResultCode.SERVER_ERROR;
        return result;
    }

    public static <T> Result<T> error(BasicEnum basicEnum) {
        Result<T> result = new Result<T>();
        result.msg = basicEnum.getMsg();
        result.code = basicEnum.getCode();
        return result;
    }

    public static <T> Result<T> error(Integer statusCode, String msg) {
        Result result = new Result();
        result.msg = msg;
        result.code = statusCode;
        return result;
    }

}