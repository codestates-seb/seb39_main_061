package com.project.QR.business.entity;

import com.project.QR.member.entity.Member;
import com.project.QR.memu.entity.Menu;
import com.project.QR.qrcode.entity.QrCode;
import com.project.QR.review.entity.Review;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@NoArgsConstructor
@Getter
@Setter
@Entity
public class Business {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long businessId;

  @Column
  private String name;

  @Column
  private String introduction;

  @OneToOne
  @JoinColumn(nullable = false, name = "MEMBER_ID") //FK one-to-one
  private Member member;

  @OneToMany(mappedBy = "business", cascade = CascadeType.ALL)
  private Review review;

  @OneToMany(mappedBy = "business", cascade = CascadeType.ALL)
  private Menu menu;

  @OneToMany(mappedBy = "business", cascade = CascadeType.ALL)
  private QrCode qrCode;

  @Builder
  public Business(String name, String introduction, Member member, Review review, Menu menu, QrCode qrCode) {
    this.name = name;
    this.introduction = introduction;
    this.member = member;
    this.review = review;
    this.menu = menu;
    this.qrCode = qrCode;
  }
}