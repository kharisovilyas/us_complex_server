package ru.spiiran.us_complex.model.dto.modelsat.equipment;

import ru.spiiran.us_complex.model.dto.IDTOEntity;
import ru.spiiran.us_complex.model.entitys.modelsat.equipment.msEquipmentEntity;

public class dtoEquipment implements IDTOEntity {
    private Long id;
    private String devName;
    private dtoEquipmentType type;

    public dtoEquipment() {
    }

    public dtoEquipment(msEquipmentEntity msEquipmentEntity) {
        this.devName = msEquipmentEntity.getDevName();
        this.id = msEquipmentEntity.getID();
        this.type = msEquipmentEntity.getTypeEntity().getDto();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDevName() {
        return devName;
    }

    public void setDevName(String devName) {
        this.devName = devName;
    }

    public dtoEquipmentType getType() {
        return type;
    }

    public void setType(dtoEquipmentType type) {
        this.type = type;
    }
}
