package com.project.QR.qrcode.entity;

<<<<<<< HEAD
import com.project.QR.audit.Auditable;
import com.project.QR.keep.entity.Keep;
import com.project.QR.member.entity.Member;
import com.project.QR.reservation.entity.Reservation;
import lombok.Builder;
=======
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.QR.audit.Auditable;
import com.project.QR.business.entity.Business;
import com.project.QR.keep.entity.Keep;
import com.project.QR.reservation.entity.Reservation;
>>>>>>> 4a643a9cd68a9baa8350400c74326bc2b6abe33d
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
<<<<<<< HEAD
=======
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
>>>>>>> 4a643a9cd68a9baa8350400c74326bc2b6abe33d


@NoArgsConstructor
@Getter
@Setter
@Entity
public class QrCode extends Auditable{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
<<<<<<< HEAD
  private long qrCodeId;

  @Column(nullable = false, length = 500)
=======
  private Long qrCodeId;

  @Column(length = 500)
>>>>>>> 4a643a9cd68a9baa8350400c74326bc2b6abe33d
  private String qrCodeImg;

  @Column(nullable = false, length = 200)
  private String target;

  @Enumerated(value = EnumType.STRING)
  @Column(nullable = false, length = 50)
  private QrType qrType;

  @ManyToOne
<<<<<<< HEAD
  @JoinColumn(name = "MEMBER_ID") //FK
  private Member member;

  @OneToOne(mappedBy = "qrCode", cascade = CascadeType.ALL)
  private Keep keep;

  @OneToOne(mappedBy = "qrCode", cascade = CascadeType.ALL)
  private Reservation reservation;

  public enum QrType {
    KEEP,
    RESERVATION;
  }

  @Builder
  public QrCode(String qrCodeImg, String target, QrType qrType, Member member, Keep keep, Reservation reservation) {
    this.qrCodeImg = qrCodeImg;
    this.target = target;
    this.qrType = qrType;
    this.member = member;
    this.keep = keep;
    this.reservation = reservation;
  }
=======
  @JoinColumn(name = "BUSINESS_ID") //FK
  @JsonBackReference
  private Business business;

  @OneToMany(mappedBy = "qrCode", cascade = CascadeType.ALL)
  private List<Keep> keep = new ArrayList<>();

  @JsonManagedReference
  @OneToMany(mappedBy = "qrCode", cascade = CascadeType.ALL)
  private List<Reservation> reservations = new ArrayList<>();

  private LocalDateTime dueDate;
>>>>>>> 4a643a9cd68a9baa8350400c74326bc2b6abe33d
}