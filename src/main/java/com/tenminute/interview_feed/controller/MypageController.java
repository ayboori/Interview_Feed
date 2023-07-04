package com.tenminute.interview_feed.controller;

import com.tenminute.interview_feed.dto.PostRequestDto;
import com.tenminute.interview_feed.dto.PostResponseDto;
import com.tenminute.interview_feed.dto.UserResponseDto;
import com.tenminute.interview_feed.service.MypageService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
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
    public UserResponseDto showMypage(@RequestParam Long id, HttpServletRequest request) {
        return mypageService.showMypage(id,request);
    }


}
