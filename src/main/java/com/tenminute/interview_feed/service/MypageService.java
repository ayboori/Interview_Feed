package com.tenminute.interview_feed.service;

import com.tenminute.interview_feed.dto.UserResponseDto;
import com.tenminute.interview_feed.entity.User;
import com.tenminute.interview_feed.jwt.JwtUtil;
import com.tenminute.interview_feed.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class MypageService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    public UserResponseDto showMypage(Long id, HttpServletRequest request){
       User user = checkToken(request);

       if(user == null){ // 로그인 안 된 상태
           throw new IllegalArgumentException("로그인을 먼저 해 주세요");
           // 로그인 페이지로 redirect 해볼까 생각 중.. 로그인 구현 이후 수정!
       }

       if(user.getId() != id){ // 토근의 id =/= url로 받아온 id
           throw new IllegalArgumentException("로그인을 먼저 해 주세요");
       }

       return new UserResponseDto(user); // 반환 객체에 입력받은 user 객체 담아 리턴
    }


    // 토큰 유효 여부 확인을 위한 함수
    public User checkToken(HttpServletRequest request){
        String token = jwtUtil.getTokenFromRequest(request);
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
