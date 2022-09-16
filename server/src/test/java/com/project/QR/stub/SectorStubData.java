package com.project.QR.stub;

import com.project.QR.sector.entity.Sector;

import java.util.ArrayList;
import java.util.List;

public class SectorStubData {
  public static Sector sector(long sectorId, String name) {
    Sector sector = new Sector();
    sector.setSectorId(sectorId);
    sector.setName(name);
    return sector;
  }

  public static List<Sector> sectorList() {
    List<Sector> sectorList = new ArrayList<>();
    for(int i = 1; i <= 15; i++) {
      sectorList.add(sector(i, String.format("업종%d", i)));
    }
    return sectorList;
  }
}
