package com.project.QR.qrcode.dto;

import com.project.QR.reservation.dto.ReservationResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class QrCodeResponseDto {
  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class QrCodeInfoDto {
    @NotBlank
    private long qrCodeId;
    @NotBlank
    private String qrCodeImg;
    @NotBlank
    private String target;

    List<ReservationResponseDto.reservationInfoDto> reservations;
  }
}