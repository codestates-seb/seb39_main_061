package com.project.QR.sector.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class Sector {
    @Id
    @GeneratedValue
    private long sectorId;

    @Column(length = 500)
    private String name;
}



