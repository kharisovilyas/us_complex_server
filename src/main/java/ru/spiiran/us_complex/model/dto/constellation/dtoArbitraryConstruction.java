package ru.spiiran.us_complex.model.dto.constellation;

import ru.spiiran.us_complex.model.dto.IDTOEntity;
import ru.spiiran.us_complex.model.dto.modelsat.equipment.dtoEquipmentType;
import ru.spiiran.us_complex.model.entitys.constellation.coArbitraryConstruction;

public class dtoArbitraryConstruction implements IDTOEntity {
    private Long id;
    private Long idNode;
    private Integer altitude;
    private Double eccentricity;
    private Double incline;
    private Integer longitudeAscendingNode;
    private Double perigeeWidthArgument;
    private Double trueAnomaly;
    private Boolean isDeleted;
    private Long modelSat;


    public dtoArbitraryConstruction(){}

    public dtoArbitraryConstruction(coArbitraryConstruction coArbitraryConstruction) {
        this.id = coArbitraryConstruction.getID();
        this.modelSat = coArbitraryConstruction.getModelSat();
        this.altitude = coArbitraryConstruction.getAltitude();
        this.eccentricity = coArbitraryConstruction.getEccentricity();
        this.incline = coArbitraryConstruction.getIncline();
        this.longitudeAscendingNode = coArbitraryConstruction.getLongitudeAscendingNode();
        this.perigeeWidthArgument = coArbitraryConstruction.getPerigeeWidthArgument();
        this.trueAnomaly = coArbitraryConstruction.getTrueAnomaly();
        this.idNode = coArbitraryConstruction.getArbitraryConstructionIdNode();
    }

    public Long getID() {
        return id;
    }

    public void setID(Long id) {
        this.id = id;
    }

    public Long getIdNode() {
        return idNode;
    }

    public void setIdNode(Long idNode) {
        this.idNode = idNode;
    }

    public Integer getAltitude() {
        return altitude;
    }

    public void setAltitude(Integer altitude) {
        this.altitude = altitude;
    }

    public Double getEccentricity() {
        return eccentricity;
    }

    public void setEccentricity(Double eccentricity) {
        this.eccentricity = eccentricity;
    }

    public Double getIncline() {
        return incline;
    }

    public void setIncline(Double incline) {
        this.incline = incline;
    }

    public Integer getLongitudeAscendingNode() {
        return longitudeAscendingNode;
    }

    public void setLongitudeAscendingNode(Integer longitudeAscendingNode) {
        this.longitudeAscendingNode = longitudeAscendingNode;
    }

    public Double getPerigeeWidthArgument() {
        return perigeeWidthArgument;
    }

    public void setPerigeeWidthArgument(Double perigeeWidthArgument) {
        this.perigeeWidthArgument = perigeeWidthArgument;
    }

    public Double getTrueAnomaly() {
        return trueAnomaly;
    }

    public void setTrueAnomaly(Double trueAnomaly) {
        this.trueAnomaly = trueAnomaly;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Long getModelSat() {
        return modelSat;
    }

    public void setModelSat(Long modelSat) {
        this.modelSat = modelSat;
    }
}
