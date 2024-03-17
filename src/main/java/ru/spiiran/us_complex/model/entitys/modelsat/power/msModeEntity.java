package ru.spiiran.us_complex.model.entitys.modelsat.power;

import jakarta.persistence.*;
import ru.spiiran.us_complex.model.dto.message.dtoMessage;
import ru.spiiran.us_complex.model.dto.modelsat.power.dtoMode;
import ru.spiiran.us_complex.model.entitys.general.IEntity;

@Entity
@Table(name = "ms_mode")
public class msModeEntity implements IEntity {
    @Id
    @Column(name = "mode_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long modeID;
    @Column(name = "mode")
    private String mode;
    @ManyToOne
    @JoinColumn(name="power_id")
    private msPowerEntity msPowerEntity;

    public msModeEntity() {
    }

    public msModeEntity(dtoMode dto) {
        this.modeID = dto.getModeId();
        this.mode = dto.getMode();
    }

    @Override
    public Long getID() {
        return modeID;
    }

    @Override
    public dtoMessage getDtoMessage(String type, String message) {
        return new dtoMessage(type, message);
    }
}
