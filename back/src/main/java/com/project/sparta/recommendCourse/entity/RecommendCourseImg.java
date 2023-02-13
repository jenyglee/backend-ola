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
    @Column(name = "recommandCourse_img_id" )
    private Long id;

    //파일 저장 게시글
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recommandCourse_post_id")
    private RecommendCoursePost recommendCoursePost;

    //파일 원본명
    @Column(nullable = false)
    private String originalImgName;

    //이미지 파일경로
    @Column(nullable = false)
    private String imgRoute;

    //이미지 크기
    private Long imgSize;


    @Builder
    public RecommendCourseImg(RecommendCoursePost recommendCoursePost, String originalImgName, String imgRoute, Long imgSize) {
        this.recommendCoursePost = recommendCoursePost;
        this.originalImgName = originalImgName;
        this.imgRoute = imgRoute;
        this.imgSize = imgSize;
    }

    public void addPost(RecommendCoursePost recommendCoursePost){
        //사진이 게시글에 존재하지 않는다면
        this.recommendCoursePost = recommendCoursePost;

    }

    public String randomCode(){
        Random random = new Random();
        String stringCode = random.ints(0,30)
                .limit(7)
                .collect(StringBuilder::new,StringBuilder::appendCodePoint,StringBuilder::append)
                .toString();
        return stringCode;
    }
}
