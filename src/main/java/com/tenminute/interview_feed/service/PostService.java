package com.tenminute.interview_feed.service;


import com.tenminute.interview_feed.dto.PostRequestDto;
import com.tenminute.interview_feed.dto.PostResponseDto;
import com.tenminute.interview_feed.entity.Post;
import com.tenminute.interview_feed.entity.Tag;
import com.tenminute.interview_feed.entity.TagPostTable;
import com.tenminute.interview_feed.entity.User;
import com.tenminute.interview_feed.jwt.JwtUtil;
import com.tenminute.interview_feed.repository.PostRepository;
import com.tenminute.interview_feed.repository.TagPostTableRepository;
import com.tenminute.interview_feed.repository.TagRepository;
import com.tenminute.interview_feed.repository.UserRepository;
import com.tenminute.interview_feed.specification.PostSpecification;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final TagPostTableRepository tagPostTableRepository;
    private final JwtUtil jwtUtil;
    private final PostSpecification postSpecification;

    // 게시글 작성
    public PostResponseDto createPost(PostRequestDto requestDto, HttpServletRequest request) {
        // 토큰 체크 추가
        User user = checkToken(request);
        if (user == null) {
            throw new IllegalArgumentException("인증되지 않은 사용자입니다.");
        }

        //Post 객체 만들기 (Tag나 TagPostTable없이)
        Post post = new Post(requestDto, user);
        //List<Tag> 콜렉션 만들기
        List<Tag> tagList = createTagListFromRequest(requestDto);
        //Post 객체 DB에 저장
        postRepository.save(post);
        //TagPostTable 콜렉션 만들고 각 TagPostTable 객체들 DB에 저장. 생성시 연관관계 설정 (Tag, Post넣어주기)
        List<TagPostTable> tagPostTableList = createTagPostTableList(post, tagList);
        //TagPostTable (외래키의 주인) 마지막에 DB에 저장
        for (TagPostTable tagPostTable : tagPostTableList) {
            tagPostTableRepository.save(tagPostTable);
        }
        return new PostResponseDto(post);
    }


    // 전체 게시글 조회
    @Transactional
    public List<PostResponseDto> getPosts() {
        List<Post> postList = postRepository.findAllByOrderByCreatedAtDesc();


        List<PostResponseDto> postResponseDto = new ArrayList<>();

        for (Post post : postList) {
            postResponseDto.add(new PostResponseDto(post));
        }

        return postResponseDto;
    }

    //태그로 포스트 조회하기 (오직 1개 태그인 경우)
    public List<PostResponseDto> getPostsBySingleTag(List<String> strings) {
        //해당 태그로 모든 Post 가져오기.
        List<Post> postList = postRepository.findAllByTagPostTableList_Tag_NameOrderByCreatedAtDesc(strings.get(0));

        //Entity -> ResponseDto
        return entityToResponse(postList);
    }

    //태그로 포스트 조회하기 (2개 이상 태그)
    public List<PostResponseDto> getPostsByMultiTags(List<String> strings) {

        //이름으로 모든 태그 객체 찾기. N개의 태그가 생성
        List<Tag> tagList = tagRepository.findAllByNameIn(strings);

        //태그로 tagPostTable 모두 가져오기
        List<TagPostTable> tagPostTableList = tagPostTableRepository.findAllByTagInOrderByPost_CreatedAtDesc(tagList);

        //Post의 중복을 세는 countMap생성.
        //처음 createdAt 내림차순으로 가져온 순서를 지키기 위해 LinkedHashMap으로 생성
        Map<Post, Integer> countMap = new LinkedHashMap<>();
        for (TagPostTable tagPostTable : tagPostTableList) {
            countMap.put(tagPostTable.getPost(), countMap.getOrDefault(tagPostTable.getPost(), 0) + 1);
        }

        //postList에 countMap의 값이 총 태그의 숫자와 같은 post만 가져오기
        //최종적으로 N개의 태그를 가진 Post만 postList에 담김.
        List<Post> postList = new ArrayList<>();
        for (Map.Entry<Post, Integer> entry : countMap.entrySet()) {
            if (entry.getValue() == tagList.size()) {
                postList.add(entry.getKey());
            }
        }

        //Entity -> ResponseDto
        return entityToResponse(postList);
    }

    // 선택 게시글 조회
    @Transactional(readOnly = true)
    public PostResponseDto getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("아이디가 일치하지 않습니다"));
        return new PostResponseDto(post);
    }

    // 게시글 수정
    @Transactional
    public PostResponseDto updatePost(Long id, PostRequestDto requestDto, HttpServletRequest request) {

        // 토큰 체크 추가
        User user = checkToken(request);

        if (user == null) {
            throw new IllegalArgumentException("인증되지 않은 사용자입니다.");
        }

        Post post = postRepository.findById(id).orElseThrow(
                () -> new NullPointerException("해당 글이 존재하지 않습니다.")
        );

        if (!post.getUser().equals(user)) {
            throw new IllegalArgumentException("글 작성자가 아닙니다.");
        }

        post.update(requestDto);
        return new PostResponseDto(post);
    }

    // 게시글 삭제
    @Transactional
    public void deletePost(Long id, HttpServletRequest request) {

        // 토큰 체크 추가
        User user = checkToken(request);

        if (user == null) {
            throw new IllegalArgumentException("인증되지 않은 사용자입니다.");
        }

        Post post = postRepository.findById(id).orElseThrow(
                () -> new NullPointerException("해당 글이 존재하지 않습니다.")
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

    private List<PostResponseDto> entityToResponse(List<Post> postList) {
        List<PostResponseDto> postResponseDtoList = new ArrayList<>();
        for (Post post : postList) {
            postResponseDtoList.add(new PostResponseDto(post));
        }
        return postResponseDtoList;
    }

    private List<Tag> createTagListFromRequest(PostRequestDto requestDto) {
        List<Tag> tagList = new ArrayList<>();
        for (String tagName : requestDto.getTagList()) {
            Tag tag = null;
            //기존 해시태그가 있다면 기존거로 진행, 없으면 생성.
            if (!tagRepository.existsByNameIgnoreCaseAllIgnoreCase(tagName)) {
                tag = new Tag(tagName);
                //DB에 tag 저장
                tagRepository.save(tag);
            } else {
                tag = tagRepository.findByNameIgnoreCase(tagName);
            }
            tagList.add(tag);
        }
        return tagList;
    }

    private List<TagPostTable> createTagPostTableList(Post post, List<Tag> tagList) {
        List<TagPostTable> tagPostTableList = new ArrayList<>();
        for (Tag tag : tagList) {
            TagPostTable tagPostTable = new TagPostTable(tag, post);
            TagPostTable saveTagPostTable = tagPostTableRepository.save(tagPostTable);
            tagPostTableList.add(saveTagPostTable);
        }
        return tagPostTableList;
    }
}
