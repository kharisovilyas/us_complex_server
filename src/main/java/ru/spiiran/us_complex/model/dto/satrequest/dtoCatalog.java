package ru.spiiran.us_complex.model.dto.satrequest;

import ru.spiiran.us_complex.model.entitys.satrequest.CatalogEntity;

public class dtoCatalog {
    private Long goalId;
    private String goalName;
    private Double lat;
    private Double lon;
    private Double alt;

    public dtoCatalog() {
    }
    public dtoCatalog(CatalogEntity catalogEntity) {
        this.alt = catalogEntity.getAlt();
        this.goalId = catalogEntity.getGoalId();
        this.lon = catalogEntity.getLon();
        this.lat = catalogEntity.getLat();
        this.goalName = catalogEntity.getGoalName();
    }
    public Long getGoalId() {
        return goalId;
    }

    public void setGoalId(Long goalId) {
        this.goalId = goalId;
    }

    public String getGoalName() {
        return goalName;
    }

    public void setGoalName(String goalName) {
        this.goalName = goalName;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getAlt() {
        return alt;
    }

    public void setAlt(Double alt) {
        this.alt = alt;
    }
}
