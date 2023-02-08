package com.project.sparta.admin.entity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OfferCourseImg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "offercourse_img_id" )
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "offercourse_post_id")
    private OfferCoursePost offerCoursePost;


    @Column(nullable = false)
    private String imgRoute;
    

    public OfferCourseImg(String imgRoute) {

        this.imgRoute = imgRoute;
    }
}
