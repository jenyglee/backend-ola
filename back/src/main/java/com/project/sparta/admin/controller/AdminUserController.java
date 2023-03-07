package com.project.sparta.admin.controller;

import com.project.sparta.admin.dto.UserGradeDto;
import com.project.sparta.admin.dto.UserStatusDto;
import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.user.dto.UserListResponseDto;
import com.project.sparta.user.dto.UserOneResponseDto;
import com.project.sparta.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.hibernate.mapping.Join;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Api(tags = {"어드민 유저"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminUserController {
    //todo 어드민 권한만 들어올 수 있도록 설정추가(완료)
    private final UserService userService;

    // 회원 전체 조회
    @ApiOperation(value = "유저 리스트 조회",response = Join.class)
    @GetMapping("/users")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "page", value = "페이지", required = true, dataType = "int", paramType = "path", defaultValue = "0", example = "0"),
        @ApiImplicitParam(name = "size", value = "보여줄 개수", required = true, dataType = "String", paramType = "query", defaultValue = "10", example = "10"),
    })
    public ResponseEntity getUserList(int page, int size){
        PageResponseDto<List<UserListResponseDto>> userList = userService.getUserList(page, size);
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    // 회원 단건 조회
    @ApiOperation(value = "유저 조회",response = Join.class)
    @ApiImplicitParams({
        @ApiImplicitParam(name = "userId", value = "유저 ID", required = true, dataType = "Long", paramType = "path", example = "1"),
    })
    @GetMapping("/users/{userId}")
    public ResponseEntity getUser(@PathVariable Long userId){
        UserOneResponseDto user = userService.getUser(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // 회원 등급 변경
    @ApiOperation(value = "유저 등급 변경",response = Join.class)
    @ApiImplicitParams({
        @ApiImplicitParam(name = "userId", value = "유저 ID", required = true, dataType = "Long", paramType = "path", example = "1"),
    })
    @PatchMapping("/users/{userId}/grade")
    public ResponseEntity changeGradeUser(@PathVariable Long userId,
        @RequestBody @ApiParam(value = "유저등급 변경값(0 : 탈퇴, 1 : 등록)", required = true) UserGradeDto gradeDto){
        userService.changeGrade(gradeDto, userId);
        return new ResponseEntity(HttpStatus.OK);
    }

    //회원 탈퇴/복구 처리
    @ApiOperation(value = "유저 활성 변경",response = Join.class)
    @ApiImplicitParams({
        @ApiImplicitParam(name = "userId", value = "유저 ID", required = true, dataType = "Long", paramType = "path", example = "1"),
    })
    @PatchMapping("/users/{userId}/status")
    public ResponseEntity changeEnableUser(@PathVariable Long userId,
        @RequestBody @ApiParam(value = "회원 탈퇴/복구 처리값(0 : 등린이, 1 : 매니아, 2 : 산신령)", required = true) UserStatusDto statusDto){
        userService.changeStatus(statusDto, userId);
        return new ResponseEntity(HttpStatus.OK);
    }
}
