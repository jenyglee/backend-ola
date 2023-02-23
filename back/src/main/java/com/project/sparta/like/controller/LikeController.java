package com.project.sparta.like.controller;


import com.project.sparta.like.service.*;
import com.project.sparta.security.UserDetailsImpl;
import com.project.sparta.user.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.hibernate.mapping.Join;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
@Api(tags = {"라이크 API"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/like")
public class LikeController {
    private final BoardLikeService boardLikeService;
    private final CommentLikeService commentLikeService;
    private final RecommendCourseLikeService recommendCourseLikeService;

    //커뮤니티 좋아요
    @ApiOperation(value = "보드 라이크",response = Join.class)
    @PostMapping("/communities/{boardId}")
    @ResponseStatus(HttpStatus.OK)
    public void likeBoard(@PathVariable Long boardId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        User user = userDetails.getUser();
        boardLikeService.likeBoard(boardId, user);
    }

    //커뮤니티 좋아요 해제
    @ApiOperation(value = "보드 언라이크",response = Join.class)
    @DeleteMapping("/communities/{boardId}")
    public void unLikeBoard(@PathVariable Long boardId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        User user = userDetails.getUser();
        boardLikeService.unLikeBoard(boardId, user);
    }

    //댓글 좋아요
    @PostMapping("/comments/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public void likeComment(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        User user = userDetails.getUser();
        commentLikeService.likeComment(commentId, user);
    }

    //댓글 좋아요 해제
    @DeleteMapping("/comments/{commentId}")
    public void unLikeComment(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        User user = userDetails.getUser();
        commentLikeService.unLikeComment(commentId, user);
    }

    //코스추천 좋아요
    @PostMapping("/recommends/{recommendId}")
    @ResponseStatus(HttpStatus.OK)
    public void likeRecommendCourse(@PathVariable Long recommendId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        User user = userDetails.getUser();
        recommendCourseLikeService.likeRecommendCourse(recommendId, user);
    }

    //코스추천 좋아요 해제
    @DeleteMapping("/recommends/{recommendId}")
    public void unlikeRecommendCourse(@PathVariable Long recommendId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        User user = userDetails.getUser();
        recommendCourseLikeService.unLikeRecommendCourse(recommendId, user);
    }
}
