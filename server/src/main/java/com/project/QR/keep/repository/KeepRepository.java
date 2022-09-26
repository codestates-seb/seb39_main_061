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
 * 등록된 식자재가 있는지 확인
 */
  @Query(value = "SELECT EXISTS (" +
          "SELECT * FROM KEEP WHERE INFO = :info AND " +
          "QR_CODE_ID = :qrCodeId AND " +
 //       "COMPLETED = 'N' AND
 //       "DELETED = 'N' AND " +
          "DATE_FORMAT(CREATED_AT, \"%Y-%m-%d\") = CURDATE())",
          nativeQuery = true)
  long existsInfoAndQrCodeId(@Param("qrCodeId") long qrCodeId,
                             @Param("info") String phone);

  /**
   * 전체 식자재 조회
   */
  @Query(value = "SELECT R.* FROM KEEP AS R " +
          "LEFT JOIN QR_CODE AS Q ON R.QR_CODE_ID = Q.QR_CODE_ID " +
          "WHERE Q.BUSINESS_ID = :businessId AND " +
  //      "R.COMPLETED = 'N' AND " +
          "R.QR_CODE_ID = :qrCodeId AND " +
  //      "R.DELETED = 'N' AND " +
          "DATE_FORMAT(R.CREATED_AT, \"%Y-%m-%d\") = CURDATE()",
          nativeQuery = true)
  Page<Keep> findAllByBusinessIdAndQrCodeId(@Param("businessId") long businessId,
                                            @Param("qrCodeId") long qrCodeId,
                                            PageRequest created_at);
  /**
   * 특정 식자재 조회
   */
  @Query(value = "SELECT * FROM KEEP WHERE QR_CODE_ID = :qrCodeId AND " +
          "KEEP_ID = :keepId AND " +
  //      "COMPLETED = 'N' AND " +
          "DATE_FORMAT(CREATED_AT, \"%Y-%m-%d\") = CURDATE()",
          nativeQuery = true)
  Optional<Keep> findByIdAndQrCodeId(@Param("keepId") long keepId,
                                     @Param("qrCodeId") long qrCodeId);
}
