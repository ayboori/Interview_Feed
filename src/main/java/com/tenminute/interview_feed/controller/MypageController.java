package com.tenminute.interview_feed.controller;

import com.tenminute.interview_feed.dto.PostRequestDto;
import com.tenminute.interview_feed.dto.PostResponseDto;
import com.tenminute.interview_feed.dto.UserRequestDto;
import com.tenminute.interview_feed.dto.UserResponseDto;
import com.tenminute.interview_feed.security.UserDetailsImpl;
import com.tenminute.interview_feed.service.MypageService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class MypageController {
    private final MypageService mypageService;

    @GetMapping("/mypage")
    public String show(){
        return "mypage";
    }

    // mypage 보기
    @ResponseBody
    @GetMapping("/my-page")
    // 헤더에서 가져올 토큰 값
    public UserResponseDto showMypage(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return mypageService.showMypage(userDetails.getUser());
    }

    // mypage 편집하기
    // 토큰을 검사한 후, 유효한 토큰이면서 해당 사용자가 작성한 게시글만 수정 가능
    // 제목, 작성 내용을 수정하고 수정된 게시글을 Client 로 반환하기

    // UserRequestDto : 수정할 값 받아옴
    // HttpServletRequest : 토큰 받아옴
    @ResponseBody
    @PutMapping("/my-page") // url 형식 : /mypage?id=3
    public UserResponseDto updateMypage(@RequestParam Long id, @RequestBody UserRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        if (requestDto.getPassword() != null){ // password 값이 들어오면
//            redirectToPassword();
//        }
        return mypageService.updateMypage(id, requestDto, userDetails.getUser());
    }

    // 수정할 정보에 password 포함되어 있을 시
    // 비밀번호 동일한지만 확인! >> 수정은 updateMypage에서 이어서 수행하게 하고 싶음

    // 리다이렉트는 프론트에서 해야할 것 같다고 답변을 받았다!
    @ResponseBody
    @PutMapping("/mypage/passwordcheck")
    public String passwordCheck(@RequestBody UserRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return  mypageService.passwordCheck(requestDto, userDetails.getUser());
    }

//    // 리다이렉트를 위한 함수
//    public String redirectToPassword() {
//        return "redirect:/mypage/passwordcheck";
//    }
}
