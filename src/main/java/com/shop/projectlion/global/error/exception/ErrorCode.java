package com.shop.projectlion.global.error.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    // 인증
    ALREADY_REGISTERED_MEMBER(400, "이미 가입된 회원 입니다."),
    MISMATCHED_PASSWORD(401, "패스워드가 일치하지 않습니다."),
    LOGIN_ERROR(401, "아이디 또는 비밀번호를 확인해주세요")
    ;

    ErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }

    private int status;
    private String message;

}