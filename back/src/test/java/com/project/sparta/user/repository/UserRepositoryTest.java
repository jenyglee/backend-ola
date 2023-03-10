package com.project.sparta.user.repository;


import static org.junit.jupiter.api.Assertions.assertThrows;

import com.project.sparta.admin.dto.UserGradeDto;
import com.project.sparta.admin.dto.UserStatusDto;
import com.project.sparta.exception.CustomException;
import com.project.sparta.user.dto.LoginRequestDto;
import com.project.sparta.user.dto.UpgradeRequestDto;
import com.project.sparta.user.dto.ValidateEmailDto;
import com.project.sparta.user.dto.ValidateNickNameDto;
import com.project.sparta.user.entity.User;
import com.project.sparta.user.service.UserServiceImpl;
import com.sun.xml.bind.v2.TODO;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserServiceImpl userService;

    private String randomUser = "user" + UUID.randomUUID();
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("이메일 or 닉네임 중복확인")
    @Transactional
    void duplicationCheck(){
        User user1 = User.userBuilder()
            .email(randomUser)
            .password("user1234!")
            .nickName("나나나나솨")
            .age(25)
            .phoneNumber("010-1234-1235")
            .build();

        userRepository.save(user1);

        ValidateEmailDto emailDto = new ValidateEmailDto(randomUser);
        ValidateNickNameDto nickNameDto = new ValidateNickNameDto("나나나나솨");

        assertThrows(CustomException.class, ()-> userService.validateEmail(emailDto));
        assertThrows(CustomException.class, ()-> userService.validateNickName(nickNameDto));
    }

    @Test
    @DisplayName("회원 정보를 찾을 수 없는 경우")
    @Transactional
    public void notFoundUser(){
        UpgradeRequestDto upgradeRequestDto = new UpgradeRequestDto("MANIA");
        UserGradeDto gradeDto = new UserGradeDto(2);
        UserStatusDto statusDto = new UserStatusDto(0);

        assertThrows(CustomException.class, ()-> userService.upgrade(upgradeRequestDto, 1234567891000000L));
        assertThrows(CustomException.class, ()-> userService.getUser(1234567891000000L));
        assertThrows(CustomException.class, ()-> userService.changeGrade(gradeDto, 1234567891000000L));
        assertThrows(CustomException.class, ()-> userService.changeStatus(statusDto, 1234567891000000L));
    }

    @Test
    @DisplayName("email, password 빈값으로 들어올 경우")
    @Transactional
    public void loginValueIsBlank(){
        LoginRequestDto loginRequestDto = new LoginRequestDto("", "");

        assertThrows(CustomException.class, ()-> userService.login(loginRequestDto));
    }

    @Test
    @DisplayName("password가 틀렸을 경우")
    @Transactional
    public void notEqualsPassword(){
        User user1 = User.userBuilder()
            .email(randomUser)
            .password("user1234!")
            .nickName("나나나나솨")
            .age(25)
            .phoneNumber("010-1234-1235")
            .build();

        userRepository.save(user1);
        LoginRequestDto loginRequestDto = new LoginRequestDto(user1.getEmail(), "password01");

        assertThrows(CustomException.class, ()-> userService.login(loginRequestDto));
    }
}
