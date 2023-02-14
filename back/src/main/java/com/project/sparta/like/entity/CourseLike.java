package com.project.sparta.like.entity;

import com.project.sparta.recommendCourse.entity.RecommendCourseBoard;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class CourseLike {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likesId;


    @Column(nullable = false)
    private String userEmail;

    @Column(nullable = false)
    private String userNickName;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recommandCourse_post_id")
    private RecommendCourseBoard CourseBoard;


    @Builder
    public CourseLike(String userEmail, String userNickName, RecommendCourseBoard courseBoard) {
        this.userEmail = userEmail;
        this.userNickName = userNickName;
        this.CourseBoard = courseBoard;
    }
}
