package com.project.QR.member.entity;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.QR.sector.entity.Sector;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Setter
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

  @Column(nullable = false)
  private String role;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private AuthProvider provider;

  @Column(nullable = false)
  private Boolean emailVerified = false;

  private String profileImg;

  private String phone;

  private String businessName;

  @ManyToOne
  @JoinColumn(name = "SECTOR_ID")
  private Sector sector;

  public List<String> getRoleList() {
    if(this.role.length() > 0) {
      return Arrays.asList(this.role.split(","));
    }
    return new ArrayList<>();
  }
  @Builder
  public Member(String email, String name, String password, String role, String businessName,
                AuthProvider provider, String profileImg, Sector sector, String phone) {
    this.email = email;
    this.name = name;
    this.password = password;
    this.role = role;
    this.provider = provider;
    this.profileImg = profileImg;
    this.sector = sector;
    this.phone = phone;
    this.businessName = businessName;
  }
}

