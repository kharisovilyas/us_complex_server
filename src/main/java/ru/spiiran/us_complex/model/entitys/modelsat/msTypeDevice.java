package ru.spiiran.us_complex.model.entitys.modelsat;

import jakarta.persistence.*;

@Entity
@Table(name = "on_board_devices")
public class msTypeDevice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_target_equipment")
    private Long targetEquipmentId;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_on_board_device")
    private msOnBoardDevice onBoardDevice;
}
