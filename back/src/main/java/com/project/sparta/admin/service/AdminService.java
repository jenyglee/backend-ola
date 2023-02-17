package com.project.sparta.admin.service;

import com.project.sparta.admin.dto.AdminRequestSignupDto;
import com.project.sparta.admin.dto.ManagerPersonResponseDto;
import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.communityBoard.dto.CommunityBoardRequestDto;
import com.project.sparta.communityBoard.dto.CommunityBoardResponseDto;
import com.project.sparta.recommendCourse.dto.RecommendRequestDto;
import com.project.sparta.recommendCourse.dto.RecommendResponseDto;
import com.project.sparta.recommendCourse.dto.RecommendDetailResponseDto;
import com.project.sparta.user.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AdminService {
  void signup(AdminRequestSignupDto signupDto);

  void deleteCommunityBoard(Long community_board_id);

  ManagerPersonResponseDto getOneUser(Long userId);


}
