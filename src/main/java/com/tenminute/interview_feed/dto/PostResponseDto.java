package com.tenminute.interview_feed.dto;

import com.tenminute.interview_feed.entity.Post;
import com.tenminute.interview_feed.entity.Reply;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class PostResponseDto {
    private long id;
    private String title;
    private String content;
    private String nickname;
    private LocalDateTime created_at;
    private int like_count;
    private List<ReplyResponseDto> replyResponseDtoList = new ArrayList<>();

    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.nickname = post.getUser().getNickname();
        this.created_at = post.getCreated_at();
        this.like_count = post.getLike_count();

        for (Reply reply : post.getReplyList()) {
            this.replyResponseDtoList.add(new ReplyResponseDto(reply));
        }
    }
}
