package com.tenminute.interview_feed.service;

import com.tenminute.interview_feed.dto.SignupRequestDto;
import com.tenminute.interview_feed.entity.User;
import com.tenminute.interview_feed.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void signup(SignupRequestDto requestDto) {
        // pw 변환
        String password = passwordEncoder.encode(requestDto.getPassword());

        // username 중복 확인
        checkUsername(requestDto.getUsername());

        // email 중복 확인
        checkEmail(requestDto.getEmail());

        // 사용자 DB에 등록
        userRepository.save(new User(requestDto, password));
    }

    // username 중복 확인
    public void checkUsername(String username) {
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("입력하신 ID는 이미 존재하는 ID 입니다.");
        }
    }
    // email 중복 확인
    public void checkEmail(String email) {
        Optional<User> checkEmail = userRepository.findByEmail(email);
        if (checkEmail.isPresent()) {
            new IllegalArgumentException("이미 회원가입 된 이메일 입니다.");
        }
    }
}
