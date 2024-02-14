package ru.spiiran.us_complex.model.entitys;

import jakarta.persistence.*;

@Entity
@Table(name = "earth_sat")
public class EarthSatEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @Column(name = "id_node")
    private Long idNode;

    @Column(name = "satellite_id")
    private Long satelliteId;

    @Column(name = "begin_time")
    private String beginTime;

    @Column(name = "end_time")
    private String endTime;

}
