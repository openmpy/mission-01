package com.example.mission01.domain.service;

import com.example.mission01.domain.dto.*;
import com.example.mission01.domain.entity.Board;
import com.example.mission01.domain.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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

    @Transactional(readOnly = true)
    public List<BoardReadResponseDto> readList() {
        List<Board> boardList = boardRepository.findAllByOrderByCreatedAtDesc();

        List<BoardReadResponseDto> responseDtoList = new ArrayList<>();
        boardList.forEach(board -> responseDtoList.add(BoardReadResponseDto.fromEntity(board)));
        return responseDtoList;
    }

    public BoardEditResponseDto edit(Long id, BoardEditRequestDto requestDto) {
        Board board = boardRepository.findById(id).orElseThrow(() ->
                new RuntimeException("찾을 수 없는 게시글 번호입니다.")
        );
        if (!board.getPassword().equals(requestDto.getPassword())) {
            throw new RuntimeException("게시글의 비밀번호와 일치하지 않습니다.");
        }

        board.update(requestDto.getTitle(), requestDto.getWriter(), requestDto.getContents());
        return BoardEditResponseDto.fromEntity(board);
    }
}
