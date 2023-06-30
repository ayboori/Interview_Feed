package com.tenminute.interview_feed.entity;

import com.tenminute.interview_feed.dto.ReplyRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;


// 댓글 엔티티
@Entity
@Getter
@Setter
@Table(name = "reply")
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column (nullable = false)
    private String nickname;
    @Column (nullable = false)
    private String content;

    // 작성 시간 업데이트 되지 않게 하기!
    @CreatedDate
    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime created_at;

    // 외래 키로 받아오기
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // 수정, 삭제를 위해 User 받아와야 한다!
    public Reply(ReplyRequestDto replyRequestDto, Post post, User user) {
        this.nickname = nickname;
        this.content = content;
        this.post = post;
        this.user = user;
    }
}
