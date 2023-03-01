package com.project.sparta.user.service;

import com.project.sparta.admin.dto.UserGradeDto;
import com.project.sparta.admin.dto.UserStatusDto;
import com.project.sparta.hashtag.dto.HashtagResponseDto;
import com.project.sparta.recommendCourse.repository.RecommendCourseBoardRepository;
import com.project.sparta.user.entity.StatusEnum;
import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.communityBoard.repository.BoardRepository;
import com.project.sparta.exception.CustomException;
import com.project.sparta.hashtag.entity.Hashtag;
import com.project.sparta.hashtag.repository.HashtagRepository;
import com.project.sparta.security.dto.RegenerateTokenDto;
import com.project.sparta.security.dto.TokenDto;
import com.project.sparta.security.jwt.JwtUtil;
import com.project.sparta.user.dto.*;
import com.project.sparta.user.entity.User;
import com.project.sparta.user.entity.UserGradeEnum;
import com.project.sparta.user.entity.UserRoleEnum;
import com.project.sparta.user.entity.UserTag;
import com.project.sparta.user.repository.UserRepository;
import com.project.sparta.user.repository.UserTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    private final RecommendCourseBoardRepository recommendRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private final JwtUtil jwtUtil;

    //회원가입
    @Override
    public void signup(UserSignupDto signupDto) {
        // 1. User를 생성해서 repository에 저장한다.
        String encodedPassword = passwordEncoder.encode(signupDto.getPassword());
        User user = User.userBuilder()
            .email(signupDto.getEmail())
            .password(encodedPassword)
            .nickName(signupDto.getNickName())
            .age(signupDto.getAge())
            .phoneNumber(signupDto.getPhoneNumber())
            .userImageUrl(signupDto.getImageUrl())
            .build();

        User saveUser = userRepository.save(user);

        System.out.println(signupDto.getEmail());
        System.out.println(signupDto.getPassword());
        System.out.println(signupDto.getNickName());
        System.out.println(signupDto.getPhoneNumber());

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
                jwtUtil.generateAccessToken(user.getEmail(), user.getRole(), user.getNickName(), user.getUserImageUrl()),
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
    public InfoResponseDto getMyInfo(User user) {
        // 내가 쓴 커뮤니티 글 개수
        Long communityCount = boardRepository.countByUserId(user.getId());

        // 내가 쓴 코스추천 글 개수
        Long recommendCount = recommendRepository.countByUserId(user.getId());

        // 내가 참여한 크루 수
        int enterCount = user.getEnterCount();

        // 내가 만든 크루 수
        int makeCount = user.getMakeCount();

        // 나의 해시태그
        List<UserTag> userTags = userTagRepository.findAllByUser(user);
        List<HashtagResponseDto> hashtagList = new ArrayList<>();
        for (UserTag tag : userTags) {
            hashtagList.add(new HashtagResponseDto(tag.getTag().getId(), tag.getTag().getName()));
        }

        return InfoResponseDto.builder()
            .nickName(user.getNickName())
            .communityCount(communityCount)
            .recommendCount(recommendCount)
            .enterCount(enterCount)
            .makeCount(makeCount)
            .tagList(hashtagList)
            .userGradeEnum(user.getGradeEnum())
            .build();
    }

    @Override
    public ResponseEntity<TokenDto> regenerateToken(RegenerateTokenDto tokenDto) {
        String changeToken = tokenDto.getRefreshToken();
        System.out.println("changeToken = " + changeToken);
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
                    jwtUtil.generateAccessToken(user.getEmail(), user.getRole(), user.getNickName(), user.getUserImageUrl()),
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

    //(어드민용) 회원 전체조회
    @Override
    public PageResponseDto<List<UserListResponseDto>> getUserList(int page, int size) {
        // 1. 페이징으로 요청해서 조회
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<User> results = userRepository.findAll(pageRequest);

        // 2. 데이터, 전체 개수 추출
        List<User> userList = results.getContent();
        long totalElements = results.getTotalElements();

        // 3. 엔티티를 DTO로 변환
        List<UserListResponseDto> userResponseDtoList = new ArrayList<>();
        for (User user : userList) {
            UserListResponseDto userResponseDto = UserListResponseDto.builder()
                .id(user.getId())
                .nickName(user.getNickName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .grade(user.getGradeEnum())
                .createdAt(user.getCreateAt())
                .status(user.getStatus())
                .build();
            userResponseDtoList.add(userResponseDto);
        }

        //4. 클라이언트에 응답(현재페이지, 전체 건수, 데이터 포함)
        return new PageResponseDto<>(page, totalElements, userResponseDtoList);
    }

    //(어드민용) 회원 단건조회
    @Override
    public UserOneResponseDto getUser(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new CustomException(NOT_FOUND_USER));
        return UserOneResponseDto.builder()
            .id(user.getId())
            .nickName(user.getNickName())
            .age(user.getAge())
            .email(user.getEmail())
            .phoneNumber(user.getPhoneNumber())
            .userGradeEnum(user.getGradeEnum())
            .createdAt(user.getCreateAt())
            .modifiedAt(user.getModifiedAt())
            .profileImage(user.getUserImageUrl())
            .status(user.getStatus())
            .build();
    }

    //(어드민용) 회원 등급 변경
    @Override
    @Transactional
    public void changeGrade(UserGradeDto gradeDto, Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new CustomException(NOT_FOUND_USER));
        if(gradeDto.getGrade() == 0){
            user.changeGrade(UserGradeEnum.MOUNTAIN_CHILDREN);
        }else if(gradeDto.getGrade() == 1){
            user.changeGrade(UserGradeEnum.MOUNTAIN_MANIA);
        }else if(gradeDto.getGrade() == 2){
            user.changeGrade(UserGradeEnum.MOUNTAIN_GOD);
        }
    }

    //(어드민용) 회원 탈퇴/복구
    @Override
    @Transactional
    public void changeStatus(UserStatusDto statusDto, Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new CustomException(NOT_FOUND_USER));
        if(statusDto.getStatus() == 0){
            user.changeStatus(StatusEnum.USER_WITHDRAWAL);
        }else if(statusDto.getStatus() == 1){
            user.changeStatus(StatusEnum.USER_REGISTERED);
        }
    }
}
