package com.example.mission01.domain.service;

import com.example.mission01.domain.dto.BoardReadResponseDto;
import com.example.mission01.domain.dto.BoardWriteRequestDto;
import com.example.mission01.domain.dto.BoardWriteResponseDto;
import com.example.mission01.domain.entity.Board;
import com.example.mission01.domain.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    public BoardWriteResponseDto write(BoardWriteRequestDto requestDto) {
        Board board = boardRepository.save(requestDto.toEntity());
        return BoardWriteResponseDto.fromEntity(board);
    }

    @Transactional(readOnly = true)
    public BoardReadResponseDto read(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(() ->
                new RuntimeException("찾을 수 없는 게시글 번호입니다.")
        );

        return BoardReadResponseDto.fromEntity(board);
    }
}
