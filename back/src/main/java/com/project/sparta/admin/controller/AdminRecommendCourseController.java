package com.project.sparta.admin.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Api(tags = {"어드민 Recommend API"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminRecommendCourseController {
    // TODO 어드민 Recommend API 제작
    @GetMapping("/boards/recommends")
    public void getRecommendList(){

    }

    @GetMapping("/boards/recommends/{boardId}")
    public void getRecommend(){

    }

    @PatchMapping("/boards/recommends/{boardId}")
    public void updateRecommend(){

    }

    @DeleteMapping("/boards/recommends/{boardId}")
    public void deleteRecommend(){

    }
}
