package com.tenminute.interview_feed.dto;

// 풀리퀘스트 생성을 위한 테스트 - 아영

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PostRequestDto {
    private String title;
    private String content;
    private List<String> tagList = new ArrayList<>();
}
