package com.project.sparta.hashtag.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.sparta.user.entity.User;
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
    private long id;

    private String name;

    public Hashtag(String name) {
        this.name = name;
    }
}
