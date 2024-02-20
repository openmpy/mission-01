package com.example.mission01.domain.service;

import com.example.mission01.domain.dto.*;
import com.example.mission01.domain.entity.Board;
import com.example.mission01.domain.repository.BoardRepository;
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
        BoardWriteRequestDto requestDto = new BoardWriteRequestDto("제목", "홍길동", "1234", "내용");

        // stub
        when(boardRepository.save(any())).thenReturn(requestDto.toEntity());

        // when
        BoardWriteResponseDto responseDto = boardService.write(requestDto);

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
        BoardReadResponseDto responseDto = boardService.read(board.getId());

        // then
        Assertions.assertEquals(1, responseDto.getId());
        Assertions.assertEquals("제목", responseDto.getTitle());
        Assertions.assertEquals("내용", responseDto.getContents());
    }

    @Test
    @DisplayName("실패 - 찾을 수 없는 게시글을 조회한다.")
    void read_02() throws Exception {
        // when & then
        Assertions.assertThrows(RuntimeException.class, () -> boardService.read(1L));
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
        List<BoardReadResponseDto> responseDtoList = boardService.readList();

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

        BoardEditRequestDto requestDto = new BoardEditRequestDto("제목2", "손흥민", "내용2", "1234");

        // stub
        when(boardRepository.findById(anyLong())).thenReturn(Optional.of(board));

        // when
        BoardEditResponseDto responseDto = boardService.edit(board.getId(), requestDto);

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

        BoardEditRequestDto requestDto = new BoardEditRequestDto("제목2", "손흥민", "내용2", "12345");

        // when & then
        Assertions.assertThrows(RuntimeException.class, () -> boardService.edit(board.getId(), requestDto));
    }
}