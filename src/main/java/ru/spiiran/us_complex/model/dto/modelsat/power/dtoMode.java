package ru.spiiran.us_complex.model.dto.modelsat.power;

import ru.spiiran.us_complex.model.dto.IDTOEntity;

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
}
