package com.project.QR.memu.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class MenuResponseDto {
  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class MenuInfoDto {
    private long menuId;
    private String name;
    private int price;
    private String img;
  }
}
