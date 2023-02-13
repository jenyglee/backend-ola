package com.project.sparta.user.service;

import com.project.sparta.admin.entity.StatusEnum;
import com.project.sparta.exception.CustomException;
import com.project.sparta.exception.api.Status;
import com.project.sparta.hashtag.entity.Hashtag;
import com.project.sparta.hashtag.repository.HashtagRepository;
import com.project.sparta.security.jwt.JwtUtil;
import com.project.sparta.user.dto.UserLoginDto;
import com.project.sparta.user.dto.UserResponseDto;
import com.project.sparta.user.dto.UserSignupDto;
import com.project.sparta.user.entity.User;
import com.project.sparta.user.entity.UserGradeEnum;
import com.project.sparta.user.entity.UserRoleEnum;
import com.project.sparta.user.entity.UserTag;
import com.project.sparta.user.repository.UserRepository;
import com.project.sparta.user.repository.UserRepositoryImpl;
import com.project.sparta.user.repository.UserTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

import static com.project.sparta.admin.entity.StatusEnum.USER_REGISTERED;
import static com.project.sparta.exception.api.Status.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final HashtagRepository hashtagRepository;
    private final UserTagRepository userTagRepository;
    private final JwtUtil jwtUtil;

    //회원가입
    @Override
    public void signup(UserSignupDto signupDto) {
        // 1. User를 생성해서 repository에 저장한다.
        String encodedPassword = passwordEncoder.encode(signupDto.getPassword());
        User user1 = new User(signupDto.getEmail(), encodedPassword, signupDto.getNickName(), signupDto.getAge(), signupDto.getPhoneNumber(), signupDto.getImageUrl());
        User saveUser = userRepository.save(user1);

        // 2. 선택한 hashtag를 각각 Usertag로 테이블에 저장한다.
        List<Long> longList = signupDto.getTagList();
        List<UserTag> userTagList = new ArrayList<>();
        for (Long along : longList) {
            Hashtag hashtag = hashtagRepository.findById(along).orElseThrow(() -> new CustomException(NOT_FOUND_HASHTAG));
            UserTag userTag = new UserTag(saveUser, hashtag);
            userTagRepository.save(userTag);
            userTagList.add(userTag);
        }

        // 3. User에 List<UserTag>를 넣어준다.
        saveUser.updateUserTags(userTagList);
    }
    //로그인
    @Override
    public void login(UserLoginDto userLoginDto, HttpServletResponse response) {
        User user = userRepository.findByEmail(userLoginDto.getEmail()).orElseThrow(() -> new CustomException(NOT_FOUND_USER));
        if (!passwordEncoder.matches(userLoginDto.getPassword(), user.getPassword())) {
            throw new CustomException(NOT_MATCH_PASSWORD);
        }
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getEmail(), user.getRole()));
    }


}
