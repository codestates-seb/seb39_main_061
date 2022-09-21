package com.project.QR.reservation.dto;

import com.project.QR.reservation.entity.Check;
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
  public static class ReservationInfoDto {
    private long reserveId;
    private String name;
    private String phone;
    private LocalDateTime createdAt;
    private Check completed;
    private int count;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class StatisticsInfoDto {
    private List<StatisticsDto> month;
    private List<StatisticsDto> week;
    private List<StatisticsDto> time;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class StatisticsDto {
    private Check deleted;
    private String date;
    private int count;
  }

}