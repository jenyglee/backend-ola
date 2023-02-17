package com.project.sparta.admin.controller;

import com.project.sparta.admin.dto.AdminRequestSignupDto;
import com.project.sparta.admin.service.AdminService;
import com.project.sparta.communityBoard.dto.CommunityBoardRequestDto;
import com.project.sparta.communityBoard.dto.CommunityBoardResponseDto;
import com.project.sparta.communityBoard.service.CommunityBoardService;
import com.project.sparta.security.UserDetailsImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import com.project.sparta.admin.dto.ManagerPersonResponseDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AdminController {
    private final AdminService adminService;
    private final CommunityBoardService communityBoardService;

    @GetMapping("/get/one/person")
    public ResponseEntity getOnePerson(@PathVariable Long id) {
        ManagerPersonResponseDto managerPersonResponseDto = adminService.getOneUser(id);
        return new ResponseEntity<>(managerPersonResponseDto, HttpStatus.OK);
    }


    // 어드민 회원가입
    @PostMapping("/signup/admin")
    public ResponseEntity signup(@RequestBody AdminRequestSignupDto signupDto){
        adminService.signup(signupDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/community_boards/{community_board_id}")
    public ResponseEntity getCommunityBoard(@PathVariable Long community_board_id) {
        CommunityBoardResponseDto communityBoardResponseDto = communityBoardService.getCommunityBoard(community_board_id);
        return new ResponseEntity<>(communityBoardResponseDto, HttpStatus.OK);
    }

}
