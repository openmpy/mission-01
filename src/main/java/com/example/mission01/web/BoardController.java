package com.example.mission01.web;

import com.example.mission01.domain.dto.*;
import com.example.mission01.domain.service.BoardService;
import com.example.mission01.global.dto.ResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/boards")
@RestController
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/")
    public ResponseEntity<?> writeBoard(@RequestBody @Valid WriteBoardRequestDto requestDto, BindingResult bindingResult) {
        WriteBoardResponseDto responseDto = boardService.write(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseDto<>(true, "게시글 작성", responseDto)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> readBoard(@PathVariable Long id) {
        ReadBoardResponseDto responseDto = boardService.read(id);
        return ResponseEntity.ok().body(
                new ResponseDto<>(true, "게시글 조회", responseDto)
        );
    }

    @GetMapping("/")
    public ResponseEntity<?> readBoardList() {
        List<ReadBoardResponseDto> responseDtoList = boardService.readList();
        return ResponseEntity.ok().body(
                new ResponseDto<>(true, "게시글 목록 조회", responseDtoList)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editBoard(@PathVariable Long id,
                                       @RequestBody @Valid EditBoardRequestDto requestDto,
                                       BindingResult bindingResult) {
        EditBoardResponseDto responseDto = boardService.edit(id, requestDto);
        return ResponseEntity.ok().body(
                new ResponseDto<>(true, "게시글 수정", responseDto)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBoard(@PathVariable Long id, @RequestParam("password") String password) {
        long response = boardService.delete(id, password);
        return ResponseEntity.ok().body(
                new ResponseDto<>(true, "게시글 삭제", response)
        );
    }
}
