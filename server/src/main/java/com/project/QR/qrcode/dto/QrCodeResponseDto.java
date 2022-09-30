package com.project.QR.qrcode.dto;

import com.project.QR.qrcode.entity.QrType;
import com.project.QR.reservation.dto.ReservationResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class QrCodeResponseDto {
  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class QrCodeInfoDto {
    private long qrCodeId;
    private String qrCodeImg;
    private String target;
    private QrType qrType;
  }
  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class ShortQrCodeInfoDto {
    private long qrCodeId;
    private String qrCodeImg;
    private String target;
    private QrType qrType;
  }
}