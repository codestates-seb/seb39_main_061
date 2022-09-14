package com.project.QR.reservation.entity;

import com.project.QR.qrcode.entity.QrCode;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Reservation {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long reservationId;

  @Column(nullable = false, length = 200)
  private String name;

  @Column(nullable = false, length = 200)
  private String phone;

  @Column(nullable = false)
  private boolean complete;

  @Column(nullable = false)
  @ColumnDefault("1")
  private int count;

  @OneToOne
  @JoinColumn(nullable = false, name = "QR_CODE_ID") //FK one-to-one
  private QrCode qrCode;

  @Builder
  public Reservation(long reservationId, String name, String phone,
                     boolean complete, int count, QrCode qrCode) {
    this.reservationId = reservationId;
    this.name = name;
    this.phone = phone;
    this.complete = complete;
    this.count = count;
    this.qrCode = qrCode;
  }
}

