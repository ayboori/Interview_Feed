package com.tenminute.interview_feed.dto;

import com.tenminute.interview_feed.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// mypage에서 user 정보 출력을 위해 생성
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    private Long id; // 일련번호
    private String username; // 로그인 시 사용할 아이디
    private String password;
    private String email;
    private String nickname; // 게시글 작성 시 보일 닉네임 (부리)
    private String one_liner;     // 한줄 소개

    public UserResponseDto(User user){
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.one_liner = user.getOne_liner();
    }
}
