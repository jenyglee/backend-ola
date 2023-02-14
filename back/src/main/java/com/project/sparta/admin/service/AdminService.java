package com.project.sparta.admin.service;

import com.project.sparta.admin.dto.ManagerPersonResponseDto;
import com.project.sparta.admin.dto.AdminRequestSignupDto;
import com.project.sparta.communityBoard.dto.CommunityBoardRequestDto;
import com.project.sparta.communityBoard.dto.CommunityBoardResponseDto;
import com.project.sparta.offerCourse.dto.RequestRecommandCoursePostDto;
import com.project.sparta.offerCourse.dto.ResponseFindAllRecommandCouesePostDto;
import com.project.sparta.offerCourse.dto.ResponseOnePostDto;
import com.project.sparta.user.entity.User;
import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface AdminService {
  void signup(AdminRequestSignupDto signupDto);
  CommunityBoardResponseDto updateCommunityBoard(Long community_board_id, CommunityBoardRequestDto communityBoardRequestDto, User user);
  void deleteCommunityBoard(Long community_board_id);
  CommunityBoardResponseDto getCommunityBoard(Long communityBoardId);
  List<CommunityBoardResponseDto> getAllCommunityBoard(int page, int size);
  List<String> modifyRecommandCoursePost(Long id, List<MultipartFile> imges, RequestRecommandCoursePostDto requestPostDto) throws IOException;
  //코스 삭제
  void deleteRecommandCoursePost(Long id);
  //단건 코스 조회
  ResponseOnePostDto oneSelectRecommandCoursePost(Long id);
  //전체 코스 조회
  PageResponseDto<List<ResponseFindAllRecommandCouesePostDto>> allRecommandCousePost(int offset, int limit);


//  AdminResponseDto getUpdateUser(User user);
//  AdminResponseDto getDeleteUser(User user);
  ManagerPersonResponseDto getOneUser(Long userId);
//  List<AdminResponseDto> getAllUser();


  //    @Override
  //    public AdminResponseDto getUpdateUser(User user)
  //    {
  //
  //    }
  //    @Override
  //    public AdminResponseDto getDeleteUser(User user){
  //
  //    }
//  AdminResponseDto getOneUser(Long id);
}
