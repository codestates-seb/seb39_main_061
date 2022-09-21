package com.project.QR.business.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class BusinessResponseDto {
  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class BusinessInfoDto {
    private long businessId;
    private String name;
    private String introduction;
  }
}
