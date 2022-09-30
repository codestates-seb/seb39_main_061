package com.project.QR.review.mapper;

import com.project.QR.business.entity.Business;
import com.project.QR.review.dto.ReviewRequestDto;
import com.project.QR.review.dto.ReviewResponseDto;
import com.project.QR.review.entity.Review;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
  default Review createReviewDtoToReview(ReviewRequestDto.CreateReviewDto createReviewDto) {
    Business business = new Business();
    business.setBusinessId(createReviewDto.getBusinessId());
    Review review = new Review();
    review.setContents(createReviewDto.getContents());
    review.setBusiness(business);
    review.setScore(createReviewDto.getScore());
    return review;
  }

  ReviewResponseDto.ReviewInfoDto reviewToReviewInfoDto(Review review);

  default List<ReviewResponseDto.ReviewInfoDto> reviewListToReviewInfoDtoList(List<Review> reviewList) {
    return reviewList.stream()
      .map(this::reviewToReviewInfoDto)
      .collect(Collectors.toList());
  }
}
