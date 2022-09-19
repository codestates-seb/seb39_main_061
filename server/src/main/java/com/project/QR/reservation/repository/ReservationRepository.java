package com.project.QR.reservation.repository;

import com.project.QR.reservation.entity.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
  /**
   * mySQL: DATE_FORMAT(CREATED_AT, "%Y-%m-%d")
   * H2: FORMATDATETIME(CREATED_AT, 'yyyy-MM-dd')
   */
  @Query(value = "SELECT EXISTS (" +
    "SELECT * FROM RESERVATION WHERE PHONE = :phone AND " +
    "QR_CODE_ID = :qrCodeId" +
    "COMPLETE = FALSE AND " +
    "DELETE = FALSE AND " +
    "FORMATDATETIME(CREATED_AT, 'yyyy-MM-dd') = CURDATE())",
    nativeQuery = true)
  boolean existsByPhoneAndToday(@Param("qrCodeId") long qrCodeId,
                                @Param("phone") String phone);

  @Query(value = "SELECT * FROM RESERVATION WHERE COMPLETE = FALSE AND " +
    "DELETE = FALSE AND " +
    "FORMATDATETIME(CREATED_AT, 'yyyy-MM-dd') = CURDATE()",
    nativeQuery = true)
  Page<Reservation> findAllByToday(@Param("qrCodeId") long qrCodeId, PageRequest created_at);

  @Query(value = "SELECT * FROM RESERVATION WHERE PHONE = :phone AND " +
    "QR_CODE_ID = :qrCodeId" +
    "RESERVATION_ID = :reservationId AND " +
    "COMPLETE = FALSE AND " +
    "FORMATDATETIME(CREATED_AT, 'yyyy-MM-dd') = CURDATE()",
    nativeQuery = true)
  Optional<Reservation> findByIdAndQrCodeId(@Param("reservationId") long reservationId,
                                            @Param("qrCodeId") long qrCodeId);
}
