package com.project.QR.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

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

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class ReIssueDto {
    @NotBlank
    private String accessToken;
  }
}
