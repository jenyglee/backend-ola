package com.project.sparta.admin.dto;

import com.project.sparta.user.entity.User;
import com.project.sparta.user.entity.UserGradeEnum;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class ManagerPersonResponseDto {

  @Column(nullable = false)
  protected String nickName;

  @Column(nullable = false)
  protected String password;

  @Column(nullable = false, unique = true)
  protected String email;
  // @Column(nullable = false)
  private int age;

  // @Column(nullable = false)
  private String phoneNumber;

  // @Column(nullable = false)
  private String userImageUrl;

  @Enumerated(value = EnumType.STRING)
  protected UserGradeEnum gradeEnum;
  public ManagerPersonResponseDto(User user)
  {
    this.nickName = user.getNickName();
    this.password = user.getPassword();
    this.email = user.getEmail();
    this.age = user.getAge();
    this.phoneNumber = user.getPhoneNumber();
    this.userImageUrl = user.getUserImageUrl();
    this.gradeEnum = user.getGradeEnum();
  }

}
