package com.project.QR.qrcode.repository;

import com.project.QR.qrcode.entity.QrCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QrCodeRepository extends JpaRepository<QrCode, Long> {
}