package com.project.sparta.communityComment.entity;

import com.project.sparta.communityComment.dto.CommunityRequestDto;
import com.project.sparta.user.entity.User;
import java.time.LocalDateTime;
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
public class CommunityComment {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "")
  private Long communityCoId;
  private Long communityId;
  private String userName;
  private String contents;

//one to many 연관관계
//  Board board;
  public CommunityComment(Long boardId, CommunityRequestDto communityRequestDto) {
    this.communityId = boardId;
    this.userName = communityRequestDto.getUserName();
    this.contents = communityRequestDto.getContents();
  }

//  public Board getBoard(Long boardId)
//  {
//    Board board = boardRepository.findById(boardId);
//    return board;
//  }

//  public User getUser()
//  {
//    User user = userRepository.findByUsername(userDetails.getUsername()).get();
//    return user;
//  }

}
