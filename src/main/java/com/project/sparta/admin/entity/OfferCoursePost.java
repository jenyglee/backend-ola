package com.project.sparta.admin.entity;

import com.project.sparta.user.entity.Timestamped;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
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

    @OneToMany( mappedBy = "offerCoursePost",cascade = CascadeType.ALL)
    private List<OfferCourseImg> images = new ArrayList<>();

}
