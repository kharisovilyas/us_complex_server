package ru.spiiran.us_complex.model.dto;

import java.util.List;

public class dtoListOfEarthPoint implements IDTOEntity {
    private List<dtoEarthPoint> dtoEarthPointList;

    public List<dtoEarthPoint> getDtoEarthPointList() {
        return dtoEarthPointList;
    }

    public void setDtoEarthPointList(List<dtoEarthPoint> dtoEarthPointList) {
        this.dtoEarthPointList = dtoEarthPointList;
    }

    public void setStatusOfEditInAll(Boolean statusOfEdit){

    }
}
