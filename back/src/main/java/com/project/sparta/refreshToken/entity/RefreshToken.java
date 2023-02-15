package com.project.sparta.refreshToken.entity;

import com.project.sparta.admin.entity.Timestamped;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "refresh_token")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long key;

    @Column(nullable = false)
    private String token;

    @Builder
    public RefreshToken(Long key, String token){
        this.key = key;
        this.token = token;
    }

    public RefreshToken updateToken(String token){
        this.token = token;
        return this;
    }

}
