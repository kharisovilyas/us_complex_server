package ru.spiiran.us_complex.model.dto.modelling.response.pro42;

import ru.spiiran.us_complex.model.entitys.modelling.EarthSatEntity;

public class dtoEarthSat {
    private String earthName;
    private Long satelliteId;
    private Long begin;
    private Long end;

    public dtoEarthSat() {}

    public dtoEarthSat(EarthSatEntity earthSatEntity) {
        this.earthName = earthSatEntity.getEarthPoint().getNameEarthPoint();
        this.satelliteId = earthSatEntity.getSatellite().getIdNodeEntity().getNodeId();
        this.end = earthSatEntity.getEndTime();
        this.begin = earthSatEntity.getBeginTime();
    }

    public String getEarthName() {
        return earthName;
    }

    public void setEarthName(String earthName) {
        this.earthName = earthName;
    }

    public Long getSatelliteId() {
        return satelliteId;
    }

    public void setSatelliteId(Long satelliteId) {
        this.satelliteId = satelliteId;
    }

    public Long getBegin() {
        return begin;
    }

    public void setBegin(Long begin) {
        this.begin = begin;
    }

    public Long getEnd() {
        return end;
    }

    public void setEnd(Long end) {
        this.end = end;
    }
}
