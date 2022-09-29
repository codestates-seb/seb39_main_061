package com.project.QR.reservation.service;

import com.project.QR.business.service.BusinessService;
import com.project.QR.exception.BusinessLogicException;
import com.project.QR.exception.ExceptionCode;
import com.project.QR.helper.page.RestPage;
import com.project.QR.qrcode.entity.QrCode;
import com.project.QR.qrcode.entity.QrType;
import com.project.QR.qrcode.service.QrCodeService;
import com.project.QR.reservation.dto.ReservationResponseDto;
import com.project.QR.reservation.dto.Statistics;
import com.project.QR.reservation.entity.Check;
import com.project.QR.reservation.entity.Reservation;
import com.project.QR.reservation.repository.ReservationRepository;
import com.project.QR.util.CustomBeanUtils;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class ReservationService {
  private ReservationRepository reservationRepository;
  private CustomBeanUtils<Reservation> beanUtils;
  private QrCodeService qrCodeService;
  private BusinessService businessService;

  /**
   * 예약 등록
   */
  @CacheEvict(cacheNames = {"getReservationList"}, allEntries = true)
  public Reservation createReservation(Reservation reservation) {
    if(findExistReservation(reservation.getQrCode().getQrCodeId(), reservation.getPhone()) != 0)
      throw new BusinessLogicException(ExceptionCode.RESERVATION_ALREADY_EXISTS);
    return reservationRepository.save(reservation);
  }

  /**
   * 예약 리스트 조회(사용자 입장)
   */
  @Cacheable(key = "{#businessId, #page}", value = "getReservationList")
  @Transactional(readOnly = true)
  public RestPage<Reservation> getUserReservationList(long businessId, long qrCodeId, int page, int size) {
    return new RestPage<>(reservationRepository.findAllByToday(businessId, qrCodeId,
      PageRequest.of(page, size, Sort.by("CREATED_AT").descending())));
  }

  /**
   * 등록된 예약이 있는지 확인
   */
  @Transactional(readOnly = true)
  public long findExistReservation(long qrCodeId, String phone) {
    return reservationRepository.existsByPhoneAndToday(qrCodeId, phone);
  }

  /**
   * 예약 변경
   */
  @CacheEvict(cacheNames = {"getReservationList"}, allEntries = true)
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
  private Reservation findVerifiedReservation(long reservationId, long qrCodeId) {
    Optional<Reservation> optionalReservation = reservationRepository.findByIdAndQrCodeId(reservationId, qrCodeId);
    return optionalReservation.orElseThrow(() -> new BusinessLogicException(ExceptionCode.RESERVATION_NOT_FOUND));
  }

  /**
   * 예약 취소, delete column 변경
   */
  @CacheEvict(cacheNames = {"getReservationList"}, allEntries = true)
  public void deleteReservation(Reservation reservation) {
    Reservation findReservation = findVerifiedReservation(reservation.getReservationId(), reservation.getQrCode().getQrCodeId());
    if(!findReservation.getName().equals(reservation.getName()) || !findReservation.getPhone().equals(reservation.getPhone())) {
      throw new BusinessLogicException(ExceptionCode.INVALID_INFO);
    }
    reservation.setDeleted(Check.Y);
    Reservation updatingReservation = beanUtils.copyNonNullProperties(reservation, findReservation);
    reservationRepository.save(updatingReservation);
  }

  /**
   * 월간 통계 데이터
   */
  @Transactional(readOnly = true)
  private List<Statistics> getStatisticsByMonth(long qrCodeId, LocalDateTime start) {
    return reservationRepository.findStatisticsByMonth(qrCodeId, start);
  }

  /**
   * 주간 통계 데이터
   */
  @Transactional(readOnly = true)
  private List<Statistics> getStatisticsByWeek(long qrCodeId, LocalDateTime start) {
    return reservationRepository.findStatisticsByWeek(qrCodeId, start);
  }

  /**
   * 시간대별 통계 데이터
   */
  @Transactional(readOnly = true)
  private List<Statistics> getStatisticsByTime(long qrCodeId, LocalDateTime start) {
    return reservationRepository.findStatisticsByTime(qrCodeId, start);
  }

  /**
   * 통계 데이터
   */
  @Transactional(readOnly = true)
  public ReservationResponseDto.StatisticsInfoDto getStatistics(long businessId, long qrCodeId, LocalDateTime start, Long memberId) {
    QrCode qrCode = qrCodeService.getQrCode(qrCodeId, businessId, memberId);
    if(!qrCode.getQrType().equals(QrType.RESERVATION)) {
      throw new BusinessLogicException(ExceptionCode.QR_CODE_NOT_FOUND);
    }
    List<Statistics> monthList = getStatisticsByMonth(qrCodeId, start);
    List<Statistics> weekList = getStatisticsByWeek(qrCodeId, start);
    List<Statistics> timeList = getStatisticsByTime(qrCodeId, start);
    return ReservationResponseDto.StatisticsInfoDto.builder()
      .month(monthList.stream()
        .map(month->ReservationResponseDto.StatisticsDto.builder()
          .count(month.getCount())
          .date(month.getDate())
          .deleted(month.getDeleted())
          .build()
        )
        .collect(Collectors.toList())
      )
      .week(weekList.stream()
        .map(week->ReservationResponseDto.StatisticsDto.builder()
          .count(week.getCount())
          .date(week.getDate())
          .deleted(week.getDeleted())
          .build()
        )
        .collect(Collectors.toList())
      )
      .time(timeList.stream()
        .map(time->ReservationResponseDto.StatisticsDto.builder()
          .count(time.getCount())
          .date(time.getDate())
          .deleted(time.getDeleted())
          .build()
        )
        .collect(Collectors.toList())
      )
      .build();
  }

  /**
   * 예약 리스트 조회(업주 입장)
   */
  @Cacheable(key = "{#businessId, #page}", value = "getReservationList")
  public RestPage<Reservation> getAdminReservationList(long businessId, long qrCodeId, Long memberId, int page, int size) {
    businessService.existBusiness(businessId, memberId);
    return new RestPage<>(reservationRepository.findAllByToday(businessId, qrCodeId,
      PageRequest.of(page, size, Sort.by("CREATED_AT").descending())));
  }

  /**
   * 예약 입장
   */
  @CacheEvict(cacheNames = {"getReservationList"}, allEntries = true)
  public void enterReservation(long reservationId, long qrCodeId, long businessId, Long memberId) {
    businessService.existBusiness(businessId, memberId);
    Reservation findReservation = findVerifiedReservation(reservationId, qrCodeId);
    // TO-DO : SMS 발송 기능
    findReservation.setCompleted(Check.Y);
    reservationRepository.save(findReservation);
  }

  /**
   * 예약 취소
   */
  @CacheEvict(cacheNames = {"getReservationList"}, allEntries = true)
  public void cancelReservation(long reservationId, long qrCodeId, long businessId, Long memberId) {
    businessService.existBusiness(businessId, memberId);
    Reservation findReservation = findVerifiedReservation(reservationId, qrCodeId);
    // TO-DO : SMS 발송 기능
    findReservation.setDeleted(Check.Y);
    reservationRepository.save(findReservation);
  }
}
