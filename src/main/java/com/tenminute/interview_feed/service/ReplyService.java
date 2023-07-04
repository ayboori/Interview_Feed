package com.tenminute.interview_feed.service;

import com.tenminute.interview_feed.dto.ReplyRequestDto;
import com.tenminute.interview_feed.dto.ReplyResponseDto;
import com.tenminute.interview_feed.entity.Post;
import com.tenminute.interview_feed.entity.Reply;
import com.tenminute.interview_feed.entity.User;
import com.tenminute.interview_feed.jwt.JwtUtil;
import com.tenminute.interview_feed.repository.PostRepository;
import com.tenminute.interview_feed.repository.ReplyRepository;
import com.tenminute.interview_feed.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public ReplyService(ReplyRepository replyRepository, PostRepository postRepository, UserRepository userRepository, JwtUtil jwtUtil) {
        this.replyRepository = replyRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    public ReplyResponseDto createReply(Long id, ReplyRequestDto requestDto, User user) {

        //게시글의 DB저장 유무 확인 및 가져오기
        Post post = findPost(id); // 매번 불러와야 하는 지??

        Reply reply = new Reply(requestDto, post, user);
        // DB 저장
        Reply saveReply = replyRepository.save(reply);

        //만들어진 댓글을 리턴하는 형태
        return new ReplyResponseDto(saveReply);
    }


    @Transactional
    public ReplyResponseDto updateReply(Long id, ReplyRequestDto requestDto) {
        // 해당 메모가 DB에 존재하는지 확인
        Reply reply = findReply(id);

        String username = getUserDetailsFromPrincipal().getUsername();

        if (username.equals(reply.getUser().getUsername())) { //done 인가는 이미 걸어뒀고, 유저네임이 같은지 확인.

            // Post 내용 수정
            reply.update(requestDto);
        }
        return new ReplyResponseDto(reply);
    }

    public Boolean deletePost(Long id) {
        Boolean deleteResult;

        // 해당 댓글이 DB에 존재하는지 확인
        Reply reply = findReply(id);

        // Post 삭제
        //username확인
        String username = getUserDetailsFromPrincipal().getUsername();

        if (username.equals(reply.getUser().getUsername())) {
            replyRepository.delete(reply);
            deleteResult = true;
        } else {
            deleteResult = false;
        }
        return deleteResult;
    }

    private Post findPost(Long post_id) {
        return postRepository.findById(post_id).orElseThrow(() ->
                new IllegalArgumentException("선택한 포스팅은 존재하지 않습니다.")
        );
    }

    private Reply findReply(Long reply_id) {
        return replyRepository.findById(reply_id).orElseThrow(() ->
                new IllegalArgumentException("선택한 포스팅은 존재하지 않습니다.")
        );
    }

    private UserDetails getUserDetailsFromPrincipal() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return (UserDetails) principal;
    }

    private User getUserFromPrincipal() {
        return userRepository.findByUsername(getUserDetailsFromPrincipal().getUsername()).orElseThrow();
//        return userRepository.findByUsername(getUserDetailsFromPrincipal().getUsername()).orElseThrow();
    }


    //todo 해당 메소드를 JwtUtil로 몰아넣는게 좋을지?
    //todo User를 가져오는 방법으로 JWT가 아닌 Principal에서 가져오는 게 좋은지? 다른 좋은방법은 없는지?
    public User checkToken(HttpServletRequest request) {

        String token = jwtUtil.getJwtFromHeader(request);
        Claims claims;

        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회ㅏ
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );
            return user;
        }
        return null;
    }

}