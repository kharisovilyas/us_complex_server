package ru.spiiran.us_complex.model.dto.constellation;

import ru.spiiran.us_complex.model.dto.IDTOEntity;
import ru.spiiran.us_complex.model.entitys.constellation.ConstellationEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class dtoConstellation implements IDTOEntity {
    private Long ID;
    private String constellationName;
    private Boolean isArbitraryFormation;
    private List<dtoArbitraryConstruction> arbitraryConstructions;
    private List<dtoPlanarConstruction> planarConstructions;
    private Boolean isDeleted;

    public dtoConstellation(){}

    public dtoConstellation(ConstellationEntity constellationEntity) {
        this.constellationName = constellationEntity.getConstellationName();
        this.ID = constellationEntity.getID();
        this.isArbitraryFormation = constellationEntity.getArbitraryFormation();
        this.arbitraryConstructions = constellationEntity
                .getArbitraryConstructionList()
                .stream()
                .map(dtoArbitraryConstruction::new) // Преобразование каждого элемента типа Arbitrary Construction в dtoArbitraryConstruction
                .collect(Collectors.toList());
        this.planarConstructions = new ArrayList<>();
    }
    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getConstellationName() {
        return constellationName;
    }

    public void setConstellationName(String constellationName) {
        this.constellationName = constellationName;
    }

    public List<dtoArbitraryConstruction> getArbitraryConstructions() {
        return arbitraryConstructions;
    }

    public void setArbitraryConstructions(List<dtoArbitraryConstruction> arbitraryConstructions) {
        this.arbitraryConstructions = arbitraryConstructions;
    }

    public List<dtoPlanarConstruction> getPlanarConstructions() {
        return planarConstructions;
    }

    public void setPlanarConstructions(List<dtoPlanarConstruction> planarConstructions) {
        this.planarConstructions = planarConstructions;
    }

    public Boolean getArbitraryFormation() {
        return isArbitraryFormation;
    }

    public void setArbitraryFormation(Boolean arbitraryFormation) {
        isArbitraryFormation = arbitraryFormation;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }


}
