package com.tenminute.interview_feed.controller;

import com.tenminute.interview_feed.dto.PostRequestDto;
import com.tenminute.interview_feed.dto.PostResponseDto;
import com.tenminute.interview_feed.dto.UserRequestDto;
import com.tenminute.interview_feed.dto.UserResponseDto;
import com.tenminute.interview_feed.security.UserDetailsImpl;
import com.tenminute.interview_feed.service.MypageService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class MypageController {
    private final MypageService mypageService;

    // mypage 보기
    @GetMapping("/mypage") // url 형식 : /mypage?id=3
    // ? 뒤에 있는 값은 parameter 값이라서 @RequestParam Long id로 사용
    // 주소창의 id + 헤더에서 가져올 토큰 값
    public UserResponseDto showMypage(@RequestParam Long id,@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return mypageService.showMypage(id,userDetails.getUser());
    }

    // mypage 편집하기
    // 토큰을 검사한 후, 유효한 토큰이면서 해당 사용자가 작성한 게시글만 수정 가능
    // 제목, 작성 내용을 수정하고 수정된 게시글을 Client 로 반환하기

    // PostRequestDto : 수정할 값 받아옴
    // HttpServletRequest : 토큰 받아옴
    @PutMapping("/mypage") // url 형식 : /mypage?id=3
    public UserResponseDto updateMypage(@RequestParam Long id, @RequestBody UserRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) { // password 대신 request 받아옴
        return mypageService.updateMypage(id, requestDto,userDetails.getUser());
    }
}
