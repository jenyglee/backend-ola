
package com.project.sparta.user.dto;

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
    //@NotBlank(message = "이메일은 필수 입력값입니다.")
    //@Email
    private String email;
    //@NotBlank(message = "비밀번호는 필수 입력값입니다.")
    //@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,16}$", message = "비밀번호는 8~16자리수여야 합니다. 영문 대소문자, 숫자, 특수문자를 1개 이상 포함해야 합니다.")
    private String password;

    //@NotBlank(message = "닉네임은 필수 입력값입니다.")
    //@Pattern(regexp = "^[가-힣a-zA-Z0-9]{2,10}$" , message = "닉네임은 특수문자를 포함하지 않은 2~10자리여야 합니다.")
    private String nickName;
    private int age;
    private String phoneNumber;
    private String imageUrl;
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


