package com.project.sparta.like.controller;


import com.project.sparta.like.service.*;
import com.project.sparta.security.UserDetailsImpl;
import com.project.sparta.user.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.hibernate.mapping.Join;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = {"좋아요"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/like")
public class LikeController {
    private final BoardLikeService boardLikeService;
    private final CommentLikeService commentLikeService;
    private final RecommendCourseLikeService recommendCourseLikeService;

    //커뮤니티 좋아요
    @ApiOperation(value = "커뮤니티 좋아요",response = Join.class)
    @PostMapping("/communities/{boardId}")
    @ResponseStatus(HttpStatus.OK)
    @ApiImplicitParams({
        @ApiImplicitParam(name = "boardId", value = "커뮤니티 ID", required = true, dataType = "Long", paramType = "path", example = "1"),
    })
    public void likeBoard(@PathVariable Long boardId, @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails){
        User user = userDetails.getUser();
        boardLikeService.likeBoard(boardId, user);
    }

    //커뮤니티 좋아요 해제
    @ApiOperation(value = "커뮤니티 좋아요 해제",response = Join.class)
    @DeleteMapping("/communities/{boardId}")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "boardId", value = "커뮤니티 ID", required = true, dataType = "Long", paramType = "path", example = "1"),
    })
    public void unLikeBoard(@PathVariable Long boardId, @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails){
        User user = userDetails.getUser();
        boardLikeService.unLikeBoard(boardId, user);
    }

    //댓글 좋아요
    @ApiOperation(value = "커뮤니티 댓글 좋아요",response = Join.class)
    @PostMapping("/comments/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    @ApiImplicitParams({
        @ApiImplicitParam(name = "commentId", value = "댓글 ID", required = true, dataType = "Long", paramType = "path", example = "1"),
    })
    public void likeComment(@PathVariable Long commentId, @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails){
        User user = userDetails.getUser();
        commentLikeService.likeComment(commentId, user);
    }

    //댓글 좋아요 해제
    @ApiOperation(value = "커뮤니티 댓글 좋아요 해제",response = Join.class)
    @DeleteMapping("/comments/{commentId}")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "commentId", value = "댓글 ID", required = true, dataType = "Long", paramType = "path", example = "1"),
    })
    public void unLikeComment(@PathVariable Long commentId, @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails){
        User user = userDetails.getUser();
        commentLikeService.unLikeComment(commentId, user);
    }

    //코스추천 좋아요
    @ApiOperation(value = "코스추천 좋아요",response = Join.class)
    @PostMapping("/recommends/{recommendId}")
    @ResponseStatus(HttpStatus.OK)
    @ApiImplicitParams({
        @ApiImplicitParam(name = "recommendId", value = "코스추천 ID", required = true, dataType = "Long", paramType = "path", example = "1"),
    })
    public void likeRecommendCourse(@PathVariable Long recommendId, @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails){
        User user = userDetails.getUser();
        recommendCourseLikeService.likeRecommendCourse(recommendId, user);
    }

    //코스추천 좋아요 해제
    @ApiOperation(value = "코스추천 좋아요 해제",response = Join.class)
    @DeleteMapping("/recommends/{recommendId}")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "recommendId", value = "코스추천 ID", required = true, dataType = "Long", paramType = "path", example = "1"),
    })
    public void unlikeRecommendCourse(@PathVariable Long recommendId, @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails){
        User user = userDetails.getUser();
        recommendCourseLikeService.unLikeRecommendCourse(recommendId, user);
    }
}
