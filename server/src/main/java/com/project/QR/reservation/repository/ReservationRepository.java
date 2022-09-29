package com.project.QR.reservation.repository;

import com.project.QR.reservation.dto.ReservationResponseDto;
import com.project.QR.reservation.dto.Statistics;
import com.project.QR.reservation.entity.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
  /**
   * mySQL: DATE_FORMAT(CREATED_AT, "%Y-%m-%d")
   * H2: FORMATDATETIME(CREATED_AT, 'yyyy-MM-dd')
   */
  @Query(value = "SELECT EXISTS (" +
    "SELECT * FROM RESERVATION WHERE PHONE = :phone AND " +
    "QR_CODE_ID = :qrCodeId AND " +
    "COMPLETED = 'N' AND " +
    "DELETED = 'N' AND " +
    "DATE_FORMAT(CREATED_AT, \"%Y-%m-%d\") = CURDATE())",
    nativeQuery = true)
  long existsByPhoneAndToday(@Param("qrCodeId") long qrCodeId,
                                @Param("phone") String phone);

  @Query(value = "SELECT R.* FROM RESERVATION AS R " +
    "LEFT JOIN QR_CODE AS Q ON R.QR_CODE_ID = Q.QR_CODE_ID " +
    "WHERE Q.BUSINESS_ID = :businessId AND " +
    "R.COMPLETED = 'N' AND " +
    "R.QR_CODE_ID = :qrCodeId AND " +
    "R.DELETED = 'N' AND " +
    "DATE_FORMAT(R.CREATED_AT, \"%Y-%m-%d\") = CURDATE()",
    nativeQuery = true)
  Page<Reservation> findAllByToday(@Param("businessId") long businessId,
                                   @Param("qrCodeId") long qrCodeId,
                                   PageRequest created_at);

  @Query(value = "SELECT * FROM RESERVATION WHERE QR_CODE_ID = :qrCodeId AND " +
    "RESERVATION_ID = :reservationId AND " +
    "COMPLETED = 'N' AND " +
    "DATE_FORMAT(CREATED_AT, \"%Y-%m-%d\") = CURDATE()",
    nativeQuery = true)
  Optional<Reservation> findByIdAndQrCodeId(@Param("reservationId") long reservationId,
                                            @Param("qrCodeId") long qrCodeId);

  @Query(value = "SELECT DELETED, DATE_FORMAT(CREATED_AT, '%Y-%m') AS DATE, COUNT(MONTH(CREATED_AT)) AS COUNT " +
    "FROM RESERVATION " +
    "WHERE QR_CODE_ID = :qrCodeId AND " +
    "DATE(CREATED_AT) BETWEEN DATE_SUB(:start, INTERVAL 1 YEAR) AND " +
    ":start GROUP BY DELETED, DATE_FORMAT(CREATED_AT, '%Y-%m')", nativeQuery = true)
  List<Statistics> findStatisticsByMonth(@Param("qrCodeId") long qrCodeId,
                                         @Param("start") LocalDateTime start);

  @Query(value = "SELECT DELETED, DATE_FORMAT(CREATED_AT, '%Y-%m-%d') AS DATE, COUNT(MONTH(CREATED_AT)) AS COUNT " +
    "FROM RESERVATION " +
    "WHERE QR_CODE_ID = :qrCodeId AND " +
    "DATE(CREATED_AT) BETWEEN DATE_SUB(:start, INTERVAL 1 WEEK) AND " +
    ":start GROUP BY DELETED, DATE_FORMAT(CREATED_AT, '%Y-%m-%d')", nativeQuery = true)
  List<Statistics> findStatisticsByWeek(@Param("qrCodeId") long qrCodeId,
                                        @Param("start") LocalDateTime start);

  @Query(value = "SELECT DELETED, HOUR(CREATED_AT) AS DATE, COUNT(HOUR(CREATED_AT)) AS COUNT " +
    "FROM RESERVATION " +
    "WHERE QR_CODE_ID = :qrCodeId AND " +
    "DATE(CREATED_AT) = DATE(:start) " +
    "GROUP BY DELETED, HOUR(CREATED_AT)", nativeQuery = true)
  List<Statistics> findStatisticsByTime(@Param("qrCodeId") long qrCodeId,
                                        @Param("start") LocalDateTime start);
}
