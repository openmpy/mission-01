package com.example.mission01.web;

import com.example.mission01.domain.dto.BoardReadResponseDto;
import com.example.mission01.domain.dto.BoardWriteRequestDto;
import com.example.mission01.domain.dto.BoardWriteResponseDto;
import com.example.mission01.domain.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/{id}")
    public ResponseEntity<?> readBoard(@PathVariable Long id) {
        BoardReadResponseDto responseDto = boardService.read(id);
        return ResponseEntity.ok().body(responseDto);
    }

    @GetMapping("/")
    public ResponseEntity<?> readBoardList() {
        List<BoardReadResponseDto> responseDtoList = boardService.readList();
        return ResponseEntity.ok().body(responseDtoList);
    }
}
