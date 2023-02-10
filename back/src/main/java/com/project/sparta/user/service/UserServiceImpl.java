package com.project.sparta.user.service;

import com.project.sparta.admin.entity.StatusEnum;
import com.project.sparta.hashtag.entity.Hashtag;
import com.project.sparta.hashtag.repository.HashtagRepository;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.project.sparta.admin.entity.StatusEnum.USER_REGISTERED;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final HashtagRepository hashtagRepository;

    @Override
    public void signup(UserSignupDto signupDto) {
        List<UserTag> tags = new ArrayList<>();
        Hashtag hashtag = hashtagRepository.findById(signupDto.getTagList().get(0)).get(); // 주변맛집
        User user = signupDto.toEntity(passwordEncoder.encode(signupDto.getPassword()), tags);
        
        // userRepository.save(user);
        // String email, String password, String nickName, int age, String phoneNumber, String userImageUrl, List< UserTag > tags
        // userRepository.save()
    }

    @Override
    public UserRoleEnum login(UserLoginDto userLoginDto) {
        User user = userRepository.findByNickNameAndStatus(userLoginDto.getNickName(), USER_REGISTERED).orElseThrow(()-> new IllegalArgumentException("회원이 존재하지 않습니다.")); //나중에 exception 처리 다시 해야함

        if(!passwordEncoder.matches(userLoginDto.getPassword(), user.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다."); //나중에 exception 처리 다시 해야함
        }
        return user.getRole();
    }


}
