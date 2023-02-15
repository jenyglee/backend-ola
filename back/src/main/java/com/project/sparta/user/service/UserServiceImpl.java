package com.project.sparta.user.service;

import com.project.sparta.communityBoard.repository.BoardRepository;
import com.project.sparta.exception.CustomException;
import com.project.sparta.hashtag.entity.Hashtag;
import com.project.sparta.hashtag.repository.HashtagRepository;
import com.project.sparta.recommendCourse.repository.RecommendCourseBoardRepository;
import com.project.sparta.security.jwt.JwtUtil;
import com.project.sparta.user.dto.*;
import com.project.sparta.user.entity.User;
import com.project.sparta.user.entity.UserTag;
import com.project.sparta.user.repository.UserRepository;
import com.project.sparta.user.repository.UserTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.project.sparta.exception.api.Status.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final HashtagRepository hashtagRepository;
    private final UserTagRepository userTagRepository;
    private final BoardRepository boardRepository;
    private final RecommendCourseBoardRepository recommandCoursePostRepository;
    private final JwtUtil jwtUtil;

    //회원가입
    @Override
    public void signup(UserSignupDto signupDto) {
        // 1. User를 생성해서 repository에 저장한다.
        String encodedPassword = passwordEncoder.encode(signupDto.getPassword());
        User user1 = new User(signupDto.getEmail(), encodedPassword, signupDto.getNickName(), signupDto.getAge(), signupDto.getPhoneNumber(), signupDto.getImageUrl());
        User saveUser = userRepository.save(user1);
        System.out.println(signupDto.getEmail());
        System.out.println(signupDto.getNickName());
        System.out.println(signupDto.getPassword());
        System.out.println(signupDto.getPhoneNumber());
        System.out.println(signupDto.getAge());

        // 2. 선택한 hashtag를 각각 Usertag로 테이블에 저장한다.
        List<Long> longList = signupDto.getTagList();
        List<UserTag> userTagList = new ArrayList<>();

        if(longList!=null)
        {
            for (Long along : longList) {
                Hashtag hashtag = hashtagRepository.findById(along).orElseThrow(() -> new CustomException(NOT_FOUND_HASHTAG));
                UserTag userTag = new UserTag(saveUser, hashtag);
                userTagRepository.save(userTag);
                userTagList.add(userTag);
            }
        }

        
        // 3. User에 List<UserTag>를 넣어준다.
        saveUser.updateUserTags(userTagList);

    }
    //로그인
    @Override
    public LoginResponseDto login(LoginRequestDto requestDto, HttpServletResponse response) {
        User user = userRepository.findByEmail(requestDto.getEmail()).orElseThrow(() -> new CustomException(NOT_FOUND_USER));
        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new CustomException(NOT_MATCH_PASSWORD);
        }
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getEmail(), user.getRole()));
        System.out.println(user.getEmail());
        System.out.println(user.getNickName());
        System.out.println(user.getPassword());
        System.out.println(user.getPhoneNumber());
        System.out.println(user.getAge());
        return new LoginResponseDto(user.getRole());
    }

    // 이메일 중복확인
    @Override
    public void validateEmail(ValidateEmailDto emailDto) {
        Optional<User> findUser = userRepository.findByEmail(emailDto.getEmail());
        if(findUser.isPresent()){
            throw new CustomException(CONFLICT_EMAIL);
        }
    }

    // 닉네임 중복확인
    @Override
    public void validateNickName(ValidateNickNameDto nickNameDto) {
        Optional<User> findUser = userRepository.findByNickName(nickNameDto.getNickName());
        if(findUser.isPresent()){
            throw new CustomException(CONFLICT_NICKNAME);
        }
    }

    // 내 정보 조회
    @Override
    public void getMyInfo(User user) {
        // 내가 쓴 커뮤니티 글 개수
        Long aLong = boardRepository.countByUserId(user.getId());
        System.out.println("aLong = " + aLong);

        // 내가 쓴 코스추천 글 개수
        // Long aLong1 = recommandCoursePostRepository.co(user.getId());
        // System.out.println("aLong1 = " + aLong1);

        // 내가 참여한 크루 수
        System.out.println("user.getEnterCount() = " + user.getEnterCount());
        //
        // 내가 만든 크루 수
        System.out.println("user.getMakeCount() = " + user.getMakeCount());
        //
        // 내 해시태그 리스트
        System.out.println("user.getTags() = " + user.getTags());
    }





}
