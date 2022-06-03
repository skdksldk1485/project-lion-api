package com.shop.projectlion.global.error.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }

    public BusinessException(String errorMessage) {
        super(errorMessage);
    }

}