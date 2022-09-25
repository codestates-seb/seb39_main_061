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

@RestController
@RequestMapping("/api/v1/business")
@AllArgsConstructor
public class BusinessAdminController {
  private final BusinessService businessService;
  private final BusinessMapper mapper;

  /**
   * 매장 정보 변경 API
   */
  @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
  public ResponseEntity updateBusiness(@AuthenticationPrincipal MemberDetails memberDetails,
                                       @Valid @RequestPart("data") BusinessRequestDto.UpdateBusinessDto updateBusinessDto,
                                       @RequestPart(value = "file", required = false) MultipartFile multipartFile) {
    updateBusinessDto.setMemberId(memberDetails.getMember().getMemberId());
    Business business = businessService.updateBusiness(mapper.updateBusinessDtoToBusiness(updateBusinessDto), multipartFile);

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
