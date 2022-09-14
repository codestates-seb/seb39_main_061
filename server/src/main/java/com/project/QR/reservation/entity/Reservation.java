package com.project.QR.reservation.entity;

import com.project.QR.audit.Auditable;
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
    public class Reservation extends Auditable {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long reservationId;

        @Column(nullable = false, length = 200)
        private String name;

        @Column(nullable = false, length = 200)
        private int phone;

        @Column(nullable = false)
        private Boolean complete;

        @Column(nullable = false)
        @ColumnDefault("1")
        private int count;

        @OneToOne(mappedBy = "reservation", cascade = CascadeType.REMOVE)
        @JoinColumn(nullable = false, name = "QR_CODE_ID") //FK one-to-one
        private QrCode qrcode;

        public Reservation(long reservationId, String name, int phone, Boolean complete, int count) {
            this.reservationId = reservationId;
            this.name = name;
            this.phone = phone;
            this.complete = complete;
            this.count = count;
        }
    }

