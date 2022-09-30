package com.project.QR.reservation.entity;

<<<<<<< HEAD
import com.project.QR.qrcode.entity.QrCode;
import lombok.Builder;
=======
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.project.QR.audit.CreatedAuditable;
import com.project.QR.qrcode.entity.QrCode;
>>>>>>> 4a643a9cd68a9baa8350400c74326bc2b6abe33d
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
<<<<<<< HEAD
public class Reservation {
=======
public class Reservation extends CreatedAuditable {
>>>>>>> 4a643a9cd68a9baa8350400c74326bc2b6abe33d
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long reservationId;

  @Column(nullable = false, length = 200)
  private String name;

  @Column(nullable = false, length = 200)
  private String phone;

  @Column(nullable = false)
<<<<<<< HEAD
  private boolean complete;
=======
  @Enumerated(EnumType.STRING)
  private Check completed;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Check deleted;
>>>>>>> 4a643a9cd68a9baa8350400c74326bc2b6abe33d

  @Column(nullable = false)
  @ColumnDefault("1")
  private int count;

<<<<<<< HEAD
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

=======
  @ManyToOne
  @JoinColumn(name = "QR_CODE_ID") //FK one-to-one
  @JsonBackReference
  private QrCode qrCode;
}
>>>>>>> 4a643a9cd68a9baa8350400c74326bc2b6abe33d
