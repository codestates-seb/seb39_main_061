package com.project.QR.qrcode.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

public class QrCodeRequestDto {

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class CreateQrCodeDto {
    @NotBlank
    private long qrCodeId;
    @NotBlank
    private long memberId;
    @NotBlank
    private String businessName;
    @NotBlank
    private String target;

    public void setQrCodeId(long qrCodeId) {

      this.qrCodeId = qrCodeId;
    }
    public void setMemberId ( long memberId){

      this.memberId = memberId;
    }
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