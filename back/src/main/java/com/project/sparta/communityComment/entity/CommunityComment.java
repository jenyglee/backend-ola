package com.project.sparta.communityComment.entity;

import com.project.sparta.common.entity.Timestamped;
import com.project.sparta.communityComment.dto.CommunityRequestDto;
import com.project.sparta.user.entity.User;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommunityComment extends Timestamped {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "communityCommentId")
  private Long Id;
  private Long communityBoardId;
  private String nickName;
  private String contents;
//  private Long likeCount;



  //one to many 연관관계
//  Board board;
  public CommunityComment(Long boardId, CommunityRequestDto communityRequestDto,
     User user) {
    this.communityBoardId = boardId;
    this.nickName = user.getNickName();
    this.contents = communityRequestDto.getContents();
  }

  public void updateComment(String contents) {
    this.contents = contents;
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
