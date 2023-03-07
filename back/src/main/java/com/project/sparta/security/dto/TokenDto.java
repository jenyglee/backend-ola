package com.project.sparta.security.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TokenDto {
    @ApiModelProperty(example = "로그아웃 엑세스 토큰")
    private String accessToken;

    @ApiModelProperty(example = "유효한 리프레쉬 토큰")
    private String refreshToken;

    @ApiModelProperty(example = "로그인한 유저 닉네임")
    private String nickname;

    @Builder
    public TokenDto(String accessToken, String refreshToken, String nickname) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.nickname = nickname;
    }
}
