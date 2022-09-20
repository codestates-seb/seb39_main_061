package com.project.QR.reservation.controller;

import com.project.QR.dto.MultiResponseWithMessageDto;
import com.project.QR.exception.BusinessLogicException;
import com.project.QR.exception.ExceptionCode;
import com.project.QR.qrcode.service.QrCodeService;
import com.project.QR.reservation.dto.ReservationResponseDto;
import com.project.QR.reservation.dto.Statistics;
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
import javax.websocket.server.PathParam;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/v1/reservation/{qr-code-id}/statistics")
@AllArgsConstructor
public class ReservationStatisticsController {
  private final ReservationService reservationService;
  private final ReservationMapper mapper;

  @GetMapping
  public ResponseEntity getStatistics(@AuthenticationPrincipal MemberDetails memberDetails,
                                      @PathVariable("qr-code-id") long qrCodeId,
                                      @NotBlank @PathParam("date") String date,
                                      @PathParam("option") String option) {
    LocalDateTime start = LocalDate.parse(date, DateTimeFormatter.BASIC_ISO_DATE).atStartOfDay();
    List<Statistics> statisticsList = new ArrayList<>();
    if(option != null) {
      if(option.equals("month")) {
        statisticsList = reservationService.getStatisticsByMonth(qrCodeId, start, memberDetails.getMember().getMemberId());
      } else if(option.equals("week")) {
        statisticsList = reservationService.getStatisticsByWeek(qrCodeId, start, memberDetails.getMember().getMemberId());
      } else {
        throw new BusinessLogicException(ExceptionCode.OPTION_IS_INVALID);
      }
    }
    return new ResponseEntity(new MultiResponseWithMessageDto<>(mapper.statisticsListToStatisticsInfoList(statisticsList),
      "SUCCESS"), HttpStatus.OK);
  }
}
