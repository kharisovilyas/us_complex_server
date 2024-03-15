package ru.spiiran.us_complex.model.entitys.modelsat.equipment;

import jakarta.persistence.*;
import ru.spiiran.us_complex.model.dto.message.dtoMessage;
import ru.spiiran.us_complex.model.entitys.general.IEntity;

import java.util.List;

@Entity
@Table(name = "ms_equipment")
public class msEquipmentEntity implements IEntity {
    @Id
    @Column(name = "dev_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long devID;

    @OneToMany(mappedBy = "msEquipmentEntity")
    private List<msEquipmentTypeEntity> equipmentTypeEntityList;

    @Column(name = "dev_name")
    private String devName;

    public void setID(Long devID) {
        this.devID = devID;
    }

    public List<msEquipmentTypeEntity> getEquipmentTypeEntityList() {
        return equipmentTypeEntityList;
    }

    public void setEquipmentTypeEntityList(List<msEquipmentTypeEntity> equipmentTypeEntityList) {
        this.equipmentTypeEntityList = equipmentTypeEntityList;
    }

    public String getDevDescription() {
        return devName;
    }

    public void setDevDescription(String devDescription) {
        this.devName = devDescription;
    }

    @Override
    public Long getID() {
        return devID;
    }

    @Override
    public dtoMessage getDtoMessage(String type, String message) {
        return new dtoMessage(type, message);
    }
}
