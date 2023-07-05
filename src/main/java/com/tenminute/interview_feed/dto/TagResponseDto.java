package com.tenminute.interview_feed.dto;

import lombok.Getter;

@Getter
public class TagResponseDto {
    private String name;

    public TagResponseDto(String name) {
        this.name = name;
    }
}
