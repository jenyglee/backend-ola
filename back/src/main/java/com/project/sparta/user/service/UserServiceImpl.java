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
    private final JwtUtil jwtUtil;

    @Override
    public void signup(UserSignupDto signupDto) {

        // List<Long>을 List<Hashtag>로 바꿔준다.
        List<Long> longList = signupDto.getTagList();
        List<Hashtag> hashtagList = new ArrayList<>();
        for (Long along : longList) {
            Hashtag hashtag = hashtagRepository.findById(along).orElseThrow(() -> new CustomException(NOT_FOUND_HASHTAG));
            hashtagList.add(hashtag);
        }

        User user = signupDto.toEntity(passwordEncoder.encode(signupDto.getPassword()), hashtagList);
        
        userRepository.save(user);
    }

    @Override
    public void login(UserLoginDto userLoginDto, HttpServletResponse response) {
        User user = userRepository.findByEmail(userLoginDto.getEmail()).orElseThrow(() -> new CustomException(NOT_FOUND_USER));
        if (!passwordEncoder.matches(userLoginDto.getPassword(), user.getPassword())) {
            throw new CustomException(NOT_MATCH_PASSWORD);
        }
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getEmail(), user.getRole()));
        // if(!passwordEncoder.matches(userLoginDto.getPassword(), user.getPassword())){
        //     throw new IllegalArgumentException("비밀번호가 일치하지 않습니다."); //나중에 exception 처리 다시 해야함
        // }
    }


}
