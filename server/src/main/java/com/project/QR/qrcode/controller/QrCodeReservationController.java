package com.project.QR.qrcode.controller;

import com.project.QR.dto.MultiResponseWithPageInfoDto;
import com.project.QR.dto.SingleResponseWithMessageDto;
import com.project.QR.qrcode.dto.QrCodeRequestDto;
import com.project.QR.qrcode.entity.QrCode;
import com.project.QR.qrcode.mapper.QrCodeMapper;
import com.project.QR.qrcode.service.QrCodeService;
import com.project.QR.security.MemberDetails;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.websocket.server.PathParam;
import java.util.List;

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
                                     @Valid @RequestBody QrCodeRequestDto.CreateQrCodeDto createQrCodeDto) {
    createQrCodeDto.setMemberId(memberDetails.getMember().getMemberId());
    QrCode qrCode = qrCodeService.createQrCode(mapper.createQrCodeDtoToQrCode(createQrCodeDto));

    return new ResponseEntity<>(new SingleResponseWithMessageDto<>(mapper.qrCodeToQrCodeInfoDto(qrCode),
      "CREATED"),
      HttpStatus.CREATED);
  }

  /**
   * 특정 QrCode 조회 api
   */
  @GetMapping("/{qr-code-id}")
  public ResponseEntity getQrCode(@Positive @PathVariable("member-id") long memberId,
                                  @Positive @PathVariable("qr-code-id")  long qrCodeId){
    QrCode qrCode = qrCodeService.getQrCode(qrCodeId, memberId);

    return new ResponseEntity<>(
            new SingleResponseWithMessageDto<>(mapper.qrCodeToQrCodeInfoDto(qrCode),"SUCCESS"),
            HttpStatus.OK);
  }

  /**
   * 전체 QrCode 리스트 조회 api
   */
  @GetMapping
  public ResponseEntity getQrCodes(@AuthenticationPrincipal MemberDetails memberDetails,
                                  @Positive @PathParam("page") int page,
                                  @Positive @PathParam("size") int size){
    Page<QrCode> pageOfQrCode = qrCodeService.getQrCodes( page - 1, size, memberDetails.getMember().getMemberId());
    List<QrCode> qrCodeList = pageOfQrCode.getContent();
    return new ResponseEntity<>(new MultiResponseWithPageInfoDto<>(mapper.qrCodeListToQrCodeInfoDtoList(qrCodeList),
            pageOfQrCode),
            HttpStatus.OK);
  }

  /**
   * QrCode 변경 api
   */
  @PostMapping(value = "{qr-code-id}/update", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
  public ResponseEntity updateQrCode(@AuthenticationPrincipal MemberDetails memberDetails,
                                     @Positive @PathVariable("qr-code-id") long qrCodeId,
                                     @Valid @RequestPart(name = "data") QrCodeRequestDto.UpdateQrCodeDto updateQrCodeDto,
                                     @RequestPart(name = "file", required = false) MultipartFile multipartFile) {
    updateQrCodeDto.setMemberId(memberDetails.getMember().getMemberId());
    updateQrCodeDto.setQrCodeId(qrCodeId);
    QrCode qrCode = qrCodeService.updateQrCode(mapper.updateQrCodeDtoToQrCode(updateQrCodeDto), multipartFile);

    return new ResponseEntity<>(new SingleResponseWithMessageDto<>(mapper.qrCodeToQrCodeInfoDto(qrCode),
      "SUCCESS"),
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