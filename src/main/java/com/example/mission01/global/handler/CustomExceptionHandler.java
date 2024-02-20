package com.example.mission01.global.handler;

import com.example.mission01.global.dto.ResponseDto;
import com.example.mission01.global.handler.exception.CustomBoardException;
import com.example.mission01.global.handler.exception.CustomValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(CustomBoardException.class)
    public ResponseEntity<?> handleCustomApiException(CustomBoardException e) {
        return ResponseEntity.badRequest().body(new ResponseDto<>(false, e.getMessage(), null));
    }

    @ExceptionHandler(CustomValidationException.class)
    public ResponseEntity<?> handleValidationApiException(CustomValidationException e) {
        return ResponseEntity.badRequest().body(new ResponseDto<>(false, e.getMessage(), e.getErrorMap()));
    }
}
