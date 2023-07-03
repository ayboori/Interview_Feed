package com.tenminute.interview_feed.dto;

import com.tenminute.interview_feed.entity.Reply;

import java.time.LocalDateTime;

public class ReplyResponseDto {
    private Long id;
    private String nickname;
    private String contents;
    private LocalDateTime created_at;


    public ReplyResponseDto(Reply reply) {
        this.id = reply.getId();
        this.nickname = reply.getNickname();
        this.contents = reply.getContent();
        this.created_at = reply.getCreated_at();
    }
}
