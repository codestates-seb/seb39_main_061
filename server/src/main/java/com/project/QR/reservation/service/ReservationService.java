package com.project.QR.reservation.service;

import com.project.QR.exception.BusinessLogicException;
import com.project.QR.exception.ExceptionCode;
import com.project.QR.reservation.entity.Reservation;
import com.project.QR.reservation.repository.ReservationRepository;
import com.project.QR.util.CustomBeanUtils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class ReservationService {
  private ReservationRepository reservationRepository;
  private CustomBeanUtils<Reservation> beanUtils;

  /**
   * 예약 등록
   */
  public Reservation createReservation(Reservation reservation) {
    if(findExistReservation(reservation.getQrCode().getQrCodeId(), reservation.getPhone()))
      throw new BusinessLogicException(ExceptionCode.RESERVATION_ALREADY_EXISTS);
    return reservationRepository.save(reservation);
  }

  /**
   * 예약 조회
   */
  @Transactional(readOnly = true)
  public Page<Reservation> getReservations(long qrCodeId, int page, int size) {
    return reservationRepository.findAllByToday(qrCodeId, PageRequest.of(page, size, Sort.by("CREATED_AT").descending()));
  }

  /**
   * 등록된 예약이 있는지 확인
   */
  @Transactional(readOnly = true)
  public boolean findExistReservation(long qrCodeId, String phone) {
    return reservationRepository.existsByPhoneAndToday(qrCodeId, phone);
  }

  /**
   * 예약 변경
   */
  public Reservation updateReservation(Reservation reservation) {
    Reservation findReservation = findVerifiedReservation(reservation.getReservationId(), reservation.getQrCode().getQrCodeId());
    if(!findReservation.getName().equals(reservation.getName()) || !findReservation.getPhone().equals(reservation.getPhone())) {
      throw new BusinessLogicException(ExceptionCode.INVALID_INFO);
    }
    Reservation updatingReservation = beanUtils.copyNonNullProperties(reservation, findReservation);
    return reservationRepository.save(updatingReservation);
  }

  /**
   * 예약 조회
   */
  public Reservation findVerifiedReservation(long reservationId, long qrCodeId) {
    Optional<Reservation> optionalReservation = reservationRepository.findByIdAndQrCodeId(reservationId, qrCodeId);
    return optionalReservation.orElseThrow(() -> new BusinessLogicException(ExceptionCode.RESERVATION_NOT_FOUND));
  }

  /**
   * 예약 취소, delete column 변경
   */
  public void deleteReservation(Reservation reservation) {
    Reservation findReservation = findVerifiedReservation(reservation.getReservationId(), reservation.getQrCode().getQrCodeId());
    if(!findReservation.getName().equals(reservation.getName()) || !findReservation.getPhone().equals(reservation.getPhone())) {
      throw new BusinessLogicException(ExceptionCode.INVALID_INFO);
    }
    reservation.setDelete(true);
    Reservation updatingReservation = beanUtils.copyNonNullProperties(reservation, findReservation);
    reservationRepository.save(updatingReservation);
  }
}
