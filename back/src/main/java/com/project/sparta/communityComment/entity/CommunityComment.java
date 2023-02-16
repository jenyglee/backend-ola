package com.project.sparta.communityComment.entity;

import com.project.sparta.admin.entity.Timestamped;
import com.project.sparta.communityBoard.entity.CommunityBoard;
import com.project.sparta.communityComment.dto.CommunityRequestDto;
import com.project.sparta.security.UserDetailsImpl;
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
  private Long communityBoardId; //게시글.. 연관관계가 원래 없는건가..? 양방향 연관관계 달아줘야 하지 않나..? 보니까 컨트롤러 다 보이던데.. 그럼 게시글에서 좋아요 찾아서 보내줘야 할텐데..
  private String nickName; // 엔티티 건드는거는 물어보고 바꾸자..!
  private String contents;



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
