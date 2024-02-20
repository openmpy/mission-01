package com.example.mission01.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BoardEditRequestDto {

    private String title;
    private String writer;
    private String contents;
    private String password;
}
