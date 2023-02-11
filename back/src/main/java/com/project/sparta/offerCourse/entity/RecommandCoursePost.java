package com.project.sparta.offerCourse.entity;

import com.project.sparta.admin.entity.Timestamped;
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
public class RecommandCoursePost extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recommandCourse_post_id")
    private Long Id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    @Enumerated(value=EnumType.STRING)
    private PostStatusEnum postStatus;

    @OneToMany(mappedBy = "recommandCoursePost",cascade = CascadeType.PERSIST)
    private List<RecommandCourseImg> images = new ArrayList<>();

    @Builder
    public RecommandCoursePost(String title, String contents , PostStatusEnum postStatus) {
        this.title = title;
        this.contents = contents;
        this.postStatus = postStatus;
    }

    public void modifyOfferCousePost(String title,String contents) {
        this.title = title;
        this.contents = contents;
    }

    public void statusModifyOfferCousePost(PostStatusEnum postStatus){
        this.postStatus = postStatus;
    }
}
