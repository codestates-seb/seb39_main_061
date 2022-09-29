package com.project.QR.stub;

import com.project.QR.business.entity.Business;
import com.project.QR.review.dto.ReviewRequestDto;
import com.project.QR.review.dto.ReviewResponseDto;
import com.project.QR.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;

public class ReviewStubData {
  private static final Business business = BusinessStubData.business();
  public static ReviewRequestDto.CreateReviewDto createReviewDto() {
    return ReviewRequestDto.CreateReviewDto.builder()
      .businessId(business.getBusinessId())
      .contents("review")
      .score(5)
      .build();
  }

  public static Review review(long reviewId, String contents, int score) {
    Review review = new Review();
    review.setReviewId(reviewId);
    review.setScore(score);
    review.setContents(contents);
    review.setBusiness(business);
    return review;
  }

  public static ReviewResponseDto.ReviewInfoDto reviewInfoDto(Review review) {
    return ReviewResponseDto.ReviewInfoDto.builder()
      .reviewId(review.getReviewId())
      .contents(review.getContents())
      .score(review.getScore())
      .build();
  }

  public static Page<Review> getReviewList(int page, int size) {
    return new PageImpl<>(List.of(
      review(1L, "review1", 1),
      review(2L, "review2", 2),
      review(3L, "review3", 3)
    ), PageRequest.of(page, size, Sort.by("REVIEW_ID").descending()), 3);
  }

  public static List<ReviewResponseDto.ReviewInfoDto> reviewInfoDtoList(List<Review> reviewList) {
    return reviewList.stream()
      .map(ReviewStubData::reviewInfoDto)
      .collect(Collectors.toList());
  }
}
