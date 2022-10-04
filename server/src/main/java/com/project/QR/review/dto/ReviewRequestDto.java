package com.project.QR.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class ReviewRequestDto {
  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class CreateReviewDto {
    private long businessId;
    private String contents;
    @Min(1)
    @Max(5)
    private int score;
  }
}
