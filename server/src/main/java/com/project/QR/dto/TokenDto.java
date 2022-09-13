package com.project.QR.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class TokenDto {
  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class TokenInfoDto {
    private String grantType;
    private String accessToken;
    private long accessTokenExpiredAt;
  }
}
