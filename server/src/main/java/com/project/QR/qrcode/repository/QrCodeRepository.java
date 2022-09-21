package com.project.QR.qrcode.repository;

import com.project.QR.qrcode.entity.QrCode;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QrCodeRepository extends JpaRepository<QrCode, Long> {
  @Query(value = "SELECT * FROM QR_CODE WHERE MEMBER_ID = :memberId", nativeQuery = true)
  Page<QrCode> findAllByMemberId(@Param("memberId") long memberId, PageRequest pageRequest);
}