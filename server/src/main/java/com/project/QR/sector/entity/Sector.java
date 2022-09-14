package com.project.QR.sector.entity;

import com.project.QR.audit.Auditable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Sector extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sectorId;

    @Column(length = 200, nullable = false)
    private String name;

    @OneToMany(mappedBy = "sector")
    private List<Sector> sectors = new ArrayList<>();

    public Sector(Long sectorId, String name) {
        this.sectorId = sectorId;
        this.name = name;
    }
}
