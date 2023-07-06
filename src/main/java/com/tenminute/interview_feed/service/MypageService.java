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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MypageService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    public UserResponseDto showMypage( User user){

       if(user == null){ // 로그인 안 된 상태
           throw new IllegalArgumentException("로그인을 먼저 해 주세요.");
           // 로그인 페이지로 redirect 해볼까 생각 중.. 로그인 구현 이후 수정!
       }
       return new UserResponseDto(user); // 반환 객체에 입력받은 user 객체 담아 리턴
    }

    @Transactional
    public UserResponseDto updateMypage(UserRequestDto requestDto, User user) {
        if(user == null) {
            throw new IllegalArgumentException("로그인을 먼저 해 주세요.");
        }

        User user1 = userRepository.findById(user.getId()).orElseThrow(
                ()-> new NullPointerException("해당 사용자가 존재하지 않습니다.")
        ); // DB에 있는 user을 가져와서 그 값을 수정해야 한다!

            // user 정보 수정
            user1.update(requestDto);

            return new UserResponseDto(user1); // 반환 객체에 변경 완료 객체 담아서 return
//        } else {
//            throw new IllegalArgumentException("수정 권한이 없는 사용자입니다.");
//        }
    }

    @Transactional
    public String passwordCheck(UserRequestDto requestDto,User user) {
        String password = requestDto.getPassword(); // 요청 본문의 password 받아옴

        if(passwordEncoder.matches(password, user.getPassword())) {
            // 비밀번호 변경은 다시 updateMypage로 가서 마저 하도록 할 예정
            return "비밀번호가 일치합니다";
        }else {
            throw new IllegalArgumentException("비밀번호가 다릅니다.");
        }
    }
}
