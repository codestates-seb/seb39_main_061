package com.project.QR.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
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
    @NotBlank
    private String phone;
    @NotBlank
    private String businessName;
    @NotNull
    private long sectorId;
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
    @NotNull
    private long sectorId;
    @NotBlank
    private String businessName;
    @NotBlank
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
    private List<String> service;
    private String profileImg;
    private long sectorId;
    private String businessName;
    private String phone;
    private String name;
  }
}
