package com.project.QR.keep.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


public class KeepRequestDto {
  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class CreateKeepDto {
    private long memberId;
    private long businessId;
    private long qrCodeId;
    @NotBlank
    private String target;
    private String info;
    private int count;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class UpdateKeepDto {
    private long memberId;
    private long businessId;
    private long qrCodeId;
    private long keepId;
    @NotBlank
    private String target;
    private String info;
    private int count;
  }
}
