package com.tenminute.interview_feed.dto;

import com.tenminute.interview_feed.entity.Post;
import com.tenminute.interview_feed.entity.Timestamped;
import lombok.Getter;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Getter
public class PostResponseDto {
    private long id;
    private String title;
    private String content;
    private String nickname;
    private LocalDateTime created_at;
    private LocalDateTime modified_at;
    private int like_count;

    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.nickname = post.getNickname();
        this.created_at = post.getCreate_at();
        this.modified_at = post.getModified_at();
        this.like_count = post.getLike_count();
    }
}
