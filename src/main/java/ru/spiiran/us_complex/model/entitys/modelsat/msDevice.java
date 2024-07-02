package ru.spiiran.us_complex.model.entitys.modelsat;

import jakarta.persistence.*;

@Entity
@Table(name = "device")
public class msDevice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_device")
    private Long deviceId;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_type_device")
    private msOnBoardDevice onBoardDevice;
}
