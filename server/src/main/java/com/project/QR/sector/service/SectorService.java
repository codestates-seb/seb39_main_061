package com.project.QR.sector.service;


import com.project.QR.sector.entity.Sector;
import com.project.QR.sector.repository.SectorRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class SectorService {
  private final SectorRepository sectorRepository;

  /**
   * 전체 업종 조회
   */
  @Transactional(readOnly = true)
  public List<Sector> getSectors() {
    return sectorRepository.findAll(Sort.by("sectorId").ascending());
  }
}
