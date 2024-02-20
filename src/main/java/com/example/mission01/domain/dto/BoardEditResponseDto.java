package com.example.mission01.domain.dto;

import com.example.mission01.domain.entity.Board;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class BoardEditResponseDto {

    private Long id;
    private String title;
    private String writer;
    private String contents;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    public static BoardEditResponseDto fromEntity(Board board) {
        return BoardEditResponseDto.builder()
                .id(board.getId())
                .title(board.getTitle())
                .writer(board.getWriter())
                .contents(board.getContents())
                .createdAt(board.getCreatedAt())
                .build();
    }
}
