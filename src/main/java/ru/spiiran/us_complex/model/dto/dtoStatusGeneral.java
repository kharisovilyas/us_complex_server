package ru.spiiran.us_complex.model.dto;

public class dtoStatusGeneral implements IDTOEntity{

    private Boolean statusOfEdit;

    public Boolean getStatusOfEdit() {
        return statusOfEdit;
    }

    public void setStatusOfEdit(Boolean statusOfEdit) {
        this.statusOfEdit = statusOfEdit;
    }

    public dtoStatusGeneral(){}

}
