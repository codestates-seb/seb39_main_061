package com.project.QR.qrcode.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class QrCodeRequestDto {

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class CreateQrCodeDto {
    private long memberId;
    @NotBlank
    private String target;
    @NotBlank
    private String qrType;
    private long businessId;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dueDate;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class UpdateQrCodeDto {
    private long qrCodeId;
    private long memberId;
    @NotBlank
    private String target;
    @NotBlank
    private String qrType;
    private long businessId;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dueDate;
  }
}