package com.tenminute.interview_feed.controller;

import com.tenminute.interview_feed.dto.ReplyRequestDto;
import com.tenminute.interview_feed.dto.ReplyResponseDto;
import com.tenminute.interview_feed.security.UserDetailsImpl;
import com.tenminute.interview_feed.service.ReplyService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api")
public class ReplyController {

    private final ReplyService replyService;

    public ReplyController(ReplyService replyService) {
        this.replyService = replyService;
    }

    @PostMapping("/posts/{id}/reply") // 프론트에서 url요청을 어떻게 줄 수 있는지 감이 안와서 이 부분은 된다는 가정하에 진행했습니다.
    public ReplyResponseDto createPost(@PathVariable Long id, @RequestBody ReplyRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return replyService.createReply(id, requestDto, userDetails.getUser());
    }


    @PutMapping("/reply/{replyId}") //클라이언트에서 게시글과 함께 보여지는 댓글에도 댓글 id가 같이있고 해당 요청을 보낼때 replyId와 함께 요청이 오겠죠??
    public ReplyResponseDto updatePost(@PathVariable Long replyId, @RequestBody ReplyRequestDto requestDto) {

        return replyService.updateReply(replyId, requestDto);//해당매소드가 @Transaction으로 적용되어 수정된 Post를 DB연동까지 자동 적용 Dirty Checking
    }


    @DeleteMapping("/reply/{replyId}")
    public Boolean deletePost(@PathVariable Long replyId, HttpServletResponse response) {
        response.setStatus(200);
        response.setHeader("message","Reply-Deleted-Success");
        return replyService.deletePost(replyId);
    }
}