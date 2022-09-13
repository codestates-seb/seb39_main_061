package com.project.QR.member.entity;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Member {
  @Id
  @GeneratedValue
  private long memberId;

  @Column(unique = true)
  private String email;

  private String nickName;

  @JsonIgnore
  private String password;

  @Enumerated(EnumType.STRING)
  private Authority authority;

  @Column(nullable = false)
  private AuthProvider provider;

  @Column(nullable = false)
  private Boolean emailVerified = false;

  private String profileImg;

  private String providerId;

  @Builder
  public Member(String email, String nickName, String password, Authority authority,
                AuthProvider provider, String profileImg, String providerId) {
    this.email = email;
    this.nickName = nickName;
    this.password = password;
    this.authority = authority;
    this.provider = provider;
    this.profileImg = profileImg;
    this.providerId = providerId;
  }
}

