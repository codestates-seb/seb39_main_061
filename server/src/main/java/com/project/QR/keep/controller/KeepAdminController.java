package com.project.QR.keep.controller;

import com.project.QR.dto.MultiResponseWithPageInfoDto;
import com.project.QR.dto.SingleResponseWithMessageDto;
import com.project.QR.keep.dto.KeepRequestDto;
import com.project.QR.keep.entity.Keep;
import com.project.QR.keep.mapper.KeepMapper;
import com.project.QR.keep.service.KeepService;
import com.project.QR.security.MemberDetails;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.websocket.server.PathParam;
import java.util.List;


@Validated
@RestController
@RequestMapping("/api/v1/business/{business-id}/type/keep/qr-code/{qr-code-id}/info")
@AllArgsConstructor
public class KeepAdminController {
  private final KeepService keepService;
  private final KeepMapper mapper;

  /**
   * 자재 등록
   */
  @PostMapping
  public ResponseEntity createKeep(@AuthenticationPrincipal MemberDetails memberDetails,
                                   @Valid @RequestBody KeepRequestDto.CreateKeepDto createKeepDto,
                                   @Positive @PathVariable("business-id") long businessId,
                                   @Positive @PathVariable("qr-code-id") long qrCodeId) {
    createKeepDto.setBusinessId(businessId);
    createKeepDto.setQrCodeId(qrCodeId);
    Keep keep = keepService.createKeep(mapper.createKeepDtoToKeep(createKeepDto));

    return new ResponseEntity<>(new SingleResponseWithMessageDto<>(mapper.keepToKeepInfoDto(keep),
            "CREATED"),
            HttpStatus.CREATED);
  }

  /**
   * 자재 조회
   */
  @GetMapping
  public ResponseEntity getKeep(@AuthenticationPrincipal MemberDetails memberDetails,
                                @Positive @PathVariable("business-id") long businessId,
                                @Positive @PathVariable("qr-code-id") long qrCodeId,
                                @Positive @PathParam("page") int page,
                                @Positive @PathParam("size") int size) {
    Page<Keep> pageOfKeep = keepService.getAdminKeepList(businessId, qrCodeId,
            memberDetails.getMember().getMemberId(), page-1, size);
    List<Keep> keepList = pageOfKeep.getContent();

    return new ResponseEntity<>(new MultiResponseWithPageInfoDto<>(
            mapper.keepListToKeepInfoDtoList(keepList), pageOfKeep),
            HttpStatus.OK);
  }

  /**
   * 자재 변경
   */
  @PatchMapping("/{keep-id}")
  public ResponseEntity updateKeep(@AuthenticationPrincipal MemberDetails memberDetails,
                                   @Positive @PathVariable("business-id") long businessId,
                                   @Positive @PathVariable("qr-code-id") long qrCodeId,
                                   @Positive @PathVariable("keep-id") long keepId,
                                   @Valid @RequestBody KeepRequestDto.UpdateKeepDto updateKeepDto) {
    updateKeepDto.setBusinessId(businessId);
    updateKeepDto.setKeepId(keepId);
    updateKeepDto.setQrCodeId(qrCodeId);
    Keep keep = keepService.updateKeep(mapper.updateKeepDtoToKeep(updateKeepDto));

    return new ResponseEntity<>(new SingleResponseWithMessageDto<>(mapper.keepToKeepInfoDto(keep),
            "SUCCESS"),
            HttpStatus.OK);
  }

  /**
   * 자재 삭제
   */
  @PatchMapping("/{keep-id}/delete")
  public ResponseEntity deleteKeep(@AuthenticationPrincipal MemberDetails memberDetails,
                                   @Positive @PathVariable("business-id") long businessId,
                                   @Positive @PathVariable("qr-code-id") long qrCodeId,
                                   @Positive @PathVariable("keep-id") long keepId,
                                   @Valid @RequestBody KeepRequestDto.UpdateKeepDto updateKeepDto) {

    updateKeepDto.setBusinessId(businessId);
    updateKeepDto.setQrCodeId(qrCodeId);
    updateKeepDto.setKeepId(keepId);
    keepService.deleteKeep(mapper.updateKeepDtoToKeep(updateKeepDto));

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
