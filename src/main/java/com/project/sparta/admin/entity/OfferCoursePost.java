package com.project.sparta.admin.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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


    @OneToMany(mappedBy = "offerCoursePost",cascade = CascadeType.PERSIST)
    private List<OfferCourseImg> images = new ArrayList<>();

    @Builder
    public OfferCoursePost(String title, String contents, List<OfferCourseImg> images) {
        this.title = title;
        this.contents = contents;
        this.images = images;
    }
}
