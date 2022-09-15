package com.project.QR.sector.service;


import com.project.QR.exception.BusinessLogicException;
import com.project.QR.exception.ExceptionCode;
import com.project.QR.sector.entity.Sector;
import com.project.QR.sector.repository.SectorRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

  @Transactional(readOnly = true)
  public Sector getSector(long sectorId) {
    return findVerifiedSector(sectorId);
  }

  @Transactional(readOnly = true)
  public Sector findVerifiedSector(long sectorId) {
    Optional<Sector> optionalSector = sectorRepository.findById(sectorId);
    return optionalSector.orElseThrow(() -> new BusinessLogicException(ExceptionCode.SECTOR_NOT_FOUND));
  }
}
