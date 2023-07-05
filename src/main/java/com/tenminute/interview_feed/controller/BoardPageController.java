package com.tenminute.interview_feed.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
public class BoardPageController {

    /**
     * 게시글 리스트 페이지
     */
    @GetMapping("/list")
    public String openBoardList() {
        return "board/list";
    }

    /**
     * 게시글 등록 페이지
     */
    @GetMapping("/write")
    public String createPost() {
        return "/write";
    }

}