package ru.spiiran.us_complex.model.dto.system;

import ru.spiiran.us_complex.model.dto.IDTOEntity;
import ru.spiiran.us_complex.model.entitys.satrequest.SystemEntity;

import java.security.Timestamp;

public class dtoSystem implements IDTOEntity {

    private Long systemId;
    private Boolean earthStatus;
    private Boolean constellationStatus;
    private Boolean earthSatStatus;
    private Boolean satSatStatus;
    private Boolean gridStatus;
    private String timeModelingHorizon;
    private Timestamp startTime;
    private Timestamp modelingBegin;
    private Timestamp modelingEnd;
    private Boolean interSatelliteCommunication;
    private String controlSystem;

    public dtoSystem() {}
    public dtoSystem(SystemEntity systemEntity) {
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

    public String getTimeModelingHorizon() {
        return timeModelingHorizon;
    }

    public void setTimeModelingHorizon(String timeModelingHorizon) {
        this.timeModelingHorizon = timeModelingHorizon;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getModelingBegin() {
        return modelingBegin;
    }

    public void setModelingBegin(Timestamp modelingBegin) {
        this.modelingBegin = modelingBegin;
    }

    public Timestamp getModelingEnd() {
        return modelingEnd;
    }

    public void setModelingEnd(Timestamp modelingEnd) {
        this.modelingEnd = modelingEnd;
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
}
