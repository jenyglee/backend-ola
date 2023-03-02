package com.project.sparta.admin.service.impl;

import com.project.sparta.admin.dto.AdminSignupDto;
import com.project.sparta.admin.service.AdminService;
import com.project.sparta.communityBoard.repository.BoardRepository;
import com.project.sparta.exception.CustomException;
import com.project.sparta.exception.api.Status;
import com.project.sparta.like.repository.LikeBoardRepository;
import com.project.sparta.recommendCourse.repository.RecommendCourseBoardRepository;
import com.project.sparta.recommendCourse.service.RecommendCourseImgService;
import com.project.sparta.user.entity.User;
import com.project.sparta.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminServiceImpl implements AdminService {
    // ADMIN_TOKEN
    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";
    private final UserRepository userRepository;
    //todo no usages 안쓰는 필드값들 지우기 (일단 주석처리 해놓음)
//    private final BoardRepository boardRepository;
//    private final RecommendCourseBoardRepository recommandCoursePostRepository;
//    private final RecommendCourseImgService recommendCourseImgService;

    // 어드민 회원가입
    private final PasswordEncoder encoder;
//    private final LikeBoardRepository likeBoardRepository;

    // 어드민 회원가입
    @Override
    public void signup(AdminSignupDto adminRequestSignupDto) {
        if(!adminRequestSignupDto.getAdminToken().equals(ADMIN_TOKEN)){
            throw new CustomException(Status.INVALID_ADMIN_TOKEN);
        }

        User admin = User.adminBuilder()
            .email(adminRequestSignupDto.getEmail())
            .password(encoder.encode(adminRequestSignupDto.getPassword()))
            .nickName(adminRequestSignupDto.getNickName())
            .build();
        userRepository.save(admin);
    }

    //todo 주석처리 해놓은것들 안쓰는거면 지우기
//
//    @Override
//    @Transactional
//    public void deleteCommunityBoard(Long community_board_id) {
//        boardRepository.findById(community_board_id)
//            .orElseThrow(() -> new CustomException(Status.NOT_FOUND_COMMUNITY_BOARD));
//        boardRepository.deleteById(community_board_id);
//    }
//
//    @Override
//    public ManagerPersonResponseDto getOneUser(Long id){
//        User user = userRepository.findById(id).orElseThrow(()-> new CustomException(Status.INVALID_USER));
//        ManagerPersonResponseDto managerPersonResponseDto = new ManagerPersonResponseDto(user);
//        return managerPersonResponseDto;
//    }

}

