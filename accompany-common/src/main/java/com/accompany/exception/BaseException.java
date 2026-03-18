package com.accompany.exception;

import com.accompany.base.BasicEnum;
import lombok.Data;

@Data
public class BaseException extends RuntimeException {

    private BasicEnum basicEnum;

    public BaseException(BasicEnum basicEnum) {
        this.basicEnum = basicEnum;
    }
}
