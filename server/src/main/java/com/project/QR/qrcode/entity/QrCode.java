package com.project.QR.qrcode.entity;

import com.project.QR.audit.Auditable;
import com.project.QR.keep.entity.Keep;
import com.project.QR.member.entity.Member;
import com.project.QR.reservation.entity.Reservation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;


@NoArgsConstructor
@Getter
@Setter
@Entity
public class QrCode extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long qrCodeId;

    @Column(nullable = false, length = 500)
    private String qrCodeImg;

    @Column(nullable = false, length = 200)
    private String target;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false, length = 50)
    private QrType qrType;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID") //FK
    private Member member;

    @OneToOne(mappedBy = "qrCode", cascade = {CascadeType.ALL})
    private Keep keep;

    @OneToOne(mappedBy = "reservation", cascade = {CascadeType.ALL})
    private Reservation reservation;

    public enum QrType {
        KEEP,
        RESERVATION;
    }

    public QrCode(long qrCodeId, String qrCodeImg, String target, QrType qrType) {
        this.qrCodeId = qrCodeId;
        this.qrCodeImg = qrCodeImg;
        this.target = target;
        this.qrType = qrType;
    }
}