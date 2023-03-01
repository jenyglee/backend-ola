package com.project.sparta.recommendCourse.entity;

import com.project.sparta.common.entity.Timestamped;
import com.project.sparta.recommendCourse.dto.RecommendRequestDto;
import com.project.sparta.user.entity.User;
import java.time.LocalDateTime;
import java.util.ArrayList;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecommendCourseBoard extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recommandCourse_post_id")
    private Long id;
    @Column(nullable = false)
    private int score;          //별점
    @Column(nullable = false)
    private String title;       //제목
    @Column(nullable = false)
    private String season;      //계절
    @Column(nullable = false)
    private int altitude;       //고도
    @Column(nullable = false)
    private String contents;    //내용
    @Column(nullable = false)
    private String region;       //지역
    @Column
    private String orderByLike;    //좋아요순
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private PostStatusEnum postStatus;
    @Column(nullable = false)
    private Long userId;

    @Builder
    public RecommendCourseBoard(int score, String title, String season, int altitude,
        String contents, String region, String orderByLike, PostStatusEnum postStatus, Long userId) {
        this.score = score;
        this.title = title;
        this.season = season;
        this.altitude = altitude;
        this.contents = contents;
        this.region = region;
        this.orderByLike = orderByLike;
        this.postStatus = postStatus;
        this.userId = userId;
    }

    //TODO User에 isUserId() 사용해서 체크하게
    //public void validateOwner(Long userId){
    //    user.isUserId(userId)
    //}

    @Builder
    public RecommendCourseBoard(long id, int score, String title, String season, int altitude,
        String contents, String region, String orderByLike, PostStatusEnum postStatus, Long userId) {
        this.id = id;
        this.score = score;
        this.title = title;
        this.season = season;
        this.altitude = altitude;
        this.contents = contents;
        this.region = region;
        this.orderByLike = orderByLike;
        this.postStatus = postStatus;
        this.userId = userId;
    }

    public void update(Long id, int score, String title, String season, int altitude,
        String contents, String region, Long userId
    ) {
        this.id = id;
        this.score = score;
        this.title = title;
        this.season = season;
        this.altitude = altitude;
        this.contents = contents;
        this.region = region;
        this.postStatus = PostStatusEnum.VAILABLE;
        this.userId = userId;
    }

    public void statusModifyRecommendCourse(PostStatusEnum postStatus) {
        this.postStatus = postStatus;
    }

    public void isOwner(Long userId) {
    }

    public void validateOwner(Long userId) {
    }
}
