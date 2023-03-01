package com.project.sparta.like.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class CountLike {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column
  private Long likeCnt;
  @Column
  private Long commnetId;

  public CountLike(Long likeCnt, Long commnetId) {
    this.likeCnt = likeCnt;
    this.commnetId = commnetId;
  }

  public void add(Long cnt){
    this.likeCnt =cnt;
    this.likeCnt +=1;
  }
  public void sub(Long cnt){
    this.likeCnt =cnt;
    this.likeCnt -=1;
  }
}
