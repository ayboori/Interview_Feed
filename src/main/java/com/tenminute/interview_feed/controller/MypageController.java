package com.tenminute.interview_feed.controller;

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

    // mypage 보기 controller
    @GetMapping("/mypage?={id}")
    // 주소창의 id + 헤더에서 가져올 토큰 값
    public UserResponseDto showMypage(@PathVariable Long id, HttpServletRequest request) {
        return mypageService.showMypage(id,request);
    }
}
