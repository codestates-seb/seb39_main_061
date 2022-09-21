package com.project.QR.review.entity;

import com.project.QR.audit.CreatedAuditable;
import com.project.QR.business.entity.Business;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Review extends CreatedAuditable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long reviewId;

  @Column(nullable = false, length = 200)
  private String contents;

  @Column(nullable = false)
  private int score;

  @ManyToOne
  @JoinColumn(name = "BUSINESS_ID") //FK
  private Business business;
}