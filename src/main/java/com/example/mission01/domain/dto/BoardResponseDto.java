package com.example.mission01.domain.dto;

import com.example.mission01.domain.entity.Board;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;

public class BoardResponseDto {

    @Getter
    public static class EditBoardResponseDto {

        private final Long id;
        private final String title;
        private final String writer;
        private final String contents;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private final LocalDateTime createdAt;

        public EditBoardResponseDto(Board board) {
            this.id = board.getId();
            this.title = board.getTitle();
            this.writer = board.getWriter();
            this.contents = board.getContents();
            this.createdAt = board.getCreatedAt();
        }
    }

    @Getter
    public static class ReadBoardResponseDto {

        private final Long id;
        private final String title;
        private final String writer;
        private final String contents;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private final LocalDateTime createdAt;

        public ReadBoardResponseDto(Board board) {
            this.id = board.getId();
            this.title = board.getTitle();
            this.writer = board.getWriter();
            this.contents = board.getContents();
            this.createdAt = board.getCreatedAt();
        }
    }

    @Getter
    public static class WriteBoardResponseDto {

        private final String title;
        private final String writer;
        private final String contents;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private final LocalDateTime createdAt;

        public WriteBoardResponseDto(Board board) {
            this.title = board.getTitle();
            this.writer = board.getWriter();
            this.contents = board.getContents();
            this.createdAt = board.getCreatedAt();
        }
    }
}
