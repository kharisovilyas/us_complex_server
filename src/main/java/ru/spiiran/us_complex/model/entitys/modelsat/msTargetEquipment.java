package ru.spiiran.us_complex.model.entitys.modelsat;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "target_equipment")
public class msTargetEquipment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_target_equipment")
    private Long targetEquipmentId;

    @OneToMany(mappedBy = "targetEquipment", cascade = CascadeType.ALL)
    private List<ModelSatEntity> modelSatEntity;
}
