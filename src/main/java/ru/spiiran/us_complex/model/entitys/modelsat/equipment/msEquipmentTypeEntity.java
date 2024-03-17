package ru.spiiran.us_complex.model.entitys.modelsat.equipment;

import jakarta.persistence.*;
import ru.spiiran.us_complex.model.dto.modelsat.equipment.dtoEquipmentType;

@Entity
@Table(name="ms_type_equipment")
public class msEquipmentTypeEntity {
    @Id
    @Column(name = "dev_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long devId;
    @ManyToOne
    @JoinColumn(name = "equipment_id")
    private msEquipmentEntity msEquipmentEntity;
    @Column(name = "type")
    private String type;

    public msEquipmentTypeEntity() {
    }

    public msEquipmentTypeEntity(dtoEquipmentType dto) {
        this.devId = dto.getDevId();
        this.type = dto.getType();
    }
}
