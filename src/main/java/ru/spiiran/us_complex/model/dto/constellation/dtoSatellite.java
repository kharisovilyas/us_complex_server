package ru.spiiran.us_complex.model.dto.constellation;

import ru.spiiran.us_complex.model.entitys.constellation.SatelliteEntity;

public class dtoSatellite {
    private Long satelliteId;
    private Double altitude;
    private Double eccentricity;
    private Double incline;
    private Double longitudeAscendingNode;
    private Double perigeeWidthArgument;
    private Double trueAnomaly;
    private Double phaseShift;
    private Long position;
    private Long plane;
    private Boolean isDeleted;

    public dtoSatellite(SatelliteEntity satelliteEntity) {
        this.satelliteId = satelliteEntity.getSatelliteId();
        this.altitude = satelliteEntity.getAltitude();
        this.eccentricity = satelliteEntity.getEccentricity();
        this.incline = satelliteEntity.getIncline();
        this.longitudeAscendingNode = satelliteEntity.getLongitudeAscendingNode();
        this.perigeeWidthArgument = satelliteEntity.getPerigeeWidthArgument();
        this.trueAnomaly = satelliteEntity.getTrueAnomaly();
        this.phaseShift = satelliteEntity.getPhaseShift();
        this.position = satelliteEntity.getPosition();
        this.plane = satelliteEntity.getPlane();
    }

    public dtoSatellite() {
    }

    public Long getSatelliteId() {
        return satelliteId;
    }

    public void setSatelliteId(Long satelliteId) {
        this.satelliteId = satelliteId;
    }

    public Double getAltitude() {
        return altitude;
    }

    public void setAltitude(Double altitude) {
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

    public Double getLongitudeAscendingNode() {
        return longitudeAscendingNode;
    }

    public void setLongitudeAscendingNode(Double longitudeAscendingNode) {
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

    public Double getPhaseShift() {
        return phaseShift;
    }

    public void setPhaseShift(Double phaseShift) {
        this.phaseShift = phaseShift;
    }

    public Long getPosition() {
        return position;
    }

    public void setPosition(Long position) {
        this.position = position;
    }

    public Long getPlane() {
        return plane;
    }

    public void setPlane(Long plane) {
        this.plane = plane;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }
}
