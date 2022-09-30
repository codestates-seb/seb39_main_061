package com.project.QR.business.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

public class BusinessRequestDto {
  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class UpdateBusinessDto {
    private long businessId;
    private long memberId;
    private String name;
    private String introduction;
    private String openTime;
    private String holiday;
    private String address;
    private String phone;
    private double lon;
    private double lat;
  }
}
