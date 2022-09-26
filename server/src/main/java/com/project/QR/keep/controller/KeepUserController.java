package com.project.QR.keep.controller;

import com.project.QR.dto.MultiResponseWithPageInfoDto;
import com.project.QR.keep.entity.Keep;
import com.project.QR.keep.mapper.KeepMapper;
import com.project.QR.keep.service.KeepService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import javax.websocket.server.PathParam;
import java.util.List;

@Validated
@RestController
@RequestMapping("/business/{business-id}/keep/qr-code/{qr-code-id}")
@AllArgsConstructor
public class KeepUserController {
  private final KeepService keepService;
  private final KeepMapper mapper;

  /**
   * 자재 조회 API
   */
  @GetMapping
  public ResponseEntity getKeeps(@Positive @PathVariable("business-id") long businessId,
                                        @Positive @PathVariable("qr-code-id") long qrCodeId,
                                        @Positive @PathParam("page") int page,
                                        @Positive @PathParam("size") int size) {
    Page<Keep> pageOfKeep = keepService.getUserKeepList(businessId, qrCodeId,page - 1, size);
    List<Keep> keepList = pageOfKeep.getContent();

    return new ResponseEntity<>(new MultiResponseWithPageInfoDto<>(
            mapper.keepListToKeepInfoDtoList(keepList),
            pageOfKeep
    ), HttpStatus.OK);
  }
}
