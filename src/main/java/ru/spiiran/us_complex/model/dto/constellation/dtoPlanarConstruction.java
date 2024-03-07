package ru.spiiran.us_complex.model.dto.constellation;

import ru.spiiran.us_complex.model.entitys.constellation.coPlanarConstruction;

public class dtoPlanarConstruction {
    private String name;
    public dtoPlanarConstruction(coPlanarConstruction coPlanarConstruction){
        this.name = coPlanarConstruction.toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
