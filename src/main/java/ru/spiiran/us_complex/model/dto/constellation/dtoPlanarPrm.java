package ru.spiiran.us_complex.model.dto.constellation;

import ru.spiiran.us_complex.model.dto.IDTOEntity;

public class dtoPlanarPrm implements IDTOEntity {
    private Long numberOfPlane;
    private Long positionPlane;
    private Double altitude;
    private Double eccentricity;
    private Double incline;
    private Double longitudeOfPlane1;
    private Double spacecraftOfLongitude;
    private Double perigeeWidthArgument;
    private Long firstPositionInPlane1;
    private Double spacecraftSpacing;
    private Double phaseShift;

    public dtoPlanarPrm() {}

    public Long getNumberOfPlane() {
        return numberOfPlane;
    }

    public void setNumberOfPlane(Long numberOfPlane) {
        this.numberOfPlane = numberOfPlane;
    }

    public Long getPositionPlane() {
        return positionPlane;
    }

    public void setPositionPlane(Long positionPlane) {
        this.positionPlane = positionPlane;
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

    public Double getLongitudeOfPlane1() {
        return longitudeOfPlane1;
    }

    public void setLongitudeOfPlane1(Double longitudeOfPlane1) {
        this.longitudeOfPlane1 = longitudeOfPlane1;
    }

    public Double getPerigeeWidthArgument() {
        return perigeeWidthArgument;
    }

    public void setPerigeeWidthArgument(Double perigeeWidthArgument) {
        this.perigeeWidthArgument = perigeeWidthArgument;
    }

    public Long getFirstPositionInPlane1() {
        return firstPositionInPlane1;
    }

    public void setFirstPositionInPlane1(Long firstPositionInPlane1) {
        this.firstPositionInPlane1 = firstPositionInPlane1;
    }

    public Double getPhaseShift() {
        return phaseShift;
    }

    public void setPhaseShift(Double phaseShift) {
        this.phaseShift = phaseShift;
    }

    public Double getSpacecraftOfLongitude() {
        return spacecraftOfLongitude;
    }

    public void setSpacecraftOfLongitude(Double spacecraftOfLongitude) {
        this.spacecraftOfLongitude = spacecraftOfLongitude;
    }

    public Double getSpacecraftSpacing() {
        return spacecraftSpacing;
    }

    public void setSpacecraftSpacing(Double spacecraftSpacing) {
        this.spacecraftSpacing = spacecraftSpacing;
    }
}
