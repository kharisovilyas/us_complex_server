package ru.spiiran.us_complex.model.dto;

import ru.spiiran.us_complex.model.entitys.general.generalStatusEntity;

public class dtoStatusGeneral implements IDTOEntity{

    private Boolean statusOfEdit;

    public Boolean getStatusOfEdit() {
        return statusOfEdit;
    }

    public void setStatusOfEdit(Boolean statusOfEdit) {
        this.statusOfEdit = statusOfEdit;
    }

    public dtoStatusGeneral(){}

    public dtoStatusGeneral(generalStatusEntity generalStatusEntity){
        this.statusOfEdit = generalStatusEntity.getStatusOfEdit();
    }

}
