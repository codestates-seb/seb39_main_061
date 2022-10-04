package com.project.QR.keep.entity;

import com.project.QR.audit.Auditable;
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
<<<<<<< HEAD
public class Keep {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long keepId;

  @Column
  private String info;

=======
public class Keep extends Auditable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long keepId;
  @Column
  private String target;
  @Column(columnDefinition = "TEXT")
  private String info;
>>>>>>> 4a643a9cd68a9baa8350400c74326bc2b6abe33d
  @Column(nullable = false)
  @ColumnDefault("1")
  private int count;

<<<<<<< HEAD
  @OneToOne
=======
  @ManyToOne
>>>>>>> 4a643a9cd68a9baa8350400c74326bc2b6abe33d
  @JoinColumn(name = "QR_CODE_ID") //FK one-to-one
  private QrCode qrCode;

  @Builder
<<<<<<< HEAD
  public Keep(String info, int count, QrCode qrCode) {
=======
  public Keep(String target, String info, int count, QrCode qrCode) {
    this.target = target;
>>>>>>> 4a643a9cd68a9baa8350400c74326bc2b6abe33d
    this.info = info;
    this.count = count;
    this.qrCode = qrCode;
  }
}

