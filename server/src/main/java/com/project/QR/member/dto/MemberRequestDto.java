package com.project.QR.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.List;

public class MemberRequestDto {
  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class EmailDto {
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z\\d_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z\\d.-]+$")
    private String email;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class CreateMemberDto {
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z\\d_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z\\d.-]+$")
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String name;
    @Pattern(regexp = "\\d{3}-\\d{4}-\\d{4}", message = "휴대전화 양식이 아닙니다.")
    private String phone;
    @NotBlank
    private String businessName;
    @NotBlank
    private String role;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class LoginDto {
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z\\d_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z\\d.-]+$")
    private String email;
    @NotBlank
    private String password;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class OAuthUpdateDto {
    private String email;
    @NotBlank
    private String service;
    @NotBlank
    private String businessName;
    private String businessIntroduction;
    @Pattern(regexp = "\\d{3}-\\d{4}-\\d{4}", message = "휴대전화 양식이 아닙니다.")
    private String phone;
    @NotBlank
    private String name;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class UpdateMemberDto {
    private String email;
    private String password;
    @NotEmpty
    private List<String> service;
    private String profileImg;
    @Pattern(regexp = "\\d{3}-\\d{4}-\\d{4}", message = "휴대전화 양식이 아닙니다.")
    private String phone;
    private String name;
  }
}
