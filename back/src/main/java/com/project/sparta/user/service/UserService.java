package com.project.sparta.user.service;


import com.project.sparta.security.dto.TokenDto;
import com.project.sparta.user.dto.*;
import com.project.sparta.user.entity.User;
import org.springframework.http.ResponseEntity;

public interface UserService {

    void signup(UserSignupDto signupDto);
    ResponseEntity<TokenDto> login(LoginRequestDto requestDto);

    void logout(TokenDto tokenRequestDto);

    ResponseEntity<TokenDto> regenerateToken(TokenDto tokenDto);

    void validateEmail(ValidateEmailDto emailDto);

    void validateNickName(ValidateNickNameDto nickNameDto);

    void getMyInfo(User user);
}
