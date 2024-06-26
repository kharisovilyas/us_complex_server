package ru.spiiran.us_complex.model.dto.modelsat.old.equipment;

import ru.spiiran.us_complex.model.dto.IDTOEntity;
import ru.spiiran.us_complex.model.entitys.modelsat.old.equipment.msEquipmentTypeEntity;

public class dtoEquipmentType implements IDTOEntity {
    private Long devId;
    private String type;

    public dtoEquipmentType(msEquipmentTypeEntity msEquipmentTypeEntity) {
        this.devId = msEquipmentTypeEntity.getDevId();
        this.type = msEquipmentTypeEntity.getType();
    }

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

    public dtoEquipmentType() {}
}
