package com.shop.projectlion.global.error.exception;

public class OutofStockException extends BusinessException {

    public OutofStockException(ErrorCode errorCode) {
        super(errorCode);
    }
}
