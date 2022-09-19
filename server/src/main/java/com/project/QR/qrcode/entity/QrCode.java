package com.project.QR.qrcode.entity;

import com.project.QR.audit.Auditable;
import com.project.QR.keep.entity.Keep;
import com.project.QR.member.entity.Member;
import com.project.QR.reservation.entity.Reservation;
import lombok.Builder;
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
  @JoinColumn(name = "MEMBER_ID") //FK
  private Member member;

  @OneToOne(mappedBy = "qrCode", cascade = CascadeType.ALL)
  private Keep keep;

  @OneToMany(mappedBy = "qrCode", cascade = CascadeType.ALL)
  private List<Reservation> reservations = new ArrayList<>();

  private LocalDateTime dueDate;
}