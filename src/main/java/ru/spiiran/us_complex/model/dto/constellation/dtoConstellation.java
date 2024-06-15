package ru.spiiran.us_complex.model.dto.constellation;

import ru.spiiran.us_complex.model.dto.IDTOEntity;
import ru.spiiran.us_complex.model.entitys.constellation.ConstellationEntity;

import java.util.List;
import java.util.stream.Collectors;

public class dtoConstellation implements IDTOEntity {
    private Long ID;
    private String constellationName;
    private Boolean isArbitraryFormation;
    private List<dtoSatellite> satellites;

    public dtoConstellation(){}

    public dtoConstellation(ConstellationEntity constellationEntity) {
        this.constellationName = constellationEntity.getConstellationName();
        this.ID = constellationEntity.getConstellationId();
        this.isArbitraryFormation = constellationEntity.getArbitraryFormation();
        this.satellites = constellationEntity
                .getSatelliteEntities()
                .stream()
                .map(dtoSatellite::new)
                .collect(Collectors.toList());
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

    public Boolean getArbitraryFormation() {
        return isArbitraryFormation;
    }

    public void setArbitraryFormation(Boolean arbitraryFormation) {
        isArbitraryFormation = arbitraryFormation;
    }

    public List<dtoSatellite> getSatellites() {
        return satellites;
    }

    public void setSatellites(List<dtoSatellite> satellites) {
        this.satellites = satellites;
    }
}
