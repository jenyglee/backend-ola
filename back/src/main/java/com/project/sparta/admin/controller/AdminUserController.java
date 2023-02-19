package com.project.sparta.admin.controller;

import static com.project.sparta.exception.api.Status.NOT_FOUND_USER;

import com.project.sparta.admin.dto.UserGradeDto;
import com.project.sparta.admin.dto.UserStatusDto;
import com.project.sparta.admin.entity.StatusEnum;
import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.exception.CustomException;
import com.project.sparta.noticeBoard.service.NoticeBoardService;
import com.project.sparta.security.UserDetailsImpl;
import com.project.sparta.user.dto.UserListResponseDto;
import com.project.sparta.user.dto.UserOneResponseDto;
import com.project.sparta.user.entity.User;
import com.project.sparta.user.entity.UserGradeEnum;
import com.project.sparta.user.repository.UserRepository;
import com.project.sparta.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hibernate.mapping.Join;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Api(tags = {"어드민 유저 API"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminUserController {
    private final UserService userService;

    // TODO 어드민 User API 제작
    @ApiOperation(value = "유저 리스트 조회",response = Join.class)
    @GetMapping("/users")
    public ResponseEntity getUserList(int page, int size){
        PageResponseDto<List<UserListResponseDto>> userList = userService.getUserList(page, size);
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }
    @ApiOperation(value = "유저 조회",response = Join.class)
    @GetMapping("/users/{userId}")
    public ResponseEntity getUser(@PathVariable Long userId){
        UserOneResponseDto user = userService.getUser(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    @ApiOperation(value = "유저 등급 변경",response = Join.class)
    @PatchMapping("/users/{userId}/grade")
    public ResponseEntity changeGradeUser(@PathVariable Long userId, @RequestBody UserGradeDto gradeDto){
        userService.changeGrade(gradeDto, userId);
        return new ResponseEntity(HttpStatus.OK);
    }
    @ApiOperation(value = "유저 활성 변경",response = Join.class)
    @PatchMapping("/users/{userId}/status")
    public ResponseEntity changeEnableUser(@PathVariable Long userId, @RequestBody UserStatusDto statusDto){
        userService.changeStatus(statusDto, userId);
        return new ResponseEntity(HttpStatus.OK);
    }
}
