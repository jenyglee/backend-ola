package com.project.sparta.communityBoard.entity;
import com.project.sparta.communityBoard.dto.CommunityBoardRequestDto;
import com.project.sparta.communityComment.entity.CommunityComment;
import com.project.sparta.security.UserDetailImpl;
import com.project.sparta.user.entity.User;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class CommunityBoard {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "communityBoard_Id")
  private Long Id;
  private String nickName;
  private String contents;
  private String title;
  @Column(nullable = false)
  @OneToMany(mappedBy = "communityBoardId",cascade = CascadeType.REMOVE)
  private List<CommunityComment> communityComment = new ArrayList<>();

  public CommunityBoard(CommunityBoardRequestDto communityBoardRequestDto, User user) {
    this.nickName = user.getNickName();
    this.contents = communityBoardRequestDto.getContents();
    this.title = communityBoardRequestDto.getTitle();

  }

  public void updateBoard(String contents) {
    this.contents = contents;
  }
}
