package com.example.mission01.global.handler.exception;

import lombok.Getter;

@Getter
public class CustomBoardException extends RuntimeException {

    public CustomBoardException(String message) {
        super(message);
    }
}
