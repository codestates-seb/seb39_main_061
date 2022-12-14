package com.project.QR.qrcode.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.QR.audit.Auditable;
import com.project.QR.business.entity.Business;
import com.project.QR.keep.entity.Keep;
import com.project.QR.reservation.entity.Reservation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@Getter
@Setter
@Entity
public class QrCode extends Auditable{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long qrCodeId;

  @Column(length = 500)
  private String qrCodeImg;

  @Column(nullable = false, length = 200)
  private String target;

  @Enumerated(value = EnumType.STRING)
  @Column(nullable = false, length = 50)
  private QrType qrType;

  @ManyToOne
  @JoinColumn(name = "BUSINESS_ID") //FK
  @JsonBackReference
  private Business business;

  @OneToMany(mappedBy = "qrCode", cascade = CascadeType.ALL)
  private List<Keep> keep = new ArrayList<>();

  @JsonManagedReference
  @OneToMany(mappedBy = "qrCode", cascade = CascadeType.ALL)
  private List<Reservation> reservations = new ArrayList<>();

  private LocalDateTime dueDate;
}