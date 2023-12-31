package com.tenminute.interview_feed.controller;


import com.tenminute.interview_feed.dto.ApiResult;
import com.tenminute.interview_feed.dto.PostRequestDto;
import com.tenminute.interview_feed.dto.PostResponseDto;
import com.tenminute.interview_feed.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {

    private final PostService postService;

    // 게시글 작성 html주소
    @GetMapping("/post")
    public String createPost() {
        return "/write";
    // 메인 페이지. 전체 게시글 조회
    @GetMapping("/posts")
    public List<PostResponseDto> getPosts() {
        return postService.getPosts();
    }


    @PostMapping("/posts/tag")
    public List<PostResponseDto> getPostsByTags(@RequestBody List<String> strings){
        if(strings.size()>1) {
            return postService.getPostsByMultiTags(strings);
        } else {
            return postService.getPostsBySingleTag(strings);
        }
    }

    // 게시글 작성
    @PostMapping("/post")
    public PostResponseDto createPost(@RequestBody PostRequestDto requestDto, HttpServletRequest request) {
        return postService.createPost(requestDto,request);
    }




    // 특정 게시글 조회 html주소
//    @GetMapping("/posts/read/{id}")
//    public String readPost() {
//        return "/readId";
//    }

    // 특정 게시글 조회
    @GetMapping("/posts/{id}")
    public PostResponseDto getPost(@PathVariable Long id) {
        return postService.getPost(id);
    }

    // 게시글 수정
    @PutMapping("/posts/{id}")
    public PostResponseDto updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto, HttpServletRequest request) {
        return postService.updatePost(id, requestDto, request);
    }

    // 게시글 삭제
    @DeleteMapping("/posts/{id}")
    public ApiResult deletePost(@PathVariable Long id, HttpServletRequest request) {
        postService.deletePost(id, request);
        return new ApiResult("게시글 삭제 성공", HttpStatus.OK.value()); // 게시글 삭제 성공시 ApiResult Dto를 사용하여 성공 메세지와 statusCode를 띄움
    }
}
