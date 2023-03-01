package com.project.sparta.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class RegenerateTokenDto {
    private String refreshToken;
}
