package com.tenminute.interview_feed.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class MailAuthRequestDto {

    @NotBlank
    private String email;
}
