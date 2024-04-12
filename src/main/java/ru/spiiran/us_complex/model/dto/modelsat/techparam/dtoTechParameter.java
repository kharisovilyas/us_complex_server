package ru.spiiran.us_complex.model.dto.modelsat.techparam;

import ru.spiiran.us_complex.model.dto.IDTOEntity;

public class dtoTechParameter implements IDTOEntity {
    private Double valueOfPrm;
    private Long prmId;
    private Long idModelSat;

    public dtoTechParameter(){}

    public Long getIdModelSat() {
        return idModelSat;
    }

    public void setIdModelSat(Long idModelSat) {
        this.idModelSat = idModelSat;
    }

    public Double getValueOfPrm() {
        return valueOfPrm;
    }

    public void setValueOfPrm(Double valueOfPrm) {
        this.valueOfPrm = valueOfPrm;
    }

    public Long getPrmId() {
        return prmId;
    }

    public void setPrmId(Long prmId) {
        this.prmId = prmId;
    }
}
