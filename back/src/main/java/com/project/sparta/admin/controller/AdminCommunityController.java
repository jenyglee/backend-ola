package com.project.sparta.admin.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Api(tags = {"어드민 커뮤니티 API"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminCommunityController {
    // TODO 어드민 Community API 제작
    @GetMapping("/boards/communities")
    public void getCommunityList(){

    }

    @GetMapping("/boards/communities/{boardId}")
    public void getCommunity(){

    }

    @PatchMapping("/boards/communities/{boardId}")
    public void updateCommunity(){

    }

    @DeleteMapping("/boards/communities/{boardId}")
    public void deleteCommunity(){

    }
}
