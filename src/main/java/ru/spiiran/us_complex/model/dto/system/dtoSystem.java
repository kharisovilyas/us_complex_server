package ru.spiiran.us_complex.model.dto.system;

import ru.spiiran.us_complex.model.dto.IDTOEntity;
import ru.spiiran.us_complex.model.entitys.satrequest.SystemEntity;

public class dtoSystem implements IDTOEntity {

    private Long systemId;
    private Boolean earthStatus;
    private Boolean constellationStatus;
    private Boolean earthSatStatus;
    private Boolean satSatStatus;
    private Boolean gridStatus;
    private Long timeModelingHorizon;
    private Long startTime;
    private Long modelingBegin;
    private Long modelingEnd;
    private Boolean interSatelliteCommunication;
    private String controlSystem;
    private Double step;
    private Double duration;

    public dtoSystem() {}
    public dtoSystem(SystemEntity systemEntity) {
        this.systemId = systemEntity.getSystemId();
        this.earthStatus = systemEntity.getEarthStatus();
        this.constellationStatus = systemEntity.getConstellationStatus();
        this.earthSatStatus = systemEntity.getEarthSatStatus();
        this.satSatStatus = systemEntity.getSatSatStatus();
        this.gridStatus = systemEntity.getGridStatus();
        this.startTime = systemEntity.getStartTime();
        this.modelingBegin = systemEntity.getModelingBegin();
        this.modelingEnd = systemEntity.getModelingEnd();
        this.timeModelingHorizon = systemEntity.getTimeModelingHorizon();
        this.interSatelliteCommunication = systemEntity.getInterSatelliteCommunication();
        this.controlSystem = systemEntity.getControlSystem();
    }

    public Boolean getEarthStatus() {
        return earthStatus;
    }

    public void setEarthStatus(Boolean earthStatus) {
        this.earthStatus = earthStatus;
    }

    public Boolean getConstellationStatus() {
        return constellationStatus;
    }

    public void setConstellationStatus(Boolean constellationStatus) {
        this.constellationStatus = constellationStatus;
    }

    public Boolean getEarthSatStatus() {
        return earthSatStatus;
    }

    public void setEarthSatStatus(Boolean earthSatStatus) {
        this.earthSatStatus = earthSatStatus;
    }

    public Boolean getSatSatStatus() {
        return satSatStatus;
    }

    public void setSatSatStatus(Boolean satSatStatus) {
        this.satSatStatus = satSatStatus;
    }

    public Boolean getGridStatus() {
        return gridStatus;
    }

    public void setGridStatus(Boolean gridStatus) {
        this.gridStatus = gridStatus;
    }

    public Long getTimeModelingHorizon() {
        return timeModelingHorizon;
    }

    public void setTimeModelingHorizon(Long timeModelingHorizon) {
        this.timeModelingHorizon = timeModelingHorizon;
    }

    public Boolean getInterSatelliteCommunication() {
        return interSatelliteCommunication;
    }

    public void setInterSatelliteCommunication(Boolean interSatelliteCommunication) {
        this.interSatelliteCommunication = interSatelliteCommunication;
    }

    public String getControlSystem() {
        return controlSystem;
    }

    public void setControlSystem(String controlSystem) {
        this.controlSystem = controlSystem;
    }

    public Long getSystemId() {
        return systemId;
    }

    public void setSystemId(Long systemId) {
        this.systemId = systemId;
    }


    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getModelingBegin() {
        return modelingBegin;
    }

    public void setModelingBegin(Long modelingBegin) {
        this.modelingBegin = modelingBegin;
    }

    public Long getModelingEnd() {
        return modelingEnd;
    }

    public void setModelingEnd(Long modelingEnd) {
        this.modelingEnd = modelingEnd;
    }

    public Double getStep() {
        return step;
    }

    public void setStep(Double step) {
        this.step = step;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }
}
