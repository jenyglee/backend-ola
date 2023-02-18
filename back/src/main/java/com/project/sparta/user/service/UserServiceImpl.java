package com.project.sparta.user.service;

import com.project.sparta.communityBoard.repository.BoardRepository;
import com.project.sparta.exception.CustomException;
import com.project.sparta.hashtag.entity.Hashtag;
import com.project.sparta.hashtag.repository.HashtagRepository;
import com.project.sparta.recommendCourse.repository.RecommendCourseBoardRepository;
import com.project.sparta.security.dto.RegenerateTokenDto;
import com.project.sparta.security.dto.TokenDto;
import com.project.sparta.security.jwt.JwtUtil;
import com.project.sparta.user.dto.*;
import com.project.sparta.user.entity.User;
import com.project.sparta.user.entity.UserGradeEnum;
import com.project.sparta.user.entity.UserTag;
import com.project.sparta.user.repository.UserRepository;
import com.project.sparta.user.repository.UserTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static com.project.sparta.exception.api.Status.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final HashtagRepository hashtagRepository;
    private final UserTagRepository userTagRepository;
    private final BoardRepository boardRepository;
    private final RedisTemplate<String, String> redisTemplate;
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
    public ResponseEntity<TokenDto> login(LoginRequestDto requestDto) {

        User user = userRepository.findByEmail(requestDto.getEmail()).orElseThrow(() -> new CustomException(NOT_FOUND_USER));
        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new CustomException(NOT_MATCH_PASSWORD);
        }

        String refresh_token = jwtUtil.generateRefreshToken(user.getEmail(), user.getRole());

        TokenDto tokenDto = new TokenDto(
                jwtUtil.generateAccessToken(user.getEmail(), user.getRole()),
                refresh_token,
                user.getRole()
        );

        redisTemplate.opsForValue().set(
                user.getEmail(),
                refresh_token,
                JwtUtil.REFRESH_TOKEN_TIME,
                TimeUnit.MILLISECONDS
        );

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtUtil.AUTHORIZATION_HEADER, tokenDto.getAccessToken());
        return new ResponseEntity<>(tokenDto, httpHeaders, HttpStatus.OK);
    }


    //로그아웃
    @Transactional
    public void logout(TokenDto tokenRequestDto){

        String resultToekn = tokenRequestDto.getAccessToken();

        if(!jwtUtil.validateToken(resultToekn)){
            throw new CustomException(INVALID_TOKEN);
        }
        Authentication authentication = jwtUtil.getAuthenticationByAccessToken(resultToekn);

        if(redisTemplate.opsForValue().get(authentication.getName())!=null){
            redisTemplate.delete(authentication.getName());
        }

        Long expiration = jwtUtil.getExpiration(resultToekn);

        redisTemplate.opsForValue().set(tokenRequestDto.getAccessToken(), "logout", expiration, TimeUnit.MILLISECONDS);
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

    @Override
    public ResponseEntity<TokenDto> regenerateToken(RegenerateTokenDto tokenDto) {

        String changeToken = tokenDto.getRefresh_token();

        try {
            if (!jwtUtil.validateRefreshToken(changeToken)) {
                throw new CustomException(INVALID_TOKEN);
            }

            Authentication authentication = jwtUtil.getAuthenticationByRefreshToken(changeToken);

            String refreshToken = redisTemplate.opsForValue().get(authentication.getName());

            if (!refreshToken.equals(changeToken)) {
                throw new CustomException(DISCORD_TOKEN);
            }

            User user = userRepository.findByEmail(authentication.getName()).orElseThrow(() -> new CustomException(NOT_FOUND_USER));

            String new_refresh_token = jwtUtil.generateRefreshToken(user.getEmail(), user.getRole());
            TokenDto new_tokenDto = new TokenDto(
                    jwtUtil.generateAccessToken(user.getEmail(), user.getRole()),
                    new_refresh_token,
                    user.getRole()
            );

            redisTemplate.opsForValue().set(
                    authentication.getName(),
                    new_refresh_token,
                    JwtUtil.REFRESH_TOKEN_TIME,
                    TimeUnit.MILLISECONDS
            );

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add(JwtUtil.AUTHORIZATION_HEADER, new_tokenDto.getAccessToken());
            return new ResponseEntity<>(new_tokenDto, httpHeaders, HttpStatus.OK);
        } catch (AuthenticationException e) {
            throw new CustomException(DISCORD_TOKEN);
        }
    }

    //자동 등업
    @Override
    public void upgrade(UpgradeRequestDto requestDto, Long userId){
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new CustomException(NOT_FOUND_USER));
        if(requestDto.getGrade().equals("MANIA")){
            user.changeGrade(UserGradeEnum.MOUNTAIN_MANIA);
        }else if(requestDto.getGrade().equals("GOD")){
            user.changeGrade(UserGradeEnum.MOUNTAIN_GOD);
        }
    }
}
