package com.project.QR.keep.repository;

import com.project.QR.keep.entity.Keep;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface KeepRepository extends JpaRepository<Keep, Long > {
  /**
   * 전체 식자재 조회
   */
  @Query(value = "SELECT K.* FROM KEEP AS K " +
          "LEFT JOIN QR_CODE AS Q ON K.QR_CODE_ID = Q.QR_CODE_ID " +
          "WHERE Q.BUSINESS_ID = :businessId AND " +
          "K.QR_CODE_ID = :qrCodeId " ,
          nativeQuery = true)
  Page<Keep> findAllByBusinessIdAndQrCodeId(@Param("businessId") long businessId,
                                            @Param("qrCodeId") long qrCodeId,
                                            PageRequest created_at);
  /**
   * 특정 식자재 조회
   */
  @Query(value = "SELECT * FROM KEEP WHERE QR_CODE_ID = :qrCodeId AND " +
          "KEEP_ID = :keepId " ,
          nativeQuery = true)
  Optional<Keep> findByIdAndQrCodeId(@Param("keepId") long keepId,
                                     @Param("qrCodeId") long qrCodeId);
}
