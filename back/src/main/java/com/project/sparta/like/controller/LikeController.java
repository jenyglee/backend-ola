package com.project.sparta.like.controller;


import com.project.sparta.like.service.*;
import com.project.sparta.security.UserDetailsImpl;
import com.project.sparta.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class LikeController {

    private final BoardLikeService boardLikeService;
    private final CommentLikeService commentLikeService;
//    private final RecommendCourseLikeService recommendCourseLikeService;

    //보드 라이크
    @PostMapping("/board_like")
    @ResponseStatus(HttpStatus.OK)
    public void likeBoard(@RequestParam Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        User user = userDetails.getUser();
        boardLikeService.likeBoard(id,user);
    }
    @DeleteMapping("/board_like")
    public void unLikeBoard(@RequestParam Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        User user = userDetails.getUser();
        boardLikeService.unLikeBoard(id,user);
    }

    //댓글 라이크
    @PostMapping("/comment_like")
    @ResponseStatus(HttpStatus.OK)
    public void likeComment(@RequestParam Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        User user = userDetails.getUser();
        commentLikeService.likeComment(id,user);
    }
    @DeleteMapping("/comment_like")
    public void unLikeComment(@RequestParam Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        User user = userDetails.getUser();
        commentLikeService.unLikeComment(id,user);
    }

    //코스추천 라이크
//    @PostMapping("/recommend_like")
//    @ResponseStatus(HttpStatus.OK)
//    public void likeRecommendCourse(@RequestParam Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
//        User user = userDetails.getUser();
//        recommendCourseLikeService.likeRecommendCourse(id,user);
//    }
//    @DeleteMapping("/recommend_like")
//    public void unlikeRecommendCourse(@RequestParam Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
//        User user = userDetails.getUser();
//        recommendCourseLikeService.unLikeRecommendCourse(id,user);
//    }
}
