package com.tenminute.interview_feed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@EnableJpaAuditing // 시간 업데이트를 위한 어노테이션
public class InterviewFeedApplication {

    public static void main(String[] args) {
        SpringApplication.run(InterviewFeedApplication.class, args);
    }

}