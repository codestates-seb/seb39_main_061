package com.project.QR.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

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
    @NotBlank
    private String sectorName;
  }


}
