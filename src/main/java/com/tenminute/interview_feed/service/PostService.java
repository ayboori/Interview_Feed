package com.tenminute.interview_feed.service;


import com.tenminute.interview_feed.dto.PostRequestDto;
import com.tenminute.interview_feed.dto.PostResponseDto;
import com.tenminute.interview_feed.entity.Post;
import com.tenminute.interview_feed.entity.User;
import com.tenminute.interview_feed.jwt.JwtUtil;
import com.tenminute.interview_feed.repository.HashtagRepository;
import com.tenminute.interview_feed.repository.PostRepository;
import com.tenminute.interview_feed.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final HashtagRepository hashtagRepository;
    private final JwtUtil jwtUtil;

    // 게시글 작성
    @Transactional
    public PostResponseDto createPost(PostRequestDto requestDto, HttpServletRequest request) {

        // 토큰 체크 추가
        User user = checkToken(request);
        if(user == null) {
            throw new IllegalArgumentException("인증되지 않은 사용자입니다.");
        }

        //request에서 받은 해시태그 리스트로 해시태그 생성. 기존에 있으면 가져오기
//        List<TagPostTable> tagPostTableList = createTagPostTableList(requestDto);

        //생성한, 태그테이블리스트와 함께 Post 생성
//        Post post = new Post(requestDto, user, tagPostTableList);
        Post post = new Post(requestDto, user);

        postRepository.save(post);
        return new PostResponseDto(post);
    }


    // 전체 게시글 조회
    @Transactional
    public List<PostResponseDto> getPosts() {
        List<Post> posts = postRepository.findAll();
        List<PostResponseDto> postResponseDto = new ArrayList<>();

        for(Post post : posts) {
            postResponseDto.add(new PostResponseDto(post));
        }

        return postResponseDto;
    }

    // 선택 게시글 조회
    @Transactional(readOnly = true)
    public PostResponseDto getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("아이디가 일치하지 않습니다"));
        return new PostResponseDto(post);
    }

    // 게시글 수정
    @Transactional
    public PostResponseDto updatePost(Long id, PostRequestDto requestDto, HttpServletRequest request) {

        // 토큰 체크 추가
        User user = checkToken(request);

        if(user == null) {
            throw new IllegalArgumentException("인증되지 않은 사용자입니다.");
        }

        Post post = postRepository.findById(id).orElseThrow(
                ()-> new NullPointerException("해당 글이 존재하지 않습니다.")
        );

        if(!post.getUser().equals(user)) {
            throw new IllegalArgumentException("글 작성자가 아닙니다.");
        }

        post.update(requestDto);
        return new PostResponseDto(post);
    }

    // 게시글 삭제
    @Transactional
    public void deletePost (Long id, HttpServletRequest request) {

        // 토큰 체크 추가
        User user = checkToken(request);

        if(user == null) {
            throw new IllegalArgumentException("인증되지 않은 사용자입니다.");
        }

        Post post = postRepository.findById(id).orElseThrow(
                ()-> new NullPointerException("해당 글이 존재하지 않습니다.")
        );

        if (post.getUser().equals(user)) {
            postRepository.delete(post);
        }
    }

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


//    private List<TagPostTable> createTagPostTableList(PostRequestDto requestDto) {
//        List<TagPostTable> tagPostTableList = null;
//        //requestDto에서 받은 해시태크리스트로 List<HashTag> 생성
//        for (String name : requestDto.getHashtagList()) {
//            Hashtag hashtag;
//            //기존 해시태그가 있다면 기존거로 진행, 없으면 생성.
//            hashtag = !hashtagRepository.existsByNameIgnoreCaseAllIgnoreCase(name)
//                    ? new Hashtag(name) : hashtagRepository.findByNameIgnoreCase(name);
//
//            tagPostTableList.add(new TagPostTable(hashtag));
//        }
//        return tagPostTableList;
//    }
}
