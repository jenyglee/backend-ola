package com.project.sparta.admin.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Random;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OfferCourseImg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "offercourse_img_id" )
    private Long id;

    //파일 저장 게시글
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "offercourse_post_id")
    private OfferCoursePost offerCoursePost;

    //파일 원본명
    @Column(nullable = false)
    private String originalImgName;

    //이미지 파일경로
    @Column(nullable = false)
    private String imgRoute;

    //이미지 크기
    private Long imgSize;


    @Builder
    public OfferCourseImg(OfferCoursePost offerCoursePost, String originalImgName, String imgRoute, Long imgSize) {
        this.offerCoursePost = offerCoursePost;
        this.originalImgName = originalImgName;
        this.imgRoute = imgRoute;
        this.imgSize = imgSize;
    }

    public void addPost(OfferCoursePost offerCoursePost){
        //사진이 게시글에 존재하지 않는다면
        if(!offerCoursePost.getImages().contains(this)){
            //지금사진 추가
            offerCoursePost.getImages().add(this);
        }
        this.offerCoursePost = offerCoursePost;

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
