package com.shop.projectlion.global.error.exception;

public class OutofStockException extends RuntimeException {

    public OutofStockException(String errorMessage) {
        super(errorMessage);
    }
}
