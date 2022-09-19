package com.project.QR.qrcode.controller;

import com.project.QR.exception.BusinessLogicException;
import com.project.QR.exception.ExceptionCode;
import com.project.QR.qrcode.service.QrCodeService;
import com.project.QR.security.MemberDetails;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.websocket.server.PathParam;

@Validated
@RestController
@RequestMapping("/api/dash-board/reservation/statistics")
@AllArgsConstructor
public class QrCodeReservationStatisticsController {
  private final QrCodeService qrCodeService;

  @GetMapping
  public ResponseEntity getStatistics(@AuthenticationPrincipal MemberDetails memberDetails,
                                      @Positive @PathParam("date") String date,
                                      @PathParam("option") String option) {
    if(option != null) {
      if(option.equals("month")) {
        qrCodeService.getStatisticsByMonth(date, memberDetails.getMember().getMemberId());
      } else if(option.equals("year")) {
        qrCodeService.getStatisticsByYear(date, memberDetails.getMember().getMemberId());
      } else {
        throw new BusinessLogicException(ExceptionCode.OPTION_IS_INVALID);
      }
    } else {
      qrCodeService.getStatisticsByTime(date, memberDetails.getMember().getMemberId());
    }
    return new ResponseEntity(HttpStatus.OK);
  }
}
