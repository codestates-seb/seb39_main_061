package com.project.QR.memu.entity;

import com.project.QR.business.entity.Business;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Menu {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long menuId;

  @Column(nullable = false, length = 200)
  private String name;

  @Column(nullable = false)
  private int price;

  @Column(nullable = false, length = 200)
  private String img;

  @ManyToOne
  @JoinColumn(nullable = false, name = "BUSINESS_ID") //FK
  private Business business;
}