package com.project.QR.stub;

import com.project.QR.dto.TokenDto;

import javax.servlet.http.Cookie;

public class TokenStubData {
  private static final String oldAccessToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ5b3VudGFlazExQGdtYWlsLmNvbSIsInJvbGUiOiJST0xFX1JFU0VSVkFUSU9OIiwiaWF0IjoxNjYzMjk5NzQ3LCJleHAiOjE2NjMzMDMzNDd9.oqb5FqwdIZpfyBTTFyIYpEtOdIHKR6HM6r2VC6pG9HJhcgnTXA_ziud2YKLBQwDxYm5C6H5dnktPM-8P1SQixA";
  private static final String newAccessToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ5b3VudGFlazExQGdtYWlsLmNvbSIsInJvbGUiOiJST0xFX1JFU0VSVkFUSU9OIiwiaWF0IjoxNjYzMzAxMDIwLCJleHAiOjE2NjMzMDQ2MjB9._6ZrqJZqaxQjHxS3OF7K-X-IgtGp0eISAQMlFWQi-r5a2iw-Ma9fEGnE87AlmxPErS7hcPRl9rpEf44NIXb6hA";
  private static final String refresh = "eyJhbGciOiJIUzUxMiJ9.eyJpYXQiOjE2NjMyOTk3NDcsImV4cCI6MTY2MzMwMzM0N30.MZETo1_AAbYU8xsxh5XwJokql7m-8u-YrrAs8BinU_qjDO4OL4ll-3XURvorAL4Ec-8yZNxu76X6IEgqRnhJzQ";

  public static TokenDto.TokenInfoDto tokenInfoDTO() {
    return TokenDto.TokenInfoDto.builder()
      .grantType("Bearer")
      .accessToken(oldAccessToken)
      .build();
  }

  public static Cookie cookie() {
    Cookie cookie = new Cookie("refresh", refresh);
    cookie.setPath("/");
    cookie.setHttpOnly(true);
    return cookie;
  }

  public static TokenDto.ReIssueDto reIssueDto() {
    return TokenDto.ReIssueDto.builder()
      .accessToken(newAccessToken)
      .build();
  }

  public static TokenDto.TokenInfoDto newTokenInfoDto() {
    return TokenDto.TokenInfoDto.builder()
      .grantType("Bearer")
      .accessToken(newAccessToken)
      .build();
  }
}
