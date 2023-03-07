package com.project.sparta.admin.controller;

import com.project.sparta.admin.dto.AdminSignupDto;
import com.project.sparta.admin.service.AdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.hibernate.mapping.Join;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
@Api(tags = {"어드민 API"})
@RestController
@RequiredArgsConstructor
public class AdminController {
    //todo 어드민 권한만 들어올 수 있도록 설정추가(완료)
    private final AdminService adminService;

    // 어드민 회원가입
    @ApiOperation(value = "어드민 회원가입",response = Join.class)
    @PostMapping("/auth/signup/admin")
    public ResponseEntity signup(@RequestBody
        @ApiParam(value = "어드민 회원가입 작성 값", required = true) AdminSignupDto signupDto){
        adminService.signup(signupDto);
        return new ResponseEntity(HttpStatus.OK);
    }
}
