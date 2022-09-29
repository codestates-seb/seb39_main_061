package com.project.QR.business.service;

import com.project.QR.business.entity.Business;
import com.project.QR.business.repository.BusinessRepository;
import com.project.QR.exception.BusinessLogicException;
import com.project.QR.exception.ExceptionCode;
import com.project.QR.file.service.StorageService;
import com.project.QR.util.CustomBeanUtils;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class BusinessService {
  private final BusinessRepository businessRepository;
  private final CustomBeanUtils<Business> beanUtils;
  private final StorageService storageService;

  /**
   * Business 조회
   */
  @Transactional(readOnly = true)
  @Cacheable(key = "#memberId", value = "getBusiness")
  public Business getBusiness(long businessId, long memberId) {
    return findVerifiedBusiness(businessId, memberId);
  }

  /**
   * 회원 식별자로 매장 조회
   */
  @Transactional(readOnly = true)
  public Business getBusinessByMemberId(long memberId) {
    return findVerifiedBusinessByMemberId(memberId);
  }

  /**
   * 매장 식별자로 매장 조회
   */
  public Business getBusinessByBusinessId(long businessId) {
    return findVerifiedBusinessById(businessId);
  }

  /**
   * 유효한 Business 조회
   */
  @Transactional(readOnly = true)
  private Business findVerifiedBusiness(long businessId, long memberId) {
    Optional<Business> optionalBusiness = businessRepository.findByIdAndMemberId(businessId, memberId);
    return optionalBusiness.orElseThrow(() -> new BusinessLogicException(ExceptionCode.BUSINESS_NOT_FOUND));
  }

  /**
   * 회원 식별자를 이용한 유효한 Business 조회
   */
  @Transactional(readOnly = true)
  private Business findVerifiedBusinessByMemberId(long memberId) {
    Optional<Business> optionalBusiness = businessRepository.findByMemberId(memberId);
    return optionalBusiness.orElseThrow(() -> new BusinessLogicException(ExceptionCode.BUSINESS_NOT_FOUND));
  }

  /**
   * 매장 식별자를 이용한 유효한 business 조회
   */
  @Transactional(readOnly = true)
  private Business findVerifiedBusinessById(long businessId){
    Optional<Business> optionalBusiness = businessRepository.findById(businessId);
    return optionalBusiness.orElseThrow(() -> new BusinessLogicException(ExceptionCode.BUSINESS_NOT_FOUND));
  }

  /**
   * Business 저장
   */
  public Business createBusiness(Business business) {
    return businessRepository.save(business);
  }

  /**
   * Business 정보 변경
   */
  public Business updateBusiness(Business business) {
    Business findBusiness = findVerifiedBusiness(business.getBusinessId(), business.getMember().getMemberId());
    Business updatingBusiness = beanUtils.copyNonNullProperties(business, findBusiness);
    return businessRepository.save(updatingBusiness);
  }
}
