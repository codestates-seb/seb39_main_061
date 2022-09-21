package com.project.QR.qrcode.repository;

import com.project.QR.qrcode.entity.QrCode;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface QrCodeRepository extends JpaRepository<QrCode, Long> {
  @Query(value = "SELECT * FROM QR_CODE WHERE QR_CODE_ID = :qrCodeId AND BUSINESS_ID = :businessId", nativeQuery = true)
  Optional<QrCode> findByIdAndBusinessId(@Param("qrCodeId") long qrCodeId,
                                         @Param("businessId") long businessId);

  @Query(value = "SELECT Q.* FROM QR_CODE AS Q " +
    "LEFT JOIN BUSINESS AS B ON Q.BUSINESS_ID = B.BUSINESS_ID " +
    "WHERE B.BUSINESS_ID = :businessId AND B.MEMBER_ID = :memberId", nativeQuery = true)
  Page<QrCode> findAllByBusinessIdAndMemberId(@Param("businessId") long businessId,
                                              @Param("memberId") long memberId,
                                              PageRequest created_at);
}