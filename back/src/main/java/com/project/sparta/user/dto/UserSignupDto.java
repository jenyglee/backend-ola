
package com.project.sparta.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
public class UserSignupDto {
    @ApiModelProperty(example = "user0@naver.com")
    private String email;
    @ApiModelProperty(example = "스웨거로 전달할 패스워드")
    private String password;
    @ApiModelProperty(example = "초보등산꾼")
    private String nickName;
    @ApiModelProperty(example = "20")
    private int age;
    @ApiModelProperty(example = "010-1234-1234")
    private String phoneNumber;

    @ApiModelProperty(example = "스웨거로 전달할 이미지url")
    private String imageUrl;

    @ApiModelProperty(example = "스웨거로 전달할 태그 리스트")
    private List<Long> tagList;

    private boolean admin = false;

    private String adminToken="";

    @Builder
    public UserSignupDto(String email,
        String password, String nickName,
        int age,
        String phoneNumber,
        String imageUrl, List<Long> tagList
    ) {
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.imageUrl = imageUrl;
        this.tagList = tagList;
    }
}


