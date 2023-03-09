package com.project.sparta.user.service;


import com.project.sparta.admin.dto.UserGradeDto;
import com.project.sparta.admin.dto.UserStatusDto;
import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.security.dto.RegenerateTokenDto;
import com.project.sparta.security.dto.TokenDto;
import com.project.sparta.user.dto.*;
import com.project.sparta.user.entity.User;
import java.util.List;
import org.springframework.http.ResponseEntity;

public interface UserService {

    void signup(UserSignupDto signupDto);
    ResponseEntity<TokenDto> login(LoginRequestDto requestDto);

    void logout(TokenDto tokenRequestDto);

    ResponseEntity<TokenDto> regenerateToken(RegenerateTokenDto tokenDto);

    void validateEmail(ValidateEmailDto emailDto);

    void validateNickName(ValidateNickNameDto nickNameDto);

    InfoResponseDto getMyInfo(User user);

    void upgrade(UpgradeRequestDto requestDto, Long userId);

    PageResponseDto<List<UserListResponseDto>> getUserList(int page, int size);
    UserOneResponseDto getUser(Long userId);
    void changeGrade(UserGradeDto grade, Long userId);
    void changeStatus(UserStatusDto statusDto, Long userId);

    void sendMail(MailDto mailDto);

    MailDto createMailAndChangePassword(String email, String nickName);

    boolean userEmailCheck(String userEmail, String userName);
}
