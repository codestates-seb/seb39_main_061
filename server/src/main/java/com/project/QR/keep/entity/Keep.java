package com.project.QR.keep.entity;

import com.project.QR.audit.Auditable;
import com.project.QR.qrcode.entity.QrCode;
import lombok.Builder;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

public class Keep {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long keepId;

    @Column
    private String info;

    @Column(nullable = false)
    @ColumnDefault("1")
    private int count;

    @OneToOne
    @JoinColumn(name = "QR_CODE_ID") //FK one-to-one
    private QrCode qrcode;

    @Builder
    public Keep(String info, int count, QrCode qrcode) {
        this.info = info;
        this.count = count;
        this.qrcode = qrcode;
    }
}

