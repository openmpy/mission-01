package com.example.mission01.domain.service;

import com.example.mission01.domain.dto.BoardRequestDto.EditBoardRequestDto;
import com.example.mission01.domain.dto.BoardRequestDto.WriteBoardRequestDto;
import com.example.mission01.domain.dto.BoardResponseDto.EditBoardResponseDto;
import com.example.mission01.domain.dto.BoardResponseDto.ReadBoardResponseDto;
import com.example.mission01.domain.dto.BoardResponseDto.WriteBoardResponseDto;
import com.example.mission01.domain.entity.Board;
import com.example.mission01.domain.repository.BoardRepository;
import com.example.mission01.global.handler.exception.CustomBoardException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.example.mission01.global.handler.exception.ErrorCode.INVALID_BOARD_ID;
import static com.example.mission01.global.handler.exception.ErrorCode.WRONG_BOARD_PASSWORD;

@RequiredArgsConstructor
@Transactional
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    public WriteBoardResponseDto write(WriteBoardRequestDto requestDto) {
        Board board = boardRepository.save(requestDto.toEntity());
        return new WriteBoardResponseDto(board);
    }

    @Transactional(readOnly = true)
    public ReadBoardResponseDto read(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(() ->
                new CustomBoardException(INVALID_BOARD_ID.getMessage())
        );
        return new ReadBoardResponseDto(board);
    }

    @Transactional(readOnly = true)
    public List<ReadBoardResponseDto> readList() {
        List<Board> boardList = boardRepository.findAllByOrderByCreatedAtDesc();

        List<ReadBoardResponseDto> responseDtoList = new ArrayList<>();
        boardList.forEach(board -> responseDtoList.add(new ReadBoardResponseDto(board)));
        return responseDtoList;
    }

    public EditBoardResponseDto edit(Long id, EditBoardRequestDto requestDto) {
        Board board = boardRepository.findById(id).orElseThrow(() ->
                new CustomBoardException(INVALID_BOARD_ID.getMessage())
        );
        if (!board.getPassword().equals(requestDto.getPassword())) {
            throw new CustomBoardException(WRONG_BOARD_PASSWORD.getMessage());
        }

        board.update(requestDto.getTitle(), requestDto.getWriter(), requestDto.getContents());
        return new EditBoardResponseDto(board);
    }

    public long delete(Long id, String password) {
        Board board = boardRepository.findById(id).orElseThrow(() ->
                new CustomBoardException(INVALID_BOARD_ID.getMessage())
        );
        if (!board.getPassword().equals(password)) {
            throw new CustomBoardException(WRONG_BOARD_PASSWORD.getMessage());
        }

        boardRepository.deleteById(id);
        return id;
    }
}
