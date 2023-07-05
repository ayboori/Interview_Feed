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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hashtag_id", nullable = false)
    private Hashtag hashtag;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    public TagPostTable(Hashtag hashtag, Post post) {
        this.hashtag = hashtag;
        this.post = post;
    }

    public TagPostTable(Hashtag hashtag) {
        this.hashtag = hashtag;
    }
}
