package com.tenminute.interview_feed.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// tag-post 연결하는 중간 테이블
@Entity
@Getter
@Setter
@Table(name = "tagPostTable")
@NoArgsConstructor
public class TagPostTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // 외래 키로 받아오기
    @ManyToOne
    @JoinColumn(name = "tag_id", nullable = false)
    private Tag tag;
    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    public TagPostTable(Tag tag, Post post) {
        this.tag = tag;
        tag.getTagPostTableList().add(this);
        this.post = post;
        post.getTagPostTableList().add(this);
    }

    public TagPostTable(Tag tag) {
        this.tag = tag;
        tag.getTagPostTableList().add(this);
    }
}
