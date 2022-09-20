package com.project.QR.member.entity;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.QR.audit.Auditable;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Setter
@Getter
@Entity
@NoArgsConstructor
@ToString
public class Member extends Auditable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long memberId;

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

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private EmailVerified emailVerified;

  private String profileImg;

  private String phone;

  private String businessName;

  private String verifiedCode;

  private String joinRole;

  public List<String> getRoleList() {
    if(this.role.length() > 0) {
      return Arrays.asList(this.role.split(","));
    }
    return new ArrayList<>();
  }
  @Builder
  public Member(Long memberId, String email, String name, String password, String role, String businessName,
                AuthProvider provider, String profileImg, String phone, String joinRole, EmailVerified emailVerified) {
    this.memberId = memberId;
    this.email = email;
    this.name = name;
    this.password = password;
    this.role = role;
    this.provider = provider;
    this.profileImg = profileImg;
    this.phone = phone;
    this.businessName = businessName;
    this.joinRole = joinRole;
    this.emailVerified = emailVerified;
  }
}

