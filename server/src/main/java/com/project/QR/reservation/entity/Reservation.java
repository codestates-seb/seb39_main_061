package com.project.QR.reservation.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.project.QR.audit.CreatedAuditable;
import com.project.QR.qrcode.entity.QrCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Reservation extends CreatedAuditable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long reservationId;

  @Column(nullable = false, length = 200)
  private String name;

  @Column(nullable = false, length = 200)
  private String phone;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Check completed;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Check deleted;

  @Column(nullable = false)
  @ColumnDefault("1")
  private int count;

  @ManyToOne
  @JoinColumn(name = "QR_CODE_ID") //FK one-to-one
  @JsonBackReference
  private QrCode qrCode;
}
