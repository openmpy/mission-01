package com.example.mission01.domain.dto;

import com.example.mission01.domain.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BoardWriteRequestDto {

    private String title;
    private String writer;
    private String password;
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
