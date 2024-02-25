package ru.spiiran.us_complex.model.dto.constellation;

import ru.spiiran.us_complex.model.entitys.ConstellationPlanar;

public class dtoConstellationPlanar {
    private String name;
    public dtoConstellationPlanar(ConstellationPlanar constellationPlanar){
        this.name = constellationPlanar.toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
