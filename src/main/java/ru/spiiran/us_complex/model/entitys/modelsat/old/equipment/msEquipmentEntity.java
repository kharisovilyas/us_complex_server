package ru.spiiran.us_complex.model.entitys.modelsat.old.equipment;

import jakarta.persistence.*;
import ru.spiiran.us_complex.model.dto.message.dtoMessage;
import ru.spiiran.us_complex.model.entitys.general.IEntity;

@Entity
@Table(name = "ms_equipment")
public class msEquipmentEntity implements IEntity {
    @Id
    @Column(name = "id_equipment")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long equipmentId;

    @ManyToOne
    @JoinColumn(name = "id_equipment_type") // имя столбца для связи
    private msEquipmentTypeEntity typeEntity;

    @Column(name = "dev_name")
    private String devName;

    public msEquipmentEntity(msEquipmentTypeEntity typeEntity, String devName) {
        this.typeEntity = typeEntity;
        this.devName = devName;
    }


    public msEquipmentTypeEntity getTypeEntity() {
        return typeEntity;
    }

    public void setTypeEntity(msEquipmentTypeEntity typeEntity) {
        this.typeEntity = typeEntity;
    }

    public String getDevName() {
        return devName;
    }

    public void setDevName(String devName) {
        this.devName = devName;
    }

    @Override
    public dtoMessage getDtoMessage(String type, String message) {
        return new dtoMessage(type, message);
    }

    public msEquipmentEntity() {
    }

    public Long getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(Long equipmentId) {
        this.equipmentId = equipmentId;
    }
}
