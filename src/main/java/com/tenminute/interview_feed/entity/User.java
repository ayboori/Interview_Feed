package com.tenminute.interview_feed.entity;

import com.tenminute.interview_feed.dto.UserRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// 사용자 정보를 담은 엔티티

@Entity
@Getter
@Setter
@Table(name = "user")
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // 로그인 시 사용 (ayboori)
    @Column (nullable = false, unique = true)
    private String username;
    @Column (nullable = false)
    private String password;
    @Column (nullable = false)
    private String email;
    // 게시글 작성 시 보일 닉네임 (부리)
    @Column (nullable = false)
    private String nickname;
    // 한줄 소개
    @Column
    private String one_liner;

    public User(UserRequestDto userRequestDto, String password ){
        // dto 만드는 사람이 set 하기
        //id, one_liner 뺴고 다 세팅~!
    }
}
