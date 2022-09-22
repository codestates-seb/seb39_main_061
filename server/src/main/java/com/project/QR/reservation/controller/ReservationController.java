package com.project.QR.reservation.controller;

import com.project.QR.dto.MultiResponseWithPageInfoDto;
import com.project.QR.dto.SingleResponseWithMessageDto;
import com.project.QR.reservation.dto.ReservationRequestDto;
import com.project.QR.reservation.entity.Reservation;
import com.project.QR.reservation.mapper.ReservationMapper;
import com.project.QR.reservation.service.ReservationService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.websocket.server.PathParam;
import java.util.List;

@Validated
@RestController
@RequestMapping("/reservation/{business-id}/qr-code/{qr-code-id}")
@AllArgsConstructor
public class ReservationController {
  private final ReservationService reservationService;
  private final ReservationMapper mapper;

  /**
   * 예약 등록 API
   */
  @PostMapping
  public ResponseEntity createReservation(@Valid @RequestBody ReservationRequestDto.CreateReservationDto createReservationDto,
                                          @Positive @PathVariable("business-id") long businessId,
                                          @Positive @PathVariable("qr-code-id") long qrCodeId) {
    createReservationDto.setBusinessId(businessId);
    createReservationDto.setQrCodeId(qrCodeId);
    Reservation reservation =  reservationService.createReservation(mapper.createReservationToReservation(createReservationDto));
    return new ResponseEntity(new SingleResponseWithMessageDto<>(mapper.reservationToReservationInfoDto(reservation),
      "CREATED"),
      HttpStatus.CREATED);
  }

  /**
   * 예약 조회 API
   */
  @GetMapping
  public ResponseEntity getReservations(@Positive @PathVariable("business-id") long businessId,
                                        @Positive @PathVariable("qr-code-id") long qrCodeId,
                                        @Positive @PathParam("page") int page,
                                        @Positive @PathParam("size") int size) {
    Page<Reservation> pageOfReservation = reservationService.getReservations(businessId, qrCodeId,page - 1, size);
    List<Reservation> reservationList = pageOfReservation.getContent();
    return new ResponseEntity(new MultiResponseWithPageInfoDto<>(
      mapper.reservationListToReservationInfoDtoList(reservationList),
      pageOfReservation
    ), HttpStatus.OK);
  }

  /**
   * 예약 변경 API
   */
  @PatchMapping("/info/{reservation-id}")
  public ResponseEntity updateReservation(@Positive @PathVariable("business-id") long businessId,
                                          @Positive @PathVariable("qr-code-id") long qrCodeId,
                                          @Positive @PathVariable("reservation-id") long reservationId,
                                          @Valid @RequestBody ReservationRequestDto.UpdateReservationDto updateReservationDto) {
    updateReservationDto.setBusinessId(businessId);
    updateReservationDto.setQrCodeId(qrCodeId);
    updateReservationDto.setReservationId(reservationId);
    Reservation reservation = reservationService.updateReservation(mapper.updateReservationToReservation(updateReservationDto));
    return new ResponseEntity(new SingleResponseWithMessageDto<>(mapper.reservationToReservationInfoDto(reservation),
      "SUCCESS"),
      HttpStatus.OK);
  }

  /**
   * 예약 취소
   */
  @PatchMapping("/cancel/{reservation-id}")
  public ResponseEntity deleteReservation(@Positive @PathVariable("business-id") long businessId,
                                          @Positive @PathVariable("qr-code-id") long qrCodeId,
                                          @Positive @PathVariable("reservation-id") long reservationId,
                                          @Valid @RequestBody ReservationRequestDto.UpdateReservationDto updateReservationDto) {
    updateReservationDto.setBusinessId(businessId);
    updateReservationDto.setQrCodeId(qrCodeId);
    updateReservationDto.setReservationId(reservationId);
    reservationService.deleteReservation(mapper.updateReservationToReservation(updateReservationDto));
    return new ResponseEntity(HttpStatus.NO_CONTENT);
  }
}
