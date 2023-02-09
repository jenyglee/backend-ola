package com.project.sparta.communityComment.entity;

import com.project.sparta.admin.entity.Timestamped;
import com.project.sparta.communityBoard.entity.CommunityBoard;
import com.project.sparta.communityComment.dto.CommunityRequestDto;
import com.project.sparta.security.UserDetailImpl;
import com.project.sparta.user.entity.User;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class CommunityComment extends Timestamped {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "communityCommentId")
  private Long Id;
  private Long communityBoardId;
  private String nickName;
  private String contents;

  @ManyToOne
  @JoinColumn(name = "communityBoardId_Id", nullable = false)
  private CommunityBoard communityBoard;

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
