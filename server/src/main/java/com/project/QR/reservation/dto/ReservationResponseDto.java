package com.project.QR.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

public class ReservationResponseDto {
  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class reservationInfoDto {
    @NotBlank
    private long reserveId;
    @NotBlank
    private String name;
    @NotBlank
    private String phone;
    private LocalDateTime createdAt;
    private boolean complete;
    @NotBlank
    private int count;
  }

}