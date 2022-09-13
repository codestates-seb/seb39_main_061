package com.project.QR.sector.service;

import com.project.QR.sector.repository.SectorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class SectorService {
  private final SectorRepository sectorRepository;
}
