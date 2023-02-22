package com.project.sparta.recommendCourse.entity;

import com.project.sparta.common.entity.Timestamped;
import com.project.sparta.recommendCourse.dto.RecommendRequestDto;
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



    //    @OneToMany
    //    private List<RecommendCourseImg> images = new ArrayList<>();


    // @ManyToOne
    // @JoinColumn(name = "user_id")
    // private User user;


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



    public void modifyRecommendCourseBoard(RecommendRequestDto requestDto, Long userId,
        List<RecommendCourseImg> images
    ) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
        this.season = requestDto.getSeason();
        this.score = requestDto.getScore();
        this.altitude = requestDto.getAltitude();
        this.postStatus = PostStatusEnum.VAILABLE;
        this.userId = userId;
        // this.images = images;
    }

    public void statusModifyRecommendCourse(PostStatusEnum postStatus) {
        this.postStatus = postStatus;
    }
}
