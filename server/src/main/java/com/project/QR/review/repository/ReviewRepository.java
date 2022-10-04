package com.project.QR.review.repository;

import com.project.QR.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {
  @Query(value = "SELECT * FROM REVIEW WHERE BUSINESS_ID = :businessId", nativeQuery = true)
  Page<Review> findAllByBusinessId(@Param("businessId") long businessId,
                                   PageRequest pageRequest);
}
