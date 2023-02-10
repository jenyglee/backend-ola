package com.project.sparta.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.sparta.hashtag.entity.Hashtag;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UserTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Hashtag tag;

    @Builder
    public UserTag(User user, Hashtag tag) {
        this.user = user;
        this.tag = tag;
    }

}
