package com.project.QR.sector.controller;

import com.project.QR.sector.service.SectorService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class SectorController {
  private final SectorService service;

}
