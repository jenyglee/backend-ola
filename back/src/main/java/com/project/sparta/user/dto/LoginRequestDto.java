package com.project.sparta.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Getter
@NoArgsConstructor
public class LoginRequestDto {

    @ApiModelProperty(example = "user0@naver.com")
    private String email;

    @ApiModelProperty(example = "스웨거로 전달할 패스워드")
    private String password;

}
