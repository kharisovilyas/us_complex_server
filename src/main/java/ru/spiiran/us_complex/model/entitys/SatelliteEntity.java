package ru.spiiran.us_complex.model.entitys;

import jakarta.persistence.*;

@Entity
@Table(name = "satellite")
public class SatelliteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_node")
    private Long constellation;
    private Integer plane;
    private Integer position;
    private Double altitude;
    private Double eccentricity;
    private Double inclination;
    private Double longitude;
    private Double argument;
    private Double phase;

    // getters and setters
}
