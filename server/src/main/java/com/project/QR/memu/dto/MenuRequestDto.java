package com.project.QR.memu.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public class MenuRequestDto {
  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class CreateMenuDto {
    private long memberId;
    private long businessId;
    @NotBlank
    private String name;
    @Positive
    private int price;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class UpdateMenuDto {
    private long menuId;
    private long memberId;
    private long businessId;
    @NotBlank
    private String name;
    @Positive
    private int price;
  }
}
