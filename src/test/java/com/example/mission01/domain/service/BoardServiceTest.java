package com.example.mission01.domain.service;

import com.example.mission01.domain.dto.BoardRequestDto.EditBoardRequestDto;
import com.example.mission01.domain.dto.BoardRequestDto.WriteBoardRequestDto;
import com.example.mission01.domain.dto.BoardResponseDto.EditBoardResponseDto;
import com.example.mission01.domain.dto.BoardResponseDto.ReadBoardResponseDto;
import com.example.mission01.domain.dto.BoardResponseDto.WriteBoardResponseDto;
import com.example.mission01.domain.entity.Board;
import com.example.mission01.domain.repository.BoardRepository;
import com.example.mission01.global.handler.exception.CustomBoardException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.LongStream;

import static com.example.mission01.global.handler.exception.ErrorCode.INVALID_BOARD_ID;
import static com.example.mission01.global.handler.exception.ErrorCode.WRONG_BOARD_PASSWORD;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BoardServiceTest {

    @InjectMocks
    private BoardService boardService;

    @Mock
    private BoardRepository boardRepository;

    @Test
    @DisplayName("게시글을 작성한다.")
    void write_01() throws Exception {
        // given
        WriteBoardRequestDto requestDto = new WriteBoardRequestDto("제목", "홍길동", "1234", "내용");

        // stub
        when(boardRepository.save(any())).thenReturn(requestDto.toEntity());

        // when
        WriteBoardResponseDto responseDto = boardService.write(requestDto);

        // then
        Assertions.assertEquals("제목", responseDto.getTitle());
        Assertions.assertEquals("홍길동", responseDto.getWriter());
        Assertions.assertEquals("내용", responseDto.getContents());
    }

    @Test
    @DisplayName("게시글을 조회한다.")
    void read_01() throws Exception {
        // given
        Board board = Board.builder()
                .id(1L)
                .title("제목")
                .contents("내용")
                .password("1234")
                .build();

        // stub
        when(boardRepository.findById(anyLong())).thenReturn(Optional.of(board));

        // when
        ReadBoardResponseDto responseDto = boardService.read(board.getId());

        // then
        Assertions.assertEquals(1, responseDto.getId());
        Assertions.assertEquals("제목", responseDto.getTitle());
        Assertions.assertEquals("내용", responseDto.getContents());
    }

    @Test
    @DisplayName("실패 - 찾을 수 없는 게시글을 조회한다.")
    void read_02() throws Exception {
        // when & then
        CustomBoardException exception = Assertions.assertThrows(CustomBoardException.class, () ->
                boardService.read(1L));
        Assertions.assertEquals(exception.getMessage(), INVALID_BOARD_ID.getMessage());
    }

    @Test
    @DisplayName("게시글 목록을 조회한다.")
    void readList_01() throws Exception {
        // given
        List<Board> boardList = new ArrayList<>();

        LongStream.range(0, 10).forEach(i -> {
            Board board = Board.builder()
                    .id(i)
                    .title("제목 " + i)
                    .contents("내용 " + i)
                    .password("1234")
                    .build();

            boardList.add(board);
        });

        // stub
        when(boardRepository.findAllByOrderByCreatedAtDesc()).thenReturn(boardList);

        // when
        List<ReadBoardResponseDto> responseDtoList = boardService.readList();

        // then
        Assertions.assertEquals(10, responseDtoList.size());
        Assertions.assertEquals("제목 9", responseDtoList.get(9).getTitle());
    }

    @Test
    @DisplayName("게시글을 수정한다.")
    void edit_01() throws Exception {
        // given
        Board board = Board.builder()
                .id(1L)
                .title("제목")
                .contents("내용")
                .password("1234")
                .build();

        EditBoardRequestDto requestDto = new EditBoardRequestDto("제목2", "손흥민", "내용2", "1234");

        // stub
        when(boardRepository.findById(anyLong())).thenReturn(Optional.of(board));

        // when
        EditBoardResponseDto responseDto = boardService.edit(board.getId(), requestDto);

        // then
        Assertions.assertEquals("제목2", responseDto.getTitle());
        Assertions.assertEquals("손흥민", responseDto.getWriter());
        Assertions.assertEquals("내용2", responseDto.getContents());
    }

    @Test
    @DisplayName("실패 - 비밀번호가 일치하지 않아 게시글을 수정하지 못한다.")
    void edit_02() throws Exception {
        // given
        Board board = Board.builder()
                .id(1L)
                .title("제목")
                .contents("내용")
                .password("1234")
                .build();

        EditBoardRequestDto requestDto = new EditBoardRequestDto("제목2", "손흥민", "내용2", "12345");

        // stub
        when(boardRepository.findById(anyLong())).thenReturn(Optional.of(board));

        // when & then
        CustomBoardException exception = Assertions.assertThrows(CustomBoardException.class, () ->
                boardService.edit(board.getId(), requestDto));
        Assertions.assertEquals(exception.getMessage(), WRONG_BOARD_PASSWORD.getMessage());
    }

    @Test
    @DisplayName("게시글을 삭제한다.")
    void delete_01() throws Exception {
        // given
        Board board = Board.builder()
                .id(1L)
                .title("제목")
                .contents("내용")
                .password("1234")
                .build();

        // stub
        when(boardRepository.findById(anyLong())).thenReturn(Optional.of(board));

        // when
        long responseId = boardService.delete(board.getId(), board.getPassword());

        // then
        Assertions.assertEquals(1L, responseId);
    }

    @Test
    @DisplayName("실패 - 비밀번호가 일치하지 않아 게시글을 삭제하지 못한다.")
    void delete_02() throws Exception {
        // given
        Board board = Board.builder()
                .id(1L)
                .title("제목")
                .contents("내용")
                .password("1234")
                .build();

        // stub
        when(boardRepository.findById(anyLong())).thenReturn(Optional.of(board));

        // when & then
        CustomBoardException exception = Assertions.assertThrows(CustomBoardException.class, () ->
                boardService.delete(board.getId(), "12345"));
        Assertions.assertEquals(exception.getMessage(), WRONG_BOARD_PASSWORD.getMessage());
    }
}