package com.project.QR.member.entity;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Member {
  @Id
  @GeneratedValue
  private long memberId;

  @Column(unique = true)
  private String email;

  private String name;

  @JsonIgnore
  private String password;

  @Enumerated(EnumType.STRING)
  private Authority authority;

  @Column(nullable = false)
  private String phone;

  @Column(nullable = false)
  private AuthProvider provider;

  @Column(nullable = false)
  private Boolean emailVerified = false;

  @Enumerated(EnumType.STRING)
  private Sector sector;

  private String profileImg;

  @Builder
  public Member(String email, String name, String password, String phone, Authority authority,
                AuthProvider provider, String profileImg) {
    this.email = email;
    this.name = name;
    this.phone = phone;
    this.password = password;
    this.authority = authority;
    this.provider = provider;
    this.profileImg = profileImg;
  }
}

