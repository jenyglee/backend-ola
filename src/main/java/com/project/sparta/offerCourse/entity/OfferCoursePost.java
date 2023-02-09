package com.project.sparta.offerCourse.entity;

import com.project.sparta.admin.entity.Timestamped;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OfferCoursePost extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "offercourse_post_id")
    private Long Id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @Builder
    public OfferCoursePost(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }



    public void modifyOfferCousePost(String title,String contents) {
        this.title = title;
        this.contents = contents;
    }
}
