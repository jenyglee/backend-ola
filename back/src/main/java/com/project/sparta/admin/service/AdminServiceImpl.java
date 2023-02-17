package com.project.sparta.admin.service;

import com.project.sparta.admin.dto.AdminRequestSignupDto;
import com.project.sparta.admin.dto.ManagerPersonResponseDto;
import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.communityBoard.dto.CommunityBoardRequestDto;
import com.project.sparta.communityBoard.dto.CommunityBoardResponseDto;
import com.project.sparta.communityBoard.entity.CommunityBoard;
import com.project.sparta.communityBoard.repository.BoardRepository;
import com.project.sparta.exception.CustomException;
import com.project.sparta.exception.api.Status;

import com.project.sparta.like.repository.LikeBoardRepository;
import com.project.sparta.recommendCourse.dto.RecommendRequestDto;
import com.project.sparta.recommendCourse.dto.RecommendResponseDto;
import com.project.sparta.recommendCourse.dto.RecommendDetailResponseDto;
import com.project.sparta.recommendCourse.entity.PostStatusEnum;
import com.project.sparta.recommendCourse.entity.RecommendCourseBoard;
import com.project.sparta.recommendCourse.entity.RecommendCourseImg;
import com.project.sparta.recommendCourse.repository.RecommendCourseBoardRepository;
import com.project.sparta.recommendCourse.service.RecommendCourseImgService;
import com.project.sparta.user.entity.User;
import com.project.sparta.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminServiceImpl implements AdminService{
    // ADMIN_TOKEN
    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final RecommendCourseBoardRepository recommandCoursePostRepository;
    private final RecommendCourseImgService recommendCourseImgService;

    // 어드민 회원가입
// ADMIN_TOKEN
    private final PasswordEncoder encoder;
    private final LikeBoardRepository likeBoardRepository;

    // 어드민 회원가입
    public void signup(AdminRequestSignupDto adminRequestSignupDto) {
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

    @Override
    @Transactional
    public void deleteCommunityBoard(Long community_board_id) {
        boardRepository.findById(community_board_id)
            .orElseThrow(() -> new CustomException(Status.NOT_FOUND_COMMUNITY_BOARD));
        boardRepository.deleteById(community_board_id);
    }

    @Override
    public ManagerPersonResponseDto getOneUser(Long id){
        User user = userRepository.findById(id).orElseThrow(()-> new CustomException(Status.INVALID_USER));
        ManagerPersonResponseDto managerPersonResponseDto = new ManagerPersonResponseDto(user);
        return managerPersonResponseDto;
    }

}
