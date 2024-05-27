package ru.spiiran.us_complex.model.dto.modelling.dto_smao;

import ru.spiiran.us_complex.model.entitys.satrequest.SystemEntity;

public class Parameters {
    private Long t0Ballistics;
    private Long t0FlightData;
    private Long tfFlightData;
    private Long dtBallistics;
    private Boolean isInterSatellite;
    private String controlSystem;
    private Long dtShooting;
    public Parameters(SystemEntity systemEntity){
        this.t0Ballistics = systemEntity.getStartTime();
        this.t0FlightData = systemEntity.getModelingBegin();
        this.tfFlightData = systemEntity.getModelingEnd();
        this.dtBallistics = systemEntity.getStep();
        this.isInterSatellite = systemEntity.getInterSatelliteCommunication();
        this.controlSystem = systemEntity.getControlSystem();
        this.dtShooting = systemEntity.getDuration();
    }

    public Parameters() {}

    public Long getT0Ballistics() {
        return t0Ballistics;
    }

    public void setT0Ballistics(Long t0Ballistics) {
        this.t0Ballistics = t0Ballistics;
    }

    public Long getT0FlightData() {
        return t0FlightData;
    }

    public void setT0FlightData(Long t0FlightData) {
        this.t0FlightData = t0FlightData;
    }

    public Long getTfFlightData() {
        return tfFlightData;
    }

    public void setTfFlightData(Long tfFlightData) {
        this.tfFlightData = tfFlightData;
    }

    public Long getDtBallistics() {
        return dtBallistics;
    }

    public void setDtBallistics(Long dtBallistics) {
        this.dtBallistics = dtBallistics;
    }

    public Boolean getInterSatelliteCommunication() {
        return isInterSatellite;
    }

    public void setInterSatelliteCommunication(Boolean interSatelliteCommunication) {
        isInterSatellite = interSatelliteCommunication;
    }

    public String getControlSystem() {
        return controlSystem;
    }

    public void setControlSystem(String controlSystem) {
        this.controlSystem = controlSystem;
    }

    public Long getDtShooting() {
        return dtShooting;
    }

    public void setDtShooting(Long dtShooting) {
        this.dtShooting = dtShooting;
    }
}
