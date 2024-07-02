package ru.spiiran.us_complex.model.entitys.modelsat.old.power;

import jakarta.persistence.*;
import ru.spiiran.us_complex.model.dto.message.dtoMessage;
import ru.spiiran.us_complex.model.entitys.general.IEntity;

import java.util.List;
@Entity
@Table(name="ms_power")
public class msPowerEntity implements IEntity {
    @Id
    @Column(name = "id_power")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long powerId;
    @OneToMany(mappedBy = "msPowerEntity")
    private List<msModeEntity> modeEntityList;
    @Column(name = "value_of_power")
    private Double valueOfPower;

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
    public dtoMessage getDtoMessage(String type, String message) {
        return new dtoMessage(type, message);
    }

    public Long getPowerId() {
        return powerId;
    }

    public void setPowerId(Long powerId) {
        this.powerId = powerId;
    }
}
