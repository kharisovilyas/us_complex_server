package ru.spiiran.us_complex.model.entitys.constellation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "co_planar_construction")
public class coPlanarConstruction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_satellite")
    private Long satelliteId;

    @ManyToOne
    @JoinColumn(name = "constellation_id")
    @JsonIgnore
    private ConstellationEntity constellation;

}
