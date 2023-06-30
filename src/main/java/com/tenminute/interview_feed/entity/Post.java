package com.tenminute.interview_feed.entity;

import com.tenminute.interview_feed.dto.PostRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;
import java.time.LocalDateTime;

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

    public Post(PostRequestDto postRequestDto, User user) {
        // 게터로 알아서 바꾸세요..
        this.title = title;
        this.content = content;
        this.nickname = nickname;
        this.user = user;
    }
}
