package com.project.sparta.recommendCourse.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecommendCourseThumbnail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;

    //게시글 외래키 연결
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recommandCourse_post_id")
    private RecommendCourseBoard recommendCourseBoard;

    @Builder
    public RecommendCourseThumbnail(String url,
        RecommendCourseBoard recommendCourseBoard) {
        this.url = url;
        this.recommendCourseBoard = recommendCourseBoard;
    }
}
