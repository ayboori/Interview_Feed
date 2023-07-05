package com.tenminute.interview_feed.controller;

import com.tenminute.interview_feed.dto.MailAuthRequestDto;
import com.tenminute.interview_feed.service.MailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@RestController
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;

    @PostMapping("/api/signup/mail-confirm")
    public String mailConfirm(@RequestBody MailAuthRequestDto requestDto) throws MessagingException, UnsupportedEncodingException {
        String authcode = mailService.sendEmail(requestDto.getEmail());
        return authcode;
    }
}
