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
  CommunityBoardResponseDto updateCommunityBoard(Long community_board_id, CommunityBoardRequestDto communityBoardRequestDto, User user);
  void deleteCommunityBoard(Long community_board_id);
  CommunityBoardResponseDto getCommunityBoard(Long communityBoardId);
  List<CommunityBoardResponseDto> getAllCommunityBoard(int page, int size);
  List<String> modifyRecommendCoursePost(Long id, List<MultipartFile> images, RecommendRequestDto requestPostDto) throws IOException;
  //코스 삭제
  void deleteRecommendCoursePost(Long id);
  //단건 코스 조회
  RecommendDetailResponseDto oneSelectRecommendCoursePost(Long id);
  //전체 코스 조회
  PageResponseDto<List<RecommendResponseDto>> allRecommendCoursePost(int offset, int limit);


//  AdminResponseDto getUpdateUser(User user);
//  AdminResponseDto getDeleteUser(User user);
  ManagerPersonResponseDto getOneUser(Long userId);
//  List<AdminResponseDto> getAllUser();


    //  @Override
    //  public AdminResponseDto getUpdateUser(User user)
    //  {
    //
    //  }
    //  @Override
    //  public AdminResponseDto getDeleteUser(User user){
    //
    //  }
    // AdminResponseDto getOneUser(Long id);
}
