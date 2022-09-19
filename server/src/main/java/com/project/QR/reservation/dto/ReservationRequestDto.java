package com.project.QR.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class ReservationRequestDto {
  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class CreateReservationDto {
    private long qrCodeId;
    @Size(min = 2, message = "이름은 최소 두 글자 이상이어야합니다.")
    private String name;
    @Pattern(regexp = "\\d{3}-\\d{4}-\\d{4}", message = "휴대전화 양식이 아닙니다.")
    private String phone;
    @Positive
    private int count;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class UpdateReservationDto {
    private long qrCodeId;
    private long reservationId;
    @Size(min = 2, message = "이름은 최소 두 글자 이상이어야합니다.")
    private String name;
    @Pattern(regexp = "\\d{3}-\\d{4}-\\d{4}", message = "휴대전화 양식이 아닙니다.")
    private String phone;
    @Positive
    private int count;
  }
}
