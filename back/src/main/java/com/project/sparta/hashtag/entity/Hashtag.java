package com.project.sparta.hashtag.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.sparta.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Hashtag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    private String name;
    public Hashtag(String name) {
        this.name = name;
    }
}
