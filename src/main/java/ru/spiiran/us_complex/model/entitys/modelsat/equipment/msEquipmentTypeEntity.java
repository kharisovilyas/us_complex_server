package ru.spiiran.us_complex.model.entitys.modelsat.equipment;

import jakarta.persistence.*;
import ru.spiiran.us_complex.model.dto.message.dtoMessage;
import ru.spiiran.us_complex.model.dto.modelsat.equipment.dtoEquipmentType;
import ru.spiiran.us_complex.model.entitys.general.IEntity;

import java.util.List;

@Entity
@Table(name="ms_type_equipment")
public class msEquipmentTypeEntity implements IEntity {
    @Id
    @Column(name = "id_dev")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long devId;
    @OneToMany(mappedBy = "typeEntity")
    private List<msEquipmentEntity> equipments;
    @Column(name = "type")
    private String type;

    public Long getDevId() {
        return devId;
    }

    public void setDevId(Long devId) {
        this.devId = devId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public msEquipmentTypeEntity() {
    }

    public msEquipmentTypeEntity(dtoEquipmentType dto) {
        this.devId = dto.getDevId();
        this.type = dto.getType();
    }

    @Override
    public dtoMessage getDtoMessage(String type, String message) {
        return null;
    }

    public List<msEquipmentEntity> getEquipments() {
        return equipments;
    }

    public void setEquipments(List<msEquipmentEntity> equipments) {
        this.equipments = equipments;
    }

    public dtoEquipmentType getDto() {
        return new dtoEquipmentType(this);
    }
}
