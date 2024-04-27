package ru.spiiran.us_complex.model.entitys.satrequest;

import jakarta.persistence.*;
import ru.spiiran.us_complex.model.dto.message.dtoMessage;
import ru.spiiran.us_complex.model.dto.system.dtoSystem;
import ru.spiiran.us_complex.model.entitys.general.IEntity;

import java.security.Timestamp;

@Entity
@Table(name = "sr_system")
public class SystemEntity implements IEntity {
    @Id
    @Column(name = "id_system")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long systemId;
    @Column(name = "earth_point_status")
    private Boolean earthStatus;
    @Column(name = "constellation_status")
    private Boolean constellationStatus;
    @Column(name = "earth_sat_status")
    private Boolean earthSatStatus;
    @Column(name = "satS_sat_status")
    private Boolean satSatStatus;
    @Column(name = "grid_status")
    private Boolean gridStatus;
    @Column(name = "time_modeling_horizon")
    private String timeModelingHorizon;
    @Column(name = "start_time")
    private Timestamp startTime;
    @Column(name = "modeling_begin")
    private Timestamp modelingBegin;
    @Column(name = "modeling_end")
    private Timestamp modelingEnd;
    @Column(name = "inter_satellite_communication")
    private Boolean interSatelliteCommunication;
    @Column(name = "control_system")
    private String controlSystem;

    @Override
    public Long getID() {
        return systemId;
    }

    public SystemEntity() {
    }
    public SystemEntity(dtoSystem dtoSystem) {
        this.systemId = dtoSystem.getSystemId();
        this.startTime = dtoSystem.getStartTime();
        this.controlSystem = dtoSystem.getControlSystem();
        this.earthSatStatus = dtoSystem.getEarthSatStatus();
        this.constellationStatus = dtoSystem.getConstellationStatus();
        this.earthStatus = dtoSystem.getEarthStatus();
        this.gridStatus = dtoSystem.getGridStatus();
        this.interSatelliteCommunication = dtoSystem.getInterSatelliteCommunication();
        this.modelingBegin = dtoSystem.getModelingBegin();
        this.modelingEnd = dtoSystem.getModelingEnd();
        this.satSatStatus = dtoSystem.getSatSatStatus();
        this.timeModelingHorizon = dtoSystem.getTimeModelingHorizon();
    }

    @Override
    public dtoMessage getDtoMessage(String type, String message) {
        return new dtoMessage(type, message);
    }

    public void setID(Long ID) {
        this.systemId = ID;
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
}