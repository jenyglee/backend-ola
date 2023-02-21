package com.project.sparta.admin.controller;

import com.project.sparta.admin.dto.AdminSignupDto;
import com.project.sparta.admin.service.AdminService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
@Api(tags = {"어드민 API"})
@RestController
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    // 어드민 회원가입
    @PostMapping("/auth/signup/admin")
    public ResponseEntity signup(@RequestBody AdminSignupDto signupDto){
        adminService.signup(signupDto);
        return new ResponseEntity(HttpStatus.OK);
    }
}
