package ru.spiiran.us_complex.model.entitys.modelsat.equipment;

import jakarta.persistence.*;
import ru.spiiran.us_complex.model.dto.message.dtoMessage;
import ru.spiiran.us_complex.model.entitys.general.IEntity;

@Entity
@Table(name = "ms_equipment")
public class msEquipmentEntity implements IEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @ManyToOne
    @JoinColumn(name = "equipment_type_id") // имя столбца для связи
    private msEquipmentTypeEntity typeEntity;

    @Column(name = "dev_name")
    private String devName;

    public msEquipmentEntity(msEquipmentTypeEntity typeEntity, String devName) {
        this.typeEntity = typeEntity;
        this.devName = devName;
    }

    public void setID(Long devID) {
        this.ID = devID;
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
    public Long getID() {
        return ID;
    }

    @Override
    public dtoMessage getDtoMessage(String type, String message) {
        return new dtoMessage(type, message);
    }

    public msEquipmentEntity() {
    }
}
