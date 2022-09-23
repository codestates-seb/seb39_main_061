package com.project.QR.review.controller;

import com.project.QR.dto.SingleResponseWithMessageDto;
import com.project.QR.review.dto.ReviewRequestDto;
import com.project.QR.review.entity.Review;
import com.project.QR.review.mapper.ReviewMapper;
import com.project.QR.review.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@Validated
@RestController
@RequestMapping("/business/{business-id}/review")
@AllArgsConstructor
public class ReviewUserController {
  private final ReviewService reviewService;
  private final ReviewMapper mapper;

  /**
   * 리뷰 등록 API
   */
  @PostMapping
  public ResponseEntity createReview(@Valid @RequestBody ReviewRequestDto.CreateReviewDto createReviewDto,
                                     @Positive @PathVariable("business-id") long businessId) {
    createReviewDto.setBusinessId(businessId);
    Review review = reviewService.createReview(mapper.createReviewDtoToReview(createReviewDto));

    return new ResponseEntity(new SingleResponseWithMessageDto<>(mapper.reviewToReviewInfoDto(review),
      "CREATED"),
      HttpStatus.CREATED);
  }
}
