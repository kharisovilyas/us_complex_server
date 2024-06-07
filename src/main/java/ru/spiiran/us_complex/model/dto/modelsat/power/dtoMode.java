package ru.spiiran.us_complex.model.dto.modelsat.power;

import ru.spiiran.us_complex.model.dto.IDTOEntity;
import ru.spiiran.us_complex.model.entitys.modelsat.power.msModeEntity;

public class dtoMode implements IDTOEntity {
    private Long modeId;
    private String mode;

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

    public dtoMode() {}

    public dtoMode(msModeEntity msModeEntity) {
        this.mode = msModeEntity.getMode();
        this.modeId = msModeEntity.getModeId();
    }
}
