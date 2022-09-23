package com.project.QR.review.controller;

import com.project.QR.dto.MultiResponseWithPageInfoDto;
import com.project.QR.dto.SingleResponseWithMessageDto;
import com.project.QR.review.dto.ReviewRequestDto;
import com.project.QR.review.entity.Review;
import com.project.QR.review.mapper.ReviewMapper;
import com.project.QR.review.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.websocket.server.PathParam;
import java.util.List;

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

  /**
   * 리뷰 리스트 조회 API
   */
  @GetMapping
  public ResponseEntity getReviewList(@Positive @PathVariable("business-id") long businessId,
                                      @Positive @PathParam("page") int page,
                                      @Positive @PathParam("size") int size) {
    Page<Review> pageOfReview = reviewService.getUserReviewList(businessId, page - 1, size);
    List<Review> reviewList = pageOfReview.getContent();

    return new ResponseEntity<>(new MultiResponseWithPageInfoDto<>(mapper.reviewListToReviewInfoDtoList(reviewList),
      pageOfReview),
      HttpStatus.OK);
  }

  /**
   * 리뷰 조회 API
   */
  @GetMapping("/{review-id}")
  public ResponseEntity getReview(@Positive @PathVariable("business-id") long businessId,
                                  @Positive @PathVariable("review-id") long reviewId) {
    Review review = reviewService.getUserReview(reviewId, businessId);

    return new ResponseEntity<>(new SingleResponseWithMessageDto<>(mapper.reviewToReviewInfoDto(review),
      "SUCCESS"),
      HttpStatus.OK);
  }
}
