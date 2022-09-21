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
public class Keep extends Auditable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long keepId;

  @Column
  private String info;

  @Column(nullable = false)
  @ColumnDefault("1")
  private int count;

  @ManyToOne
  @JoinColumn(name = "QR_CODE_ID") //FK one-to-one
  private QrCode qrCode;

  @Builder
  public Keep(String info, int count, QrCode qrCode) {
    this.info = info;
    this.count = count;
    this.qrCode = qrCode;
  }
}

