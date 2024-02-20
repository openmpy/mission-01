package com.example.mission01.global.handler.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    INVALID_BOARD_ID("찾을 수 없는 게시글 번호입니다."),
    WRONG_BOARD_PASSWORD("잘못된 게시글 비밀번호입니다.");

    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }
}
