package com.project.QR.stub;

import com.project.QR.dto.TokenDto;

public class TokenStubData {
  public static TokenDto.TokenInfoDto tokenInfoDTO() {
    return TokenDto.TokenInfoDto.builder()
      .grantType("Bearer")
      .accessToken("sdf")
      .build();
  }
}
