package com.example.mission01.web;

import com.example.mission01.domain.dto.BoardWriteRequestDto;
import com.example.mission01.domain.dto.BoardWriteResponseDto;
import com.example.mission01.domain.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/boards")
@RestController
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/")
    public ResponseEntity<?> writeBoard(@RequestBody BoardWriteRequestDto requestDto) {
        BoardWriteResponseDto responseDto = boardService.write(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
}
