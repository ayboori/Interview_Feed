package com.tenminute.interview_feed.service;

import com.tenminute.interview_feed.dto.PostRequestDto;
import com.tenminute.interview_feed.dto.PostResponseDto;
import com.tenminute.interview_feed.dto.UserRequestDto;
import com.tenminute.interview_feed.dto.UserResponseDto;
import com.tenminute.interview_feed.entity.Post;
import com.tenminute.interview_feed.entity.User;
import com.tenminute.interview_feed.jwt.JwtUtil;
import com.tenminute.interview_feed.repository.UserRepository;
import com.tenminute.interview_feed.security.UserDetailsImpl;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MypageService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    public UserResponseDto showMypage(Long id, User user){

       if(user == null){ // 로그인 안 된 상태
           throw new IllegalArgumentException("로그인을 먼저 해 주세요.");
           // 로그인 페이지로 redirect 해볼까 생각 중.. 로그인 구현 이후 수정!
       }

       if(user.getId() != id){ // 토근의 id =/= url로 받아온 id
           throw new IllegalArgumentException("사용자 권한이 없습니다.");
       }
       return new UserResponseDto(user); // 반환 객체에 입력받은 user 객체 담아 리턴
    }

    @Transactional
    public UserResponseDto updateMypage(Long id, UserRequestDto requestDto, User user) {
        // 해당 메모가 DB에 존재하는지 확인

        if(user == null) {
            throw new IllegalArgumentException("인증되지 않은 사용자입니다.");
        }

        // 변경할 값 이외에는 다 세팅해주기 ... 필요하면?

        if (user.getId().equals(id)) { // 로그인 사용자 == 작성자
            // user 정보 수정
            user.update(requestDto);
            return new UserResponseDto(user); // 반환 객체에 변경 완료 객체 담아서 return
        } else {
            throw new IllegalArgumentException("수정 권한이 없는 사용자입니다.");
        }
    }

    private User findUser(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 메모는 존재하지 않습니다.")
        );
    }
}
