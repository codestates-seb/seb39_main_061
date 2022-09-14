package com.project.QR.keep.entity;

import com.project.QR.audit.Auditable;
import com.project.QR.qrcode.entity.QrCode;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

public class Keep extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long keepId;

    @Column
    private String info;

    @Column(nullable = false)
    @ColumnDefault("1")
    private int count;

    @OneToOne
    @JoinColumn(nullable = false, name = "QR_CODE_ID") //FK one-to-one
    private QrCode qrcode;

    public Keep(long keepId, String info, int count) {
        this.keepId = keepId;
        this.info = info;
        this.count = count;
    }
}
