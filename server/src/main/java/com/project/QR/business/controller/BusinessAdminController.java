package com.project.QR.business.controller;

import com.project.QR.business.dto.BusinessRequestDto;
import com.project.QR.business.entity.Business;
import com.project.QR.business.mapper.BusinessMapper;
import com.project.QR.business.service.BusinessService;
import com.project.QR.dto.SingleResponseWithMessageDto;
import com.project.QR.security.MemberDetails;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/api/v1/business")
@AllArgsConstructor
public class BusinessAdminController {
  private final BusinessService businessService;
  private final BusinessMapper mapper;

  /**
   * 매장 정보 변경 API
   */
  @PatchMapping("/{business-id}")
  public ResponseEntity updateBusiness(@AuthenticationPrincipal MemberDetails memberDetails,
                                       @Valid @RequestBody BusinessRequestDto.UpdateBusinessDto updateBusinessDto,
                                       @Positive @PathVariable("business-id") long businessId) {
    updateBusinessDto.setBusinessId(businessId);
    updateBusinessDto.setMemberId(memberDetails.getMember().getMemberId());
    Business business = businessService.updateBusiness(mapper.updateBusinessDtoToBusiness(updateBusinessDto));

    return new ResponseEntity<>(new SingleResponseWithMessageDto<>(mapper.businessToBusinessInfoDto(business),
      "SUCCESS"),
      HttpStatus.OK);
  }

  /**
   * 매장 정보 조회 API
   */
  @GetMapping
  public ResponseEntity getBusiness(@AuthenticationPrincipal MemberDetails memberDetails) {
    Business business = businessService.getBusinessByMemberId(memberDetails.getMember().getMemberId());

    return new ResponseEntity<>(new SingleResponseWithMessageDto<>(mapper.businessToBusinessInfoDto(business),
      "SUCCESS"),
      HttpStatus.OK);
  }
}
