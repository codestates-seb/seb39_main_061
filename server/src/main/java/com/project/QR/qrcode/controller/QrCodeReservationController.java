package com.project.QR.qrcode.controller;

import com.project.QR.dto.SingleResponseWithMessageDto;
import com.project.QR.qrcode.dto.QrCodeRequestDto;
import com.project.QR.qrcode.entity.QrCode;
import com.project.QR.qrcode.mapper.QrCodeMapper;
import com.project.QR.qrcode.service.QrCodeService;
import com.project.QR.security.MemberDetails;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@Validated
@RestController
@RequestMapping("/api/v1/qr-code/reservation")
@AllArgsConstructor
public class QrCodeReservationController {

  private final QrCodeService qrCodeService;
  private final QrCodeMapper mapper;

  /**
   * QrCode 생성 api
   */

  @PostMapping
  public ResponseEntity createQrCode(@AuthenticationPrincipal MemberDetails memberDetails,
                                     @Valid @RequestPart(name = "data") QrCodeRequestDto.CreateQrCodeDto createQrCodeDto,
                                     @RequestPart(name = "file", required = false) MultipartFile multipartFile) {

    createQrCodeDto.setMemberId(memberDetails.getMember().getMemberId());
    QrCode qrCode = qrCodeService.createQrCode(mapper.createQrCodeDtoToQrCode(createQrCodeDto));

    return new ResponseEntity<>(new SingleResponseWithMessageDto<>(mapper.qrCodeToQrCodeInfoDto(qrCode), "CREATED"), HttpStatus.CREATED);
  }

  /**
   * QrCode 조회 api
   */
  @GetMapping("/{qr-code-id}")
  public ResponseEntity getQrCode(@Positive @PathVariable("member-id") long memberId,
                                  @Positive @PathVariable("qr-code-id")  long qrCodeId){


    QrCode qrCode = qrCodeService.getQrCode(memberId, qrCodeId);

    return new ResponseEntity<>(
            new SingleResponseWithMessageDto<>(mapper.qrCodeToQrCodeInfoDto(qrCode),"SUCCESS"),
            HttpStatus.OK);
  }

  /**
   * QrCode 변경 api
   */
  @PostMapping("/update")
  public ResponseEntity updateQrCode(@AuthenticationPrincipal MemberDetails memberDetails,
                                     @Positive @PathVariable("qr-code-id") long qrCodeId,
                                     @Valid @RequestPart(name = "data") QrCodeRequestDto.UpdateQrCodeDto updateQrCodeDto,
                                     @RequestPart(name = "file", required = false) MultipartFile multipartFile) {



    updateQrCodeDto.setQrCodeId(qrCodeId);
    updateQrCodeDto.setMemberId(memberDetails.getMember().getMemberId());
    QrCode qrCode = qrCodeService.updateQrCode(mapper.updateQrCodeDtoToQrCode(updateQrCodeDto));


    return new ResponseEntity<>(
            new SingleResponseWithMessageDto<>(mapper.qrCodeToQrCodeInfoDto(qrCode), "SUCCESS"),
            HttpStatus.OK);
  }

  /**
   * QrCode 삭제 api
   */
  @PostMapping("/delete")
  public ResponseEntity deleteQrCode(@AuthenticationPrincipal MemberDetails memberDetails,
                                     @Positive @PathVariable("qr-code-id") long qrCodeId) {

    qrCodeService.deleteQrCode(qrCodeId, memberDetails.getMember().getMemberId());

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}