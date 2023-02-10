package com.project.sparta.admin.controller;

import com.project.sparta.admin.dto.AdminSignupDto;
import com.project.sparta.admin.service.AdminService;
import com.project.sparta.offerCourse.dto.RequestOfferCoursePostDto;
import com.project.sparta.offerCourse.service.OfferCoursePostService;
import com.project.sparta.user.dto.UserSignupDto;
import com.project.sparta.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AdminController {
    private final AdminService adminService;

    // @PostMapping("/signup/admin")
    // public ResponseEntity signup(@RequestBody AdminSignupDto signupDto){
    //     adminService.signup()
    // }
}
