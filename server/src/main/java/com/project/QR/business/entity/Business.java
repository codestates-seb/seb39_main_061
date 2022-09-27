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
import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@Getter
@Setter
@Entity
public class Business {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long businessId;

  private String name;

  @Column(columnDefinition = "TEXT")
  private String introduction;

  private String openTime;

  private String holiday;

  private String address;

  private String phone;

  private double lon;

  private double lat;

  @OneToOne
  @JoinColumn(name = "MEMBER_ID") //FK one-to-one
  private Member member;

  @OneToMany(mappedBy = "business", cascade = CascadeType.ALL)
  private List<Review> review = new ArrayList<>();

  @OneToMany(mappedBy = "business", cascade = CascadeType.ALL)
  private List<Menu> menu = new ArrayList<>();

  @OneToMany(mappedBy = "business", cascade = CascadeType.ALL)
  private List<QrCode> qrCode = new ArrayList<>();

  @Builder
  public Business(String name, String introduction, Member member, List<Review> review, List<Menu> menu, List<QrCode> qrCode) {
    this.name = name;
    this.introduction = introduction;
    this.member = member;
    this.review = review;
    this.menu = menu;
    this.qrCode = qrCode;
  }
}