package ru.spiiran.us_complex.model.dto.modelling.dto_smao;

import ru.spiiran.us_complex.model.entitys.satrequest.SystemEntity;

import java.util.ArrayList;
import java.util.List;

public class Parameters {
    private Long t0Ballistics;
    private Long t0FlightData;
    private Long tfFlightData;
    private Double dtBallistics;
    private Boolean isInterSatellite;
    private String controlSystem;
    private Double dtShooting;
    private List<ConfigUDM> config_udm;
    public Parameters(SystemEntity systemEntity){
        this.t0Ballistics = systemEntity.getStartTime();
        this.t0FlightData = systemEntity.getModelingBegin();
        this.tfFlightData = systemEntity.getModelingEnd();
        this.dtBallistics = systemEntity.getStep();
        this.isInterSatellite = systemEntity.getInterSatelliteCommunication();
        this.controlSystem = systemEntity.getControlSystem();
        this.dtShooting = systemEntity.getDuration();
        List<ConfigUDM> configUDMList = new ArrayList<>();
        configUDMList.add(new ConfigUDM(4L, 2.1, 10.0, 10.0));
        configUDMList.add(new ConfigUDM(3L, 0.5, 4.0, 10.0));
        configUDMList.add(new ConfigUDM(2L, 0.005, 2.0, 20.0));
        this.config_udm = configUDMList;

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


    public Double getDtShooting() {
        return dtShooting;
    }

    public void setDtShooting(Double dtShooting) {
        this.dtShooting = dtShooting;
    }

    public Double getDtBallistics() {
        return dtBallistics;
    }

    public void setDtBallistics(Double dtBallistics) {
        this.dtBallistics = dtBallistics;
    }

    public List<ConfigUDM> getConfig_udm() {
        return config_udm;
    }

    public void setConfig_udm(List<ConfigUDM> config_udm) {
        this.config_udm = config_udm;
    }
}
