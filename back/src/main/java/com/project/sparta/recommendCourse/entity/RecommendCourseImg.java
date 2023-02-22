package com.project.sparta.recommendCourse.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Random;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecommendCourseImg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recommandCourse_img_id")
    private Long id;

    private String url;

    //파일 저장 게시글
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recommandCourse_post_id")
    private RecommendCourseBoard recommendCourseBoard;

    public RecommendCourseImg(String url, RecommendCourseBoard recommendCourseBoard) {
        this.url = url;
        this.recommendCourseBoard = recommendCourseBoard;
    }

    public void updateRecommendCourseImg(String url, RecommendCourseBoard recommendCourseBoard){
        this.url = url;
        this.recommendCourseBoard = recommendCourseBoard;
    }
}
