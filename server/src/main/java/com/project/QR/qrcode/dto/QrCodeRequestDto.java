package com.project.QR.qrcode.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    private String email;
    @NotBlank
    private String target;
    @NotBlank
    private String qrType;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime dueDate;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class UpdateQrCodeDto {
    @NotBlank
    private long qrCodeId;
    @NotBlank
    private long memberId;
    @NotBlank
    private String target;

    public void setQrCodeId(long qrCodeId) {

      this.qrCodeId = qrCodeId;
    }
    public void setMemberId ( long memberId){

      this.memberId = memberId;
    }
  }
}