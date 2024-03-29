package ru.spiiran.us_complex.model.entitys.modelsat.power;

import jakarta.persistence.*;
import ru.spiiran.us_complex.model.dto.message.dtoMessage;
import ru.spiiran.us_complex.model.entitys.general.IEntity;
import ru.spiiran.us_complex.model.entitys.modelsat.ModelSatEntity;

import java.util.List;
@Entity
@Table(name="ms_power")
public class msPowerEntity implements IEntity {
    @Id
    @Column(name = "power_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long powerID;
    @OneToMany(mappedBy = "msPowerEntity")
    private List<ModelSatEntity> modelSatEntityList;
    @OneToMany(mappedBy = "msPowerEntity")
    private List<msModeEntity> modeEntityList;
    @Column(name = "value_of_power")
    private Double valueOfPower;

    public void setPowerID(Long powerID) {
        this.powerID = powerID;
    }

    public List<ModelSatEntity> getModelSatEntityList() {
        return modelSatEntityList;
    }

    public void setModelSatEntityList(List<ModelSatEntity> modelSatEntityList) {
        this.modelSatEntityList = modelSatEntityList;
    }

    public List<msModeEntity> getModeEntityList() {
        return modeEntityList;
    }

    public void setModeEntityList(List<msModeEntity> modeEntityList) {
        this.modeEntityList = modeEntityList;
    }

    public Double getValueOfPower() {
        return valueOfPower;
    }

    public void setValueOfPower(Double valueOfPower) {
        this.valueOfPower = valueOfPower;
    }

    @Override
    public Long getID() {
        return powerID;
    }

    @Override
    public dtoMessage getDtoMessage(String type, String message) {
        return new dtoMessage(type, message);
    }
}
