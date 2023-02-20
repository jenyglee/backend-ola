package com.project.sparta.hashtag.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.sparta.communityBoard.entity.CommunityTag;
import com.project.sparta.user.entity.User;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Hashtag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hashtag_id")
    private long id;

    private String name;

    @OneToMany(mappedBy = "hashtag", cascade = CascadeType.ALL)
    private List<CommunityTag> communityTags = new ArrayList<>();

    public Hashtag(String name) {
        this.name = name;
    }
}
