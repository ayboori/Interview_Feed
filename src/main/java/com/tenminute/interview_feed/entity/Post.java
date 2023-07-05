package com.tenminute.interview_feed.entity;

import com.tenminute.interview_feed.dto.PostRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// 게시글 엔티티

@Entity
@Getter
@Setter
@Table(name = "post")
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column (nullable = false)
    private String title;
    @Column (nullable = false)
    private String content;
    @Column (nullable = false)
    private String nickname;

    // 작성 시간 업데이트 되지 않게 하기!
    @CreatedDate
    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime created_at;

    // 외래키로 user_id 받아오기
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column (nullable = false)
    private int like_count;

    //todo fetch List형은 아마 기본이 LAZY라 그냥 선언만해도 될까 싶어서 이렇게 해둠
    @OneToMany(mappedBy = "post", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<Reply> replyList = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<TagPostTable> tagPostTableList = new ArrayList<>();


    public Post(PostRequestDto requestDto, User user) {
        // 게터로 알아서 바꾸세요..
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.nickname = user.getNickname();
        this.user = user;
        this.like_count = 0;
    }

    public void update(PostRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
    }
}
