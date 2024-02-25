package com.example.mission01.domain.dto;

import com.example.mission01.domain.entity.Board;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class BoardRequestDto {

    @AllArgsConstructor
    @Getter
    public static class EditBoardRequestDto {

        private String title;
        private String writer;
        private String contents;
        private String password;
    }

    @AllArgsConstructor
    @Getter
    public static class WriteBoardRequestDto {

        @NotEmpty(message = "제목을 입력해주세요.")
        private String title;

        @NotEmpty(message = "작성자를 입력해주세요.")
        private String writer;

        @NotEmpty(message = "비밀번호를 입력해주세요.")
        private String password;

        @NotEmpty(message = "내용을 입력해주세요.")
        private String contents;

        public Board toEntity() {
            return Board.builder()
                    .title(this.title)
                    .writer(this.writer)
                    .password(this.password)
                    .contents(this.contents)
                    .build();
        }
    }
}
