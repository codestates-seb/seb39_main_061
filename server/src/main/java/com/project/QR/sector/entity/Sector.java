package com.project.QR.sector.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Sector {
  @Id
  @GeneratedValue
  private long sectorId;

  @Column(length = 500)
  private String name;
}
