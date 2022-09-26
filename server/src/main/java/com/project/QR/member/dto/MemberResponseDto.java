package com.project.QR.member.dto;

import com.project.QR.business.dto.BusinessResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class MemberResponseDto {
  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class MemberInfoDto {
    private String email;
    private List<String> service;
    private String profileImg;
    private String phone;
    private String name;
  }
}
