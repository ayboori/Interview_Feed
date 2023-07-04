package com.tenminute.interview_feed.controller;

import com.tenminute.interview_feed.dto.StatusResponseDto;
import com.tenminute.interview_feed.dto.UserRequestDto;
import com.tenminute.interview_feed.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j // 로깅에 사용
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public StatusResponseDto signup(@RequestBody UserRequestDto requestDto, HttpServletResponse res) {
        return userService.signup(requestDto, res);
    }

}