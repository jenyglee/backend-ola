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
import com.project.sparta.user.entity.UserTag;
import com.project.sparta.user.repository.UserRepository;
import com.project.sparta.user.repository.UserTagRepository;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
    private final PasswordEncoder encoder;
    private final JavaMailSender javaMailSender;
    
    //회원가입
    @Override
    public void signup(UserSignupDto signupDto) {
        // 1. User를 생성 및 저장
        String encodedPassword = passwordEncoder.encode(signupDto.getPassword());
        User user = User.userBuilder()
            .email(signupDto.getEmail())
            .password(encodedPassword)
            .nickName(signupDto.getNickName())
            .age(signupDto.getAge())
            .phoneNumber(signupDto.getPhoneNumber())
            .userImageUrl(signupDto.getImageUrl())
            .build();
        userRepository.save(user);

        // 2. hashtag의 ID 리스트를 각각 Usertag로 변환하여 저장
        List<Long> longList = signupDto.getTagList();
        List<UserTag> userTagList = new ArrayList<>();
        if (longList != null) {
            longList.stream().forEach(tagId -> {
                Hashtag hashtag = hashtagRepository.findById(tagId)
                    .orElseThrow(() -> new CustomException(NOT_FOUND_HASHTAG));
                UserTag userTag = new UserTag(user, hashtag);
                userTagRepository.save(userTag);
                userTagList.add(userTag);
            });
        }

        // 3. User에 태그 리스트를 저장
        user.updateUserTags(userTagList);
    }

    //로그인
    @Override
    public ResponseEntity<TokenDto> login(LoginRequestDto requestDto) {

        if (requestDto.getEmail().isBlank() || requestDto.getPassword().isBlank()){
            throw new CustomException(INVALID_CONTENT);
        }

        User user = userRepository.findByEmail(requestDto.getEmail())
            .orElseThrow(() -> new CustomException(NOT_FOUND_USER));

        //에러2: 비밀번호가 틀렸을 경우
        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new CustomException(NOT_MATCH_PASSWORD);
        }

        // 1. access/refresh token 재발급
        String accessToken = jwtUtil.generateAccessToken(user.getEmail(), user.getRole(), user.getGradeEnum(),
            user.getNickName(), user.getUserImageUrl());
        String refreshToken = jwtUtil.generateRefreshToken(user.getEmail(), user.getRole());

        // 2. 응답할 DTO에 token과 nickName 추가
        TokenDto tokenDto = new TokenDto(
            accessToken,
            refreshToken,
            user.getNickName()
        );

        // 3. 레디스 저장소에 refresh token 저장
        redisTemplate.opsForValue().set(
            user.getEmail(),
            refreshToken,
            JwtUtil.REFRESH_TOKEN_TIME,
            TimeUnit.MILLISECONDS
        );

        // 4. 응답 시 헤더에 'Authorization'을 access token으로 세팅
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtUtil.AUTHORIZATION_HEADER, tokenDto.getAccessToken());
        return new ResponseEntity<>(tokenDto, httpHeaders, HttpStatus.OK);
    }


    //로그아웃
    @Transactional
    public void logout(TokenDto tokenRequestDto) {
        // 1. token을 검증
        String resultToekn = tokenRequestDto.getAccessToken();
        jwtUtil.validateToken(resultToekn);

        // 2. 레디스 저장소에 있는 token을 제거
        Authentication authentication = jwtUtil.getAuthenticationByAccessToken(resultToekn);
        if (redisTemplate.opsForValue().get(authentication.getName()) != null) {
            redisTemplate.delete(authentication.getName());
        }

        //3. 토큰을 만료
        Long expiration = jwtUtil.getExpiration(resultToekn);
        redisTemplate.opsForValue()
            .set(tokenRequestDto.getAccessToken(), "logout", expiration, TimeUnit.MILLISECONDS);
    }

    // 이메일 중복확인
    @Override
    public void validateEmail(ValidateEmailDto emailDto) {
        Optional<User> findUser = userRepository.findByEmail(emailDto.getEmail());
        if (findUser.isPresent()) {
            throw new CustomException(CONFLICT_EMAIL);
        }
    }

    // 닉네임 중복확인
    @Override
    public void validateNickName(ValidateNickNameDto nickNameDto) {
        Optional<User> findUser = userRepository.findByNickName(nickNameDto.getNickName());
        if (findUser.isPresent()) {
            throw new CustomException(CONFLICT_NICKNAME);
        }
    }

    // 내 정보 조회
    @Override
    public InfoResponseDto getMyInfo(User user) {
        // 내가 쓴 커뮤니티 글 개수
        Long communityCount = boardRepository.countMyCommunity(user.getId());

        // 내가 쓴 코스추천 글 개수
        Long recommendCount = recommendRepository.countByUserId(user.getId());

        // 내가 참여한 크루 수
        int enterCount = user.getEnterCount();

        // 내가 만든 크루 수
        int makeCount = boardRepository.countMyChat(user.getId());

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

    // 토큰 재발급
    @Override
    public ResponseEntity<TokenDto> regenerateToken(RegenerateTokenDto tokenDto) {
        String changeToken = tokenDto.getRefreshToken();
        try {
            jwtUtil.validateRefreshToken(changeToken);
            Authentication authentication = jwtUtil.getAuthenticationByRefreshToken(changeToken);

            String refreshToken = redisTemplate.opsForValue().get(authentication.getName());

            if (!refreshToken.equals(changeToken)) {
                throw new CustomException(DISCORD_TOKEN);
            }

            User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new CustomException(NOT_FOUND_USER));

            String new_refresh_token = jwtUtil.generateRefreshToken(user.getEmail(),
                user.getRole());

            TokenDto new_tokenDto = new TokenDto(
                jwtUtil.generateAccessToken(user.getEmail(), user.getRole(), user.getGradeEnum(),
                    user.getNickName(), user.getUserImageUrl()),
                new_refresh_token,
                user.getNickName()
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
    @Transactional
    public void upgrade(UpgradeRequestDto requestDto, Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new CustomException(NOT_FOUND_USER));
        if (requestDto.getGrade().equals("MANIA")) {
            user.changeGrade(UserGradeEnum.MOUNTAIN_MANIA);
        } else if (requestDto.getGrade().equals("GOD")) {
            user.changeGrade(UserGradeEnum.MOUNTAIN_GOD);
        }
    }

    //(어드민용) 회원 전체조회
    @Override
    public PageResponseDto<List<UserListResponseDto>> getUserList(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<User> results = userRepository.findAll(pageRequest);

        List<User> userList = results.getContent();
        long totalElements = results.getTotalElements();

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
        if (gradeDto.getGrade() == 0) {
            user.changeGrade(UserGradeEnum.MOUNTAIN_CHILDREN);
        } else if (gradeDto.getGrade() == 1) {
            user.changeGrade(UserGradeEnum.MOUNTAIN_MANIA);
        } else if (gradeDto.getGrade() == 2) {
            user.changeGrade(UserGradeEnum.MOUNTAIN_GOD);
        }
    }

    //(어드민용) 회원 탈퇴/복구
    @Override
    @Transactional
    public void changeStatus(UserStatusDto statusDto, Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new CustomException(NOT_FOUND_USER));
        if (statusDto.getStatus() == 0) {
            user.changeStatus(StatusEnum.USER_WITHDRAWAL);
        } else if (statusDto.getStatus() == 1) {
            user.changeStatus(StatusEnum.USER_REGISTERED);
        }
    }

    // 새로운 비밀번호를 이메일로 전송
    @Override
    public void sendMail(MailDto mailDto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailDto.getAddress()); // 받는사람 주소
        message.setFrom("jenyglee30@gmail.com"); // 보내는 사람 주소
        message.setSubject(mailDto.getTitle()); // 메일 제목
        message.setText(mailDto.getMessage()); // 메일 내용

        javaMailSender.send(message);
    }

    // 이메일과 닉네임 일치여부 확인
    @Override
    public boolean userEmailCheck(String userEmail, String userName) {
        User user = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new CustomException(NOT_FOUND_USER));
        if (user != null && user.getNickName().equals(userName)) {
            return true;
        } else {
            return false;
        }
    }

    // 이메일로 전달할 내용 폼 DTO로 반환
    @Override
    public MailDto createMailAndChangePassword(String userEmail, String userName) {
        String str = getTempPassword();
        MailDto dto = new MailDto();
        dto.setAddress(userEmail);
        dto.setTitle(userName + "님의 올라 임시비밀번호 안내 이메일 입니다.");
        dto.setMessage(
            "안녕하세요. 올라 임시비밀번호 안내 관련 이메일 입니다." + "[" + userName + "]" + "님의 임시 비밀번호는 "
                + str + " 입니다.");
        updatePassword(str, userEmail);
        return dto;
    }

    // 임시 비밀번호로 변환
    public void updatePassword(String str, String userEmail) {
        String pw = encoder.encode(str);
        User user = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new CustomException(NOT_FOUND_USER));
        user.updateUserPassword(pw);
        userRepository.save(user);
    }

    public String getTempPassword() {
        char[] charSet = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C',
            'D', 'E', 'F',
            'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
            'X', 'Y', 'Z'};

        String str = "";

        int idx = 0;
        for (int i = 0; i < 10; i++) {
            idx = (int) (charSet.length * Math.random());
            str += charSet[idx];
        }
        return str;
    }


}