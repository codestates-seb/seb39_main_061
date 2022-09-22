package com.project.QR.keep.controller;

import com.project.QR.dto.MultiResponseWithMessageDto;
import com.project.QR.dto.SingleResponseWithMessageDto;
import com.project.QR.keep.entity.Keep;
import com.project.QR.keep.service.KeepService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.websocket.server.PathParam;
import java.util.List;


@Validated
@RestController
@RequestMapping("/keep/{qr-code-id}")
@AllArgsConstructor
public class KeepController {
  private final KeepService keepService;
  private final KeepMapper mapper;

  /**
   * 자재 등록 API
   */
  @PostMapping
  public ResponseEntity createKeep(@Valid @RequestBody KeepRequestDto.CreateKeepDto createKeepDto,
                                   @Positive @PathVariable("qr-code-id") long qrCodeId) {
    createKeepDto.setKeepId(keepId);
    Keep keep = keepService.createKeep(mapper.createKeepToKeep(createKeepDto));
    return new ResponseEntity(new SingleResponseWithMessageDto<>(mapper.keepToKeepInfoDto(keep),
            "CREATED"),
            HttpStatus.CREATED);
  }

  /**
   * 자재 조회 API
   */
  @GetMapping
  public ResponseEntity getKeep(@Positive @PathVariable("qr-code-id") long qrCodeId,
                                @Positive @PathParam("page") int page,
                                @Positive @PathParam("size") int size) {
    Page<Keep> pageOfKeep = keepService.getKeeps(keepId, page-1, size);
    List<Keep> keepList = pageOfKeep.getKeep();
    return new ResponseEntity(new MultiResponseWithMessageDto<>(
            mapper.keepListToKeepInfoDtoList(keepList),
            pageOfKeep
    ), HttpStatus.Ok);
  }

  /**
   * 자재 변경 API
   */
  @PatchMapping("/{keep-id}")
  public ResponseEntity updateKeep(@Positive @PathVariable("qr-code-id") long qrCodeId,
                                   @Positive @PathVariable("keep-id") long keepId,
                                   @Valid @RequestBody KeepRequestDto.UpdatdKeepdto updatekeepdto) {
    updateKeepDto.setKeepId(keepId);
    updateKeepDto.setQrCodeId(qrCodeId);
    Keep keep = keepService.updateKeep(mapper.updateKeepToKeep(updateKeepDto));
    return new ResponseEntity(new SingleResponseWithMessageDto<>(mapper.keepToKeepInfoDto(keep),
            "SUCCESS"),
            HttpStatus.OK);
  }

  /**
   * 자재 삭제
   */
  @PatchMapping("/delete/{keep-id}")
  public ResponseEntity deleteKeep(@Positive @PathVariable("qr-code-id") long qrCodeId,
                                   @Positive @PathVariable("keep-id") long keepId,
                                   @Valid @RequestBody KeepRequestDto.UpdateKeepDto updateKeepDto) {
    updatKeepDto.setKeepId(keepId);
    updateKeepDto.setQrCodeId(qrCodeId);
    keepService.deleteKeep(mapper.updateKeepToKeep(updateKeepDto));
    return new ResponseEntity(HttpStatus.NO_CONTENT);
  }
}
