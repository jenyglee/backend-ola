package com.project.sparta.admin.controller;


import com.project.sparta.admin.dto.AdminSignupDto;
import com.project.sparta.communityBoard.dto.CommunityBoardRequestDto;
import com.project.sparta.communityBoard.dto.CommunityBoardResponseDto;
import com.project.sparta.communityBoard.service.CommunityBoardService;
import com.project.sparta.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RestController;
import com.project.sparta.admin.service.AdminServiceImpl;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AdminController {
    private final AdminServiceImpl adminServiceImpl;

    private final CommunityBoardService communityBoardService;
    // 어드민 회원가입
    @PostMapping("/signup/admin")
    public ResponseEntity signup(@RequestBody AdminSignupDto signupDto){
        adminServiceImpl.signup(signupDto);
        return new ResponseEntity(HttpStatus.OK);
    }
    @PatchMapping("/admin_community_boards/{community_board_id}")
    public ResponseEntity updateCommunityBoard(@PathVariable Long community_board_id, @RequestBody CommunityBoardRequestDto communityBoardRequestDto
        ,@AuthenticationPrincipal UserDetailsImpl userDetail) {
        CommunityBoardResponseDto communityBoardResponseDto = communityBoardService.updateCommunityBoard(
            community_board_id, communityBoardRequestDto, userDetail.getUser());
        return new ResponseEntity<>(communityBoardResponseDto, HttpStatus.OK);
    }


}
