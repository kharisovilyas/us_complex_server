package ru.spiiran.us_complex.model.entitys.modelsat.power;

import jakarta.persistence.*;
import ru.spiiran.us_complex.model.dto.message.dtoMessage;
import ru.spiiran.us_complex.model.dto.modelsat.power.dtoMode;
import ru.spiiran.us_complex.model.entitys.general.IEntity;

@Entity
@Table(name = "ms_mode")
public class msModeEntity implements IEntity {
    @Id
    @Column(name = "id_mode")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long modeId;
    @Column(name = "mode")
    private String mode;
    @ManyToOne
    @JoinColumn(name="id_power")
    private msPowerEntity msPowerEntity;

    public msModeEntity() {
    }

    public msModeEntity(dtoMode dto) {
        this.modeId = dto.getModeId();
        this.mode = dto.getMode();
    }

    public Long getModeId() {
        return modeId;
    }

    public void setModeId(Long modeId) {
        this.modeId = modeId;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public ru.spiiran.us_complex.model.entitys.modelsat.power.msPowerEntity getMsPowerEntity() {
        return msPowerEntity;
    }

    public void setMsPowerEntity(ru.spiiran.us_complex.model.entitys.modelsat.power.msPowerEntity msPowerEntity) {
        this.msPowerEntity = msPowerEntity;
    }

    @Override
    public dtoMessage getDtoMessage(String type, String message) {
        return new dtoMessage(type, message);
    }
}
