package ru.spiiran.us_complex.model.dto.modelsat.techparam;

import ru.spiiran.us_complex.model.dto.IDTOEntity;

public class dtoParameter implements IDTOEntity {
    private Long prmId;
    private String parameter;

    public Long getPrmId() {
        return prmId;
    }

    public void setPrmId(Long prmId) {
        this.prmId = prmId;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public dtoParameter() {}

}
