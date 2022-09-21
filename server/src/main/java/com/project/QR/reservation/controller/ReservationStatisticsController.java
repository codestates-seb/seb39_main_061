package com.project.QR.reservation.controller;

import com.project.QR.dto.SingleResponseWithMessageDto;
import com.project.QR.reservation.dto.ReservationResponseDto;
import com.project.QR.reservation.mapper.ReservationMapper;
import com.project.QR.reservation.service.ReservationService;
import com.project.QR.security.MemberDetails;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.websocket.server.PathParam;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Validated
@RestController
@RequestMapping("/api/v1/reservation/{business-id}/qr-code/{qr-code-id}/statistics")
@AllArgsConstructor
public class ReservationStatisticsController {
  private final ReservationService reservationService;
  private final ReservationMapper mapper;

  @GetMapping
  public ResponseEntity getStatistics(@AuthenticationPrincipal MemberDetails memberDetails,
                                      @Positive @PathVariable("business-id") long businessId,
                                      @Positive @PathVariable("qr-code-id") long qrCodeId,
                                      @NotBlank @PathParam("date") String date) {
    LocalDateTime start = LocalDate.parse(date, DateTimeFormatter.BASIC_ISO_DATE).atStartOfDay();
    ReservationResponseDto.StatisticsInfoDto statisticsInfoDto =
      reservationService.getStatistics(businessId, qrCodeId, start, memberDetails.getMember().getMemberId());

    return new ResponseEntity(new SingleResponseWithMessageDto<>(statisticsInfoDto,
      "SUCCESS"),
      HttpStatus.OK);
  }
}
