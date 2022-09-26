package com.project.QR.business.repository;

import com.project.QR.business.entity.Business;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BusinessRepository extends JpaRepository<Business, Long> {
  @Query(value = "SELECT * FROM BUSINESS WHERE BUSINESS_ID = :businessId AND MEMBER_ID = :memberId", nativeQuery = true)
  Optional<Business> findByIdAndMemberId(@Param("businessId") long businessId,
                                         @Param("memberId") long memberId);
}
