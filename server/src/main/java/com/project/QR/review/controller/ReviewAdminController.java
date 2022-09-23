package com.project.QR.review.controller;

import com.project.QR.dto.MultiResponseWithPageInfoDto;
import com.project.QR.dto.SingleResponseWithMessageDto;
import com.project.QR.review.entity.Review;
import com.project.QR.review.mapper.ReviewMapper;
import com.project.QR.review.service.ReviewService;
import com.project.QR.security.MemberDetails;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import javax.websocket.server.PathParam;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/v1/business/{business-id}/review")
@AllArgsConstructor
public class ReviewAdminController {
  private final ReviewService reviewService;
  private final ReviewMapper mapper;

  /**
   * 리뷰 리스트 조회 API
   */
  @GetMapping
  public ResponseEntity getReviewList(@AuthenticationPrincipal MemberDetails memberDetails,
                                      @Positive @PathVariable("business-id") long businessId,
                                      @Positive @PathParam("page") int page,
                                      @Positive @PathParam("size") int size) {
    Page<Review> pageOfReview = reviewService.getAdminReviewList(businessId, memberDetails.getMember().getMemberId(),
      page - 1, size);
    List<Review> reviewList = pageOfReview.getContent();

    return new ResponseEntity<>(new MultiResponseWithPageInfoDto<>(mapper.reviewListToReviewInfoDtoList(reviewList),
      pageOfReview),
      HttpStatus.OK);
  }

  /**
   * 리뷰 조회 API
   */
  @GetMapping("/{review-id}")
  public ResponseEntity getReview(@AuthenticationPrincipal MemberDetails memberDetails,
                                  @Positive @PathVariable("business-id") long businessId,
                                  @Positive @PathVariable("review-id") long reviewId) {
    Review review = reviewService.getAdminReview(reviewId, businessId, memberDetails.getMember().getMemberId());

    return new ResponseEntity<>(new SingleResponseWithMessageDto<>(mapper.reviewToReviewInfoDto(review),
      "SUCCESS"),
      HttpStatus.OK);
  }

  /**
   * 리뷰 삭제 API
   */
  @DeleteMapping("/{review-id}")
  public ResponseEntity deleteReview(@AuthenticationPrincipal MemberDetails memberDetails,
                                     @Positive @PathVariable("business-id") long businessId,
                                     @Positive @PathVariable("review-id") long reviewId) {
    reviewService.deleteReview(reviewId, businessId, memberDetails.getMember().getMemberId());

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
