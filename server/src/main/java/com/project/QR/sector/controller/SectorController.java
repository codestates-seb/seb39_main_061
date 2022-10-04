package com.project.QR.sector.controller;

import com.project.QR.dto.MultiResponseWithMessageDto;
import com.project.QR.sector.service.SectorService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/sectors")
@AllArgsConstructor
public class SectorController {
  private final SectorService sectorService;

  /**
   * 업종 전체 조회 api
   */
  @GetMapping
  public ResponseEntity getSectors() {
    return new ResponseEntity(new MultiResponseWithMessageDto<>(sectorService.getSectors(),
      "SUCCESS"),
      HttpStatus.OK);
  }
}
