package com.project.QR.review.service;

import com.project.QR.business.service.BusinessService;
import com.project.QR.exception.BusinessLogicException;
import com.project.QR.exception.ExceptionCode;
import com.project.QR.review.entity.Review;
import com.project.QR.review.repository.ReviewRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class ReviewService {
  private final ReviewRepository reviewRepository;
  private final BusinessService businessService;

  /**
   * 리뷰 등록
   */
  public Review createReview(Review review) {
    return reviewRepository.save(review);
  }

  /**
   * 리뷰 리스트 조회(업주 입장)
   */
  @Transactional(readOnly = true)
  public Page<Review> getAdminReviewList(long businessId, long memberId, int page, int size) {
    businessService.existBusiness(businessId, memberId);
    return reviewRepository.findAllByBusinessId(businessId,
      PageRequest.of(page, size, Sort.by("CREATED_AT").descending()));
  }

  /**
   * 리뷰 리스트 조회(사용자 입장)
   */
  @Transactional(readOnly = true)
  public Page<Review> getUserReviewList(long businessId, int page, int size) {
    return reviewRepository.findAllByBusinessId(businessId,
      PageRequest.of(page, size, Sort.by("CREATED_AT").descending()));
  }

  /**
   * 리뷰 조회(업주 입장)
   */
  @Transactional(readOnly = true)
  public Review getAdminReview(long reviewId, long businessId, long memberId) {
    businessService.existBusiness(businessId, memberId);
    return findVerifiedReview(reviewId);
  }

  /**
   * 리뷰 조회(업주 입장)
   */
  @Transactional(readOnly = true)
  public Review getUserReview(long reviewId, long memberId) {
    return findVerifiedReview(reviewId);
  }

  /**
   * 유효한 리뷰 조회
   */
  @Transactional(readOnly = true)
  private Review findVerifiedReview(long reviewId) {
    Optional<Review> optionalReview = reviewRepository.findById(reviewId);
    return optionalReview.orElseThrow(() -> new BusinessLogicException(ExceptionCode.REVIEW_NOT_FOUND));
  }
}
