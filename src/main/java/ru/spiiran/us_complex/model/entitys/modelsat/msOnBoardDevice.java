package ru.spiiran.us_complex.model.entitys.modelsat;

import jakarta.persistence.*;
@Entity
@Table(name = "on_board_devices")
public class msOnBoardDevice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_target_equipment")
    private Long targetEquipmentId;

    @OneToOne(mappedBy = "onBoardDevice")
    @JoinColumn(name = "id_type_device")
    private msTypeDevice typeDevice;

    @OneToOne(mappedBy = "onBoardDevice")
    @JoinColumn(name = "id_device")
    private msDevice device;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private ModelSatEntity modelSatEntity;
}
