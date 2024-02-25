package ru.spiiran.us_complex.model.dto.constellation;

import ru.spiiran.us_complex.model.dto.IDTOEntity;
import ru.spiiran.us_complex.model.entitys.ConstellationEntity;
import ru.spiiran.us_complex.model.entitys.ConstellationPlanar;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class dtoConstellation implements IDTOEntity {
    private Long ID;
    private String constellationName;
    private Boolean isArbitraryFormation;
    private Long modelSat;
    private List<dtoConstellationArbitrary> constellationArbitraryList;
    private List<dtoConstellationPlanar> constellationPlanarList;

    public dtoConstellation(String constellationName, String typeOfConstellation, Long modelSat, List<dtoConstellationArbitrary> constellationArbitraryList, List<dtoConstellationPlanar> constellationOverviewList) {
        this.constellationName = constellationName;
        this.modelSat = modelSat;
        this.constellationArbitraryList = constellationArbitraryList;
        this.constellationPlanarList = constellationOverviewList;
    }

    public dtoConstellation(){}

    public dtoConstellation(ConstellationEntity constellationEntity) {
        this.constellationName = constellationEntity.getConstellationName();
        this.modelSat = constellationEntity.getModelSat();
        this.ID = constellationEntity.getID();
        this.isArbitraryFormation = constellationEntity.getArbitraryFormation();
        this.constellationArbitraryList = constellationEntity
                .getConstellationArbitraryList()
                .stream()
                .map(dtoConstellationArbitrary::new) // Преобразование каждого элемента типа ConstellationArbitrary в dtoConstellationArbitrary
                .collect(Collectors.toList());
        this.constellationPlanarList = new ArrayList<>();
    }

    private dtoConstellationArbitrary mapToDto(ConstellationPlanar constellationPlanar) {
        return null;
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

    public List<dtoConstellationArbitrary> getConstellationArbitraryList() {
        return constellationArbitraryList;
    }

    public void setConstellationArbitraryList(List<dtoConstellationArbitrary> constellationArbitraryList) {
        this.constellationArbitraryList = constellationArbitraryList;
    }

    public List<dtoConstellationPlanar> getConstellationPlanarList() {
        return constellationPlanarList;
    }

    public void setConstellationPlanarList(List<dtoConstellationPlanar> constellationOverviewList) {
        this.constellationPlanarList = constellationOverviewList;
    }

    public Boolean getArbitraryFormation() {
        return isArbitraryFormation;
    }

    public void setArbitraryFormation(Boolean arbitraryFormation) {
        isArbitraryFormation = arbitraryFormation;
    }

    public Long getModelSat() {
        return modelSat;
    }

    public void setModelSat(Long modelSat) {
        this.modelSat = modelSat;
    }

}
