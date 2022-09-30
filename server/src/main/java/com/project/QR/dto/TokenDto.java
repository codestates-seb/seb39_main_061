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
<<<<<<< HEAD
    private long accessTokenExpiredAt;
=======
>>>>>>> 4a643a9cd68a9baa8350400c74326bc2b6abe33d
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
