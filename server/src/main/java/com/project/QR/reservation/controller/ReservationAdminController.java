package com.project.QR.reservation.controller;

import com.project.QR.dto.MessageResponseDto;
import com.project.QR.dto.MultiResponseWithPageInfoDto;
import com.project.QR.dto.SingleResponseWithMessageDto;
import com.project.QR.reservation.dto.ReservationResponseDto;
import com.project.QR.reservation.entity.Reservation;
import com.project.QR.reservation.mapper.ReservationMapper;
import com.project.QR.reservation.service.ReservationService;
import com.project.QR.security.MemberDetails;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.websocket.server.PathParam;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/v1/business/{business-id}/reservation/qr-code/{qr-code-id}")
@AllArgsConstructor
public class ReservationAdminController {
  private final ReservationService reservationService;
  private final ReservationMapper mapper;

  /**
   * 예약 리스트 조회 API
   */
  @GetMapping
  public ResponseEntity getReservationList(@AuthenticationPrincipal MemberDetails memberDetails,
                                           @Positive @PathVariable("business-id") long businessId,
                                           @Positive @PathVariable("qr-code-id") long qrCodeId,
                                           @Positive @PathParam("page") int page,
                                           @Positive @PathParam("size") int size) {
    Page<Reservation> pageOfReservation = reservationService.getAdminReservationList(businessId, qrCodeId,
      memberDetails.getMember().getMemberId(), page - 1, size);
    List<Reservation> reservationList = pageOfReservation.getContent();

    return new ResponseEntity<>(new MultiResponseWithPageInfoDto<>(
      mapper.reservationListToReservationInfoDtoList(reservationList),
      pageOfReservation
    ), HttpStatus.OK);
  }

  /**
   * 예약 입장 API
   */
  @PatchMapping("/info/{reservation-id}/enter")
  public ResponseEntity enterReservation(@AuthenticationPrincipal MemberDetails memberDetails,
                                         @Positive @PathVariable("business-id") long businessId,
                                         @Positive @PathVariable("qr-code-id") long qrCodeId,
                                         @Positive @PathVariable("reservation-id") long reservationId) {
    reservationService.enterReservation(reservationId, qrCodeId, businessId, memberDetails.getMember().getMemberId());

    return new ResponseEntity<>(new MessageResponseDto("SUCCESS"), HttpStatus.OK);
  }

  /**
   * 예약 취소 API
   */
  @PatchMapping("/info/{reservation-id}/cancel")
  public ResponseEntity cancelReservation(@AuthenticationPrincipal MemberDetails memberDetails,
                                          @Positive @PathVariable("business-id") long businessId,
                                          @Positive @PathVariable("qr-code-id") long qrCodeId,
                                          @Positive @PathVariable("reservation-id") long reservationId) {
    reservationService.cancelReservation(reservationId, qrCodeId, businessId, memberDetails.getMember().getMemberId());

    return new ResponseEntity<>(new MessageResponseDto("SUCCESS"), HttpStatus.OK);
  }

  /**
   * 통계 정보 조회 API
   */
  @GetMapping("/statistics")
  public ResponseEntity getStatistics(@AuthenticationPrincipal MemberDetails memberDetails,
                                      @Positive @PathVariable("business-id") long businessId,
                                      @Positive @PathVariable("qr-code-id") long qrCodeId,
                                      @NotBlank @PathParam("date") String date) {
    LocalDateTime start = LocalDate.parse(date, DateTimeFormatter.BASIC_ISO_DATE).atStartOfDay();
    ReservationResponseDto.StatisticsInfoDto statisticsInfoDto =
      reservationService.getStatistics(businessId, qrCodeId, start, memberDetails.getMember().getMemberId());

    return new ResponseEntity<>(new SingleResponseWithMessageDto<>(statisticsInfoDto,
      "SUCCESS"),
      HttpStatus.OK);
  }
}
