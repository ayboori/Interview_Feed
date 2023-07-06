package com.tenminute.interview_feed.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.UnsupportedEncodingException;


@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender emailSender;
    private final SpringTemplateEngine templateEngine;
    private String authcode;

    // 인증 코드 생성
    public String createCode() {

        return "test123";
    }

    // 메일 양식 생성
    public MimeMessage createEmailForm(String email) throws MessagingException, UnsupportedEncodingException {

        authcode = createCode(); // 코드 생성

        // 보내는 사람
        String setFrom = "ftest9857@gmail.com";
        // 받는 사람
        String toEmail = email;
        // 제목
        String subject = "[인증번호] Interview Feed 회원가입 인증 번호";

        MimeMessage message = emailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, email); // 보낼 이메일 설정
        message.setSubject(subject); // 제목 설정
        message.setFrom(setFrom); // 보내는 이메일 설정
        message.setText(setContext(authcode), "utf-8", "html");

        return message;
    }

    // 메일 전송
    public String sendEmail(String toEmail) throws MessagingException, UnsupportedEncodingException {

        // 메일 전송에 필요한 정보 설정
        MimeMessage emailForm = createEmailForm(toEmail);
        // 실제 메일 전송
        emailSender.send(emailForm);

        return authcode; // 인증코드 반환
    }


    // context 설정
    private String setContext(String authcode) {
        Context context = new Context();
        context.setVariable("authcode", authcode);

        return templateEngine.process("mail", context); // mail.html 호출
    }

    public void confirmAuthcode(String input) {
        if(!authcode.equals(input)) {
            throw new IllegalArgumentException("인증코드가 일치하지 않습니다.");
        }
    }
}
