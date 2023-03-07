package com.project.sparta.admin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AdminSignupDto {
    @ApiModelProperty(example = "admin@naver.com")
    private String email;
    @ApiModelProperty(example = "스웨거로 전달할 패스워드")
    private String password;
    @ApiModelProperty(example = "운영자")
    private String nickName;

    @ApiModelProperty(example = "어드민 토큰")
    private String adminToken;

    @Builder
    public AdminSignupDto(String email, String password, String nickName, String adminToken) {
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.adminToken = adminToken;
    }
}
