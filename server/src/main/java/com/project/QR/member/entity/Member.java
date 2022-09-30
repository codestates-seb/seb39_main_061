package com.project.QR.member.entity;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
<<<<<<< HEAD
import com.project.QR.sector.entity.Sector;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
=======
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.QR.audit.Auditable;
import com.project.QR.business.entity.Business;
import lombok.*;
>>>>>>> 4a643a9cd68a9baa8350400c74326bc2b6abe33d

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Setter
@Getter
@Entity
@NoArgsConstructor
<<<<<<< HEAD
public class Member {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long memberId;
=======
@ToString
public class Member extends Auditable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long memberId;
>>>>>>> 4a643a9cd68a9baa8350400c74326bc2b6abe33d

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

<<<<<<< HEAD
  @Column(nullable = false)
  private Boolean emailVerified = false;
=======
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private EmailVerified emailVerified;
>>>>>>> 4a643a9cd68a9baa8350400c74326bc2b6abe33d

  private String profileImg;

  private String phone;

<<<<<<< HEAD
  private String businessName;

=======
>>>>>>> 4a643a9cd68a9baa8350400c74326bc2b6abe33d
  private String verifiedCode;

  private String joinRole;

<<<<<<< HEAD
  @ManyToOne
  @JoinColumn(name = "SECTOR_ID")
  private Sector sector;

=======
>>>>>>> 4a643a9cd68a9baa8350400c74326bc2b6abe33d
  public List<String> getRoleList() {
    if(this.role.length() > 0) {
      return Arrays.asList(this.role.split(","));
    }
    return new ArrayList<>();
  }
<<<<<<< HEAD
  @Builder
  public Member(String email, String name, String password, String role, String businessName,
                AuthProvider provider, String profileImg, Sector sector, String phone, String joinRole) {
=======

  @JsonManagedReference
  @OneToOne(mappedBy = "member", cascade = CascadeType.ALL)
  private Business business;

  @Builder
  public Member(Long memberId, String email, String name, String password, String role, AuthProvider provider,
                 String profileImg, String phone, String joinRole, EmailVerified emailVerified, Business business) {
    this.memberId = memberId;
>>>>>>> 4a643a9cd68a9baa8350400c74326bc2b6abe33d
    this.email = email;
    this.name = name;
    this.password = password;
    this.role = role;
    this.provider = provider;
    this.profileImg = profileImg;
<<<<<<< HEAD
    this.sector = sector;
    this.phone = phone;
    this.businessName = businessName;
    this.joinRole = joinRole;
=======
    this.phone = phone;
    this.joinRole = joinRole;
    this.emailVerified = emailVerified;
    this.business = business;
>>>>>>> 4a643a9cd68a9baa8350400c74326bc2b6abe33d
  }
}

