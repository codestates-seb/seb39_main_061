package com.project.QR.keep.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class KeepResponseDto {
  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class KeepInfoDto {
    private long keepId;
    private String target;
    private String info;
    private int count;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
  }
}
