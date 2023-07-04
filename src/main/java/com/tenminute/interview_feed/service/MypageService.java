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


    public UserResponseDto updateMypage(Long id, UserRequestDto requestDto, User user) {

        if(user == null) {
            throw new IllegalArgumentException("로그인을 먼저 해 주세요.");
        }

        // 변경할 값 이외에는 다 세팅해주기 ... 필요하면?

        if (user.getId() != id) { // 로그인 사용자 == 작성자
            // RequestDto -> Entity

            // user 정보 수정
            user.update(requestDto);
            userRepository.update(id, requestDto);

            return new UserResponseDto(user); // 반환 객체에 변경 완료 객체 담아서 return
        } else {
            throw new IllegalArgumentException("수정 권한이 없는 사용자입니다.");
        }
    }


    // 토큰 유효 여부 확인을 위한 함수
    public User checkToken(HttpServletRequest request){
        String token = jwtUtil.getJwtFromHeader(request);
        Claims claims;

        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );
            return user;
        }
        return null;
    }
}
