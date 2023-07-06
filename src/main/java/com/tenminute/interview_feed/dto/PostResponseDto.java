package com.tenminute.interview_feed.dto;

import com.tenminute.interview_feed.entity.Post;
import com.tenminute.interview_feed.entity.Reply;
import com.tenminute.interview_feed.entity.TagPostTable;
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
    private LocalDateTime modified_at;
    private int like_count;
    private List<ReplyResponseDto> replyResponseDtoList = new ArrayList<>();
    private List<TagResponseDto> tagResponseDtoList = new ArrayList<>();


    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.nickname = post.getNickname();
        this.created_at = post.getCreatedAt();
        this.modified_at = post.getModifiedAt();
        this.like_count = post.getLike_count();

        for (Reply reply : post.getReplyList()) {
            this.replyResponseDtoList.add(new ReplyResponseDto(reply));
        }

        for (TagPostTable tagPostTable : post.getTagPostTableList()) {
            this.tagResponseDtoList.add(new TagResponseDto(tagPostTable.getTag().getName()));
        }
    }
}